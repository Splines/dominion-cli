package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;

import me.splines.dominion.Card.Card;
import me.splines.dominion.Game.Deck.EmptyDeckException;

public final class Player extends PlayerAbstract {

    public Player(String name) {
        super(name);
    }

    public Card draw() {
        // TODO: add handling for empty drawDeck
        // -> shuffle discardDeck, this will become the new drawDeck
        try {
            Card card = drawDeck.draw();
            hand.add(card);
            return card;
        } catch (EmptyDeckException e) {

        }

        return null;
    }

    public void makeMove() {
        Move move = new Move();

        // TODO Is this really code for a "Player" class?
        // 1st PHASE - Action phase
        // player MAY play an action card

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

    private void makeDrawDeckFromDiscardDeck() {
        // TODO
    }

    public void discard(Card card) {
        discardDeck.put(card);
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand); // copy by value
    }

}
