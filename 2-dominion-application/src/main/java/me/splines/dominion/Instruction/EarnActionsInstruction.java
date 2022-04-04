package me.splines.dominion.Instruction;

import me.splines.dominion.Game.Move;
import me.splines.dominion.Game.PlayerAbstract;

public final class EarnActionsInstruction implements Instruction {

    private final int earnActionsCount;

    public EarnActionsInstruction(int earnActionsCount) {
        this.earnActionsCount = earnActionsCount;
    }

    @Override
    public void execute(PlayerAbstract player, Move move) {
        move.earnActions(this.earnActionsCount);
    }

}
