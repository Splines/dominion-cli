package me.splines.dominion.game;

import java.util.ArrayList;
import java.util.List;

import me.splines.dominion.card.ActionCard;
import me.splines.dominion.card.Card;
import me.splines.dominion.card.MoneyCard;
import me.splines.dominion.interaction.PlayerDecision;
import me.splines.dominion.interaction.PlayerInformation;
import me.splines.dominion.interaction.PlayerInteraction;

public abstract class PlayerAbstract {

    protected final String name;
    protected final Deck drawDeck;
    protected final Deck discardDeck = new Deck();
    protected List<Card> hand = new ArrayList<>();
    protected List<Card> table = new ArrayList<>();
    protected final PlayerInteraction playerInteraction;
    protected final Stock stock;

    protected PlayerAbstract(String name, PlayerInteraction playerInteraction, Deck drawDeck, Stock stock) {
        this.name = name;
        this.playerInteraction = playerInteraction;
        this.drawDeck = drawDeck;
        this.stock = stock;
    }

    public PlayerDecision decide() {
        return this.playerInteraction.decision();
    }

    public PlayerInformation inform() {
        return this.playerInteraction.information();
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

    public abstract List<Card> drawNewHandCards();

    public abstract void makeMove();

    public abstract void play(Card card);

    public abstract List<Card> getHand();

    public abstract void clearHand();

    public abstract List<Card> getTable();

    public abstract void clearTable();

    public abstract List<ActionCard> getActionCardsOnHand();

    public abstract List<MoneyCard> getMoneyCardsOnHand();

    public abstract int calculatePoints();

}
