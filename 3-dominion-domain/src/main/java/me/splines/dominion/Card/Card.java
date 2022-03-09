package me.splines.dominion.Card;

public abstract class Card {

    private final String name;
    private final CardType type;
    private final int cost;

    public Card(String name, CardType type, int cost) {
        this.name = name;
        this.type = type;

        // Cost
        if (cost < 0) {
            throw new IllegalArgumentException(
                    "Card cost must be greater than or equal to 0");
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
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
