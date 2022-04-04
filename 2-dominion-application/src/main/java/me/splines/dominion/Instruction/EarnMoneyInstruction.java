package me.splines.dominion.Instruction;

import me.splines.dominion.Game.Move;
import me.splines.dominion.Game.PlayerAbstract;

public final class EarnMoneyInstruction implements Instruction {

    private final int earnMoney;

    public EarnMoneyInstruction(int earnMoney) {
        this.earnMoney = earnMoney;
    }

    @Override
    public void execute(PlayerAbstract player, Move move) {
        move.earnMoney(this.earnMoney);
    }

}
