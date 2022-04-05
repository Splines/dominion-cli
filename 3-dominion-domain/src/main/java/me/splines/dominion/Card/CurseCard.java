package me.splines.dominion.Card;

public class CurseCard extends ValueCard {

    public CurseCard(String name, int cost, int curse) {
        super(name, CardType.CURSE, cost, curse);

        if (curse > 0) {
            throw new IllegalArgumentException("Curse must be a negative value");
        }
    }

    public int getCurse() {
        return getValue();
    }

}
