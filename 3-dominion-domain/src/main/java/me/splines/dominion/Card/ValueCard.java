package me.splines.dominion.Card;

import java.util.List;

public abstract class ValueCard extends Card {

    private final int value;

    public ValueCard(String name, CardType type, int cost, int value) {
        super(name, type, cost);

        // CardType
        List<CardType> validTypes = List.of(
                CardType.MONEY,
                CardType.POINTS,
                CardType.CURSE);
        if (!validTypes.contains(type)) {
            throw new IllegalArgumentException(
                    String.format("Invalid type %s for value card", type));
        }

        // Value
        if (value == 0) {
            throw new IllegalArgumentException(
                    "The value of the value card must be non-zero");
        }
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
