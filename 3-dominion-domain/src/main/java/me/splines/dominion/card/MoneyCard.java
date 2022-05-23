package me.splines.dominion.card;

public class MoneyCard extends ValueCard {

    public MoneyCard(String name, int cost, int money) {
        super(name, CardType.MONEY, cost, money);

        if (money < 0) {
            throw new IllegalArgumentException("'Money' must be a positive value");
        }
    }

    public int getMoney() {
        return getValue();
    }

}
