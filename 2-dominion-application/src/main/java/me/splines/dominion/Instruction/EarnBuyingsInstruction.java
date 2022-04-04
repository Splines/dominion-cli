package me.splines.dominion.Instruction;

import me.splines.dominion.Game.Move;
import me.splines.dominion.Game.PlayerAbstract;

public final class EarnBuyingsInstruction implements Instruction {

    private final int earnBuyingsCount;

    public EarnBuyingsInstruction(int earnActionsCount) {
        this.earnBuyingsCount = earnActionsCount;
    }

    @Override
    public void execute(PlayerAbstract player, Move move) {
        move.earnBuyings(this.earnBuyingsCount);
    }

}