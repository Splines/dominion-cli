package me.splines.dominion.card;

public class PointCard extends ValueCard {

    public PointCard(String name, int cost, int points) {
        super(name, CardType.POINTS, cost, points);

        if (points < 0) {
            throw new IllegalArgumentException("Points must be a positive value");
        }
    }

    public int getPoints() {
        return getValue();
    }

}
