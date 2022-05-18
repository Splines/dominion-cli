package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;
import me.splines.dominion.Game.Deck.EmptyDeckException;
import me.splines.dominion.Instruction.Instruction;

public final class Player extends PlayerAbstract {

    private final PlayerDecision playerDecision;

    public Player(String name, PlayerDecision playerDecision) {
        super(name);
        this.playerDecision = playerDecision;
    }

    public PlayerDecision getDecisionHandle() {
        return this.playerDecision;
    }

    @Override
    public Card draw() {
        try {
            Card card = drawDeck.draw();
            hand.add(card);
            return card;
        } catch (EmptyDeckException e) {
            makeDrawDeckFromDiscardDeck();
            draw();
        }
        return null;
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
                Card card = discardDeck.draw();
                drawDeck.put(card);
            } catch (EmptyDeckException e) {
                break;
            }
        }
    }

    @Override
    public void makeMove() {
        Move move = new Move();

        // TODO: Is this really code for a "Player" class?
        // 1st PHASE - Action phase
        // player MAY play an action card
        ActionCard actionCard = playerDecision.chooseActionCard(List.copyOf(hand));
        if (actionCard != null) {
            List<Instruction> instructions = actionCard.getAction().getInstructions();

            // TODO: Execute instruction
            // instructions.forEach((instruction) -> instruction.execute(player, move,
            // decision, stock));
        }
        // check that card played is action card (!)
        // move card from hand to table
        // perform instructions on that card from top to bottom
        // instructions might be "MUST" or "MAY DO" -> player must be able to decide
        // (maybe observer pattern here?)
        // instruction might recursively lead to another card being played and so on

        // 2nd PHASE - Buy phase
        // player MAY buy cards according to money available

        // 3rd PHASE - "Clean up" phase
        // player MUST put all hand & table cards to the discard deck ("discard" these
        // cards)
        // player MUST draw 5 new cards for the next move
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
    public List<MoneyCard> getMoneyCardsOnHand() {
        return getHand()
                .stream()
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
