package me.splines.dominion.Instruction;

import me.splines.dominion.Game.Move;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.PlayerDecision;
import me.splines.dominion.Game.StockAbstract;

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
    public void execute(PlayerAbstract player, Move move, PlayerDecision decision, StockAbstract stock) {
        move.earnActions(this.earnActionsCount);
    }

}
