package me.splines.dominion.card;

import java.util.List;

public class FormatExceptions {

    private FormatExceptions() {
    }

    public static class CardNameTooLongException extends RuntimeException {
        public CardNameTooLongException(String name) {
            super("The card name " + name + " is too long");
        }
    }

    public static class CardsDifferentHeightsException extends RuntimeException {
        public CardsDifferentHeightsException(List<Card> cards) {
            super("The cards have different lengths, cannot print them in a grid: "
                    + String.join(",",
                            cards.stream().map(c -> c.getName()).toList()));
        }
    }

}
