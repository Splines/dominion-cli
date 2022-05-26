package me.splines.dominion.game;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.splines.dominion.card.ActionCard;
import me.splines.dominion.card.Card;
import me.splines.dominion.card.CurseCard;
import me.splines.dominion.card.MoneyCard;
import me.splines.dominion.card.PointCard;
import me.splines.dominion.game.Deck.EmptyDeckException;
import me.splines.dominion.game.Deck.NotEnoughCardsOnDeckException;
import me.splines.dominion.interaction.PlayerInteraction;

public class Player extends PlayerAbstract {

    public Player(String name, PlayerInteraction playerInteraction, Deck drawDeck, Stock stock) {
        super(name, playerInteraction, drawDeck, stock);
        drawNewHandCards();
    }

    /////////////////////////////// Move ///////////////////////////////////////

    @Override
    public void makeMove() {
        inform().yourTurn(this.name, getHand());
        PlayerMove move = new PlayerMove(this, stock);
        move.doActionPhase();
        move.doBuyPhase();
        move.doCleanUpPhase();
    }

    ////////////////////////////// Draw cards //////////////////////////////////

    @Override
    public Card draw() {
        try {
            return drawNormal();
        } catch (EmptyDeckException e) {
            makeDrawDeckFromDiscardDeck();
            // this time: don't catch EmptyDeckException
            return drawNormal();
        }
    }

    private Card drawNormal() {
        Card card = drawDeck.draw();
        hand.add(card);
        return card;
    }

    private void drawMultipleWithCheck(int cardsCount) {
        if (drawDeck.size() < cardsCount) {
            int cardsNeeded = cardsCount - drawDeck.size();
            if (discardDeck.size() < cardsNeeded) {
                String msg = "Draw deck has " + drawDeck.size() +
                        " cards and discard deck has only " + discardDeck.size()
                        + "cards. Can't draw " + cardsCount + " cards in total";
                throw new NotEnoughCardsOnDeckException(msg);
            }
        }
        for (int i = 0; i < cardsCount; i++) {
            this.draw();
        }
    }

    @Override
    public List<Card> drawNewHandCards() {
        drawMultipleWithCheck(5);
        return getHand();
    }

    /**
     * Make a new draw deck from the discard deck
     * by shuffling the cards and putting them on the draw deck.
     *
     * Afterwards the discardDeck is empty and the drawDeck has all the cards
     * from the drawDeck.
     */
    private void makeDrawDeckFromDiscardDeck() {
        discardDeck.shuffle();
        while (!discardDeck.isEmpty()) {
            Card card = discardDeck.draw();
            drawDeck.put(card);
        }
    }

    //////////////////////////// Hand & Table //////////////////////////////////

    @Override
    public void discard(Card card) {
        removeFromHand(card);
        discardDeck.put(card);
    }

    @Override
    public void dispose(Card card) {
        removeFromHand(card);
    }

    @Override
    public void play(Card card) {
        removeFromHand(card);
        table.add(card);
    }

    @Override
    public void take(Card card) {
        discardDeck.put(card);
    }

    @Override
    public void takeToHand(Card card) {
        hand.add(card);
    }

    private void removeFromHand(Card card) {
        boolean contained = hand.remove(card);
        if (!contained) {
            throw new HandDoesNotHaveCard(card);
        }
    }

    @Override
    public List<Card> getHand() {
        return List.copyOf(hand);
    }

    @Override
    public void clearHand() {
        hand.clear();
    }

    @Override
    public List<ActionCard> getActionCardsOnHand() {
        return getHand().stream()
                .filter(ActionCard.class::isInstance)
                .map(ActionCard.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<MoneyCard> getMoneyCardsOnHand() {
        return getHand().stream()
                .filter(MoneyCard.class::isInstance)
                .map(MoneyCard.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<Card> getTable() {
        return List.copyOf(table);
    }

    @Override
    public void clearTable() {
        table.clear();
    }

    ///////////////////////////////// Other ////////////////////////////////////

    @Override
    public int calculatePoints() {
        AtomicInteger points = new AtomicInteger(0);
        Stream.of(hand, discardDeck.asList(), drawDeck.asList())
                .flatMap(Collection::stream)
                .forEach(card -> {
                    if (card instanceof PointCard) {
                        int addPoints = ((PointCard) card).getPoints();
                        points.getAndAdd(addPoints);
                    } else if (card instanceof CurseCard) {
                        int curse = ((CurseCard) card).getCurse();
                        points.getAndAdd(curse);
                    }
                });
        return points.get();
    }

}
