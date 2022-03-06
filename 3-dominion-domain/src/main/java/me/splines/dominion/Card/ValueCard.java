package me.splines.dominion.Card;

public final class ValueCard extends Card {

    private final int value;

    public ValueCard(String name, CardType type, int cost, int value)
            throws IllegalArgumentException {
        super(name, type, cost);

        // CardType
        if (type != CardType.MONEY
                && type != CardType.POINTS
                && type != CardType.CURSE) {
            throw new IllegalArgumentException(
                    String.format("Invalid type %s for value card", type));
        }

        // Value
        if (value == 0) {
            throw new IllegalArgumentException(
                    "Value card's value must be non-zero");
        }
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
