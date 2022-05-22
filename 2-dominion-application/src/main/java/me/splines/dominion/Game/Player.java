package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;
import me.splines.dominion.Game.Deck.EmptyDeckException;

public class Player extends PlayerAbstract {

    public Player(String name, PlayerDecision playerDecision, Deck drawDeck) {
        super(name, playerDecision, drawDeck);
        drawNewHandCards();
    }

    @Override
    public Card draw() {
        try {
            Card card = drawDeck.draw();
            hand.add(card);
            return card;
        } catch (EmptyDeckException e) {
            makeDrawDeckFromDiscardDeck();
            return draw();
        }
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
        while (true) {
            try {
                discardDeck.draw();
                Card card = discardDeck.draw();
                drawDeck.put(card);
            } catch (EmptyDeckException e) {
                break;
            }
        }
    }

    @Override
    public void makeMove() {
        playerDecision.informYourTurn(this.name);
        PlayerMove move = new PlayerMove();
        move.doActionPhase(this);
        move.doBuyPhase(this);
        move.doCleanUpPhase(this);
    }

    @Override
    public void drawNewHandCards() {
        for (int i = 0; i < 5; i++) {
            this.draw();
        }
    }

    @Override
    public void discard(Card card) {
        removeFromHand(card);
        discardDeck.put(card);
    }

    @Override
    public void dispose(Card card) {
        removeFromHand(card);
    }

    private void removeFromHand(Card card) {
        boolean contained = hand.remove(card);
        if (!contained) {
            throw new HandDoesNotHaveCard(card);
        }
    }

    @Override
    public List<Card> getHand() {
        return new ArrayList<>(hand); // copy by value
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
    public void take(Card card) {
        discardDeck.put(card);
    }

    @Override
    public void takeToHand(Card card) {
        hand.add(card);
    }

}
