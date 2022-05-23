package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;

public abstract class PlayerAbstract {

    protected final String name;
    protected final Deck drawDeck;
    protected final Deck discardDeck = new Deck();
    protected List<Card> hand = new ArrayList<>();
    protected List<Card> table = new ArrayList<>();
    protected final PlayerDecision playerDecision;
    protected final Stock stock;

    public PlayerAbstract(String name, PlayerDecision playerDecision, Deck drawDeck, Stock stock) {
        this.name = name;
        this.playerDecision = playerDecision;
        this.drawDeck = drawDeck;
        this.stock = stock;
    }

    public PlayerDecision decision() {
        return this.playerDecision;
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

    public abstract void drawNewHandCards();

    public abstract void makeMove();

    public abstract void play(Card card);

    public abstract void buy(Card card);

    public abstract List<Card> getHand();

    public abstract List<Card> getTable();

    public abstract List<ActionCard> getActionCardsOnHand();

    public abstract List<MoneyCard> getMoneyCardsOnHand();

}
