package me.splines.dominion.Card;

/**
 * A stock of same cards, e.g. only gold money cards.
 */
public class CardStock<T extends Card> {

    public static class EmptyCardStockException extends RuntimeException {

        public EmptyCardStockException(Card card) {
            super("CardStock for cards '" + card
                    + "' is empty, cannot take card from it.");
        }

    }

    private final T card;
    private int count;

    public CardStock(T card, int count) {
        this.card = card;
        this.count = count;
    }

    public T getCard() {
        return card;
    }

    public boolean isEmpty() {
        return (count == 0);
    }

    public void takeOneCard() {
        if (count == 0)
            throw new EmptyCardStockException(card);
        count--;
    }

}
