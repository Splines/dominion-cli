package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;

import me.splines.dominion.Card.Card;

public abstract class PlayerAbstract {

    protected final String name;
    protected final Deck drawDeck = new Deck();
    protected final Deck discardDeck = new Deck();
    protected final List<Card> hand = new ArrayList<>();

    public PlayerAbstract(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract Card draw();

    public abstract void makeMove();

}
