package me.splines.dominion.Instruction;

import me.splines.dominion.Action.Instruction;
import me.splines.dominion.Game.MoveState;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.Stock;

/**
 * Instruction:
 * Increase the number of playable actions in this move.
 *
 * Cards using this instruction include "Dorf" and "Wilddiebin".
 */
public final class EarnActionsInstruction implements Instruction {

    private final int earnActionsCount;

    public EarnActionsInstruction(int earnActionsCount) {
        this.earnActionsCount = earnActionsCount;
    }

    @Override
    public void execute(PlayerAbstract player, MoveState moveState, Stock stock) {
        moveState.earnActions(this.earnActionsCount);
    }

}
