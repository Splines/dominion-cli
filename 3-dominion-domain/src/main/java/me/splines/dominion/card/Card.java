package me.splines.dominion.card;

import java.util.Objects;

public abstract class Card {

    private final String name;
    private final CardType type;
    private final int cost;

    protected Card(String name, CardType type, int cost) {
        Objects.requireNonNull(name);
        this.name = name;

        Objects.requireNonNull(type);
        this.type = type;

        // Cost
        if (cost < 0) {
            throw new IllegalArgumentException("Card must cost at least 0 'money'");
        }
        this.cost = cost;
    }

    public String getName() {
        return this.name;
    }

    public CardType getType() {
        return this.type;
    }

    public int getCost() {
        return this.cost;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        return name.equals(other.name);
    }

    @Override
    public String toString() {
        return name;
    }

}
