package me.splines.dominion.instruction;

import me.splines.dominion.action.Instruction;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.PlayerAbstract;
import me.splines.dominion.game.Stock;

/**
 * Instruction:
 * Increase the number of playable actions in this move.
 *
 * Cards using this instruction include "Dorf" and "Wilddiebin".
 */
public final class EarnActionsInstruction implements Instruction {

    private final int earnActionsCount;

    public EarnActionsInstruction(int earnActionsCount) {
        if (earnActionsCount < 0) {
            throw new IllegalArgumentException("Cannot earn a negative amount of actions");
        }
        this.earnActionsCount = earnActionsCount;
    }

    @Override
    public void execute(PlayerAbstract player, MoveState moveState, Stock stock) {
        moveState.earnActions(this.earnActionsCount);
    }

    @Override
    public String getName() {
        String actionSingularPlural = (earnActionsCount == 1) ? "Aktion" : "Aktionen";
        return "+" + earnActionsCount + " " + actionSingularPlural;
    }

}
