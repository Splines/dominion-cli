package me.splines.dominion.Card;

public enum CardType {
    MONEY("Geld"),
    POINTS("Punkte"),
    CURSE("Fluch"),
    ACTION("Aktion"),
    ACTION_ATTACK("Aktion - Angriff"),
    ACTION_REACTION("Aktion - Reaktion");

    private final String name;

    private CardType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
