package me.splines.dominion.card;

public enum CardType {
    MONEY("Geld"),
    POINTS("Punkte"),
    CURSE("Fluch"),
    ACTION("Aktion"),
    ACTION_ATTACK("Angriff"),
    ACTION_REACTION("Reaktion");

    private final String name;

    private CardType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
