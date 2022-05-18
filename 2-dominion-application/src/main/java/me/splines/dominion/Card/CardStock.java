package me.splines.dominion.Card;

/**
 * A stock of same cards, e.g. only gold money cards.
 */
public class CardStock<T extends Card> {

    private final T card;
    private final int count;

    public CardStock(T card, int count) {
        this.card = card;
        this.count = count;
    }

    public T getCard() {
        return card;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

}
