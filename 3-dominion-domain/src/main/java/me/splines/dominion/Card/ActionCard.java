package me.splines.dominion.Card;

import java.util.List;
import java.util.Objects;

import me.splines.dominion.Action.Action;

public class ActionCard extends Card {

    private final Action action;

    public ActionCard(String name, CardType type, int cost, Action action) {
        super(name, type, cost);

        // Card Type
        List<CardType> validTypes = List.of(
                CardType.ACTION,
                CardType.ACTION_ATTACK,
                CardType.ACTION_REACTION);
        if (!validTypes.contains(type)) {
            throw new IllegalArgumentException(
                    String.format("Invalid type %s for action card", type));
        }

        // Action
        Objects.requireNonNull(action);
        this.action = action;
    }

    public Action getAction() {
        return this.action;
    }

}
