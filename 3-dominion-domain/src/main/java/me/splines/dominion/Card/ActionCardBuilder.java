package me.splines.dominion.Card;

import me.splines.dominion.Instruction.Action;

public class ActionCardBuilder {

    private String name;
    private CardType cardType;
    private int cost;
    private Action action;

    public ActionCardBuilder(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.cardType = CardType.ACTION;
    }

    public ActionCardBuilder asAttack() {
        this.cardType = CardType.ACTION_ATTACK;
        return this;
    }

    public ActionCardBuilder asReaction() {
        this.cardType = CardType.ACTION_REACTION;
        return this;
    }

    public ActionCardBuilder with(Action action) {
        this.action = action;
        return this;
    }

    public ActionCard build() {
        return new ActionCard(this.name, this.cardType, this.cost, this.action);
    }

}
