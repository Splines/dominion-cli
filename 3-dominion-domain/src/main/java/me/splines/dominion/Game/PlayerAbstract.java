package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;

import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;

public abstract class PlayerAbstract {

    protected final String name;
    protected final Deck drawDeck = new Deck();
    protected final Deck discardDeck = new Deck();
    protected final List<Card> hand = new ArrayList<>();

    public PlayerAbstract(String name) {
        this.name = name;
    }

    public class HandDoesNotHaveCard extends RuntimeException {

        public HandDoesNotHaveCard(Card card) {
            super(String.format("Card %s is not present in hand", card));
        }

    }

    public String getName() {
        return this.name;
    }

    public abstract void take(Card card);

    public abstract void takeToHand(Card card);

    public abstract void discard(Card card);

    public abstract void dispose(Card card);

    public abstract Card draw();

    public abstract void makeMove();

    public abstract List<Card> getHand();

    public abstract List<MoneyCard> getMoneyCardsOnHand();

}
