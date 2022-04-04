package me.splines.dominion.Card;

import java.util.Objects;

import me.splines.dominion.Instruction.Action;

public class ActionCard extends Card {

    private final Action action;

    public ActionCard(String name, CardType type, int cost, Action action) {
        super(name, type, cost);

        // Card Type
        if (type != CardType.ACTION
                && type != CardType.ACTION_ATTACK
                && type != CardType.ACTION_REACTION) {
            throw new IllegalArgumentException(String.format("Invalid type %s for action card", type));
        }

        // Action
        Objects.requireNonNull(action);
        this.action = action;
    }

    public Action getAction() {
        return this.action;
    }

}
