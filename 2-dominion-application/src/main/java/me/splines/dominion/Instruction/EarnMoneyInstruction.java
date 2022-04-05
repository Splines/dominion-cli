package me.splines.dominion.Instruction;

import me.splines.dominion.Game.Move;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.PlayerDecision;
import me.splines.dominion.Game.Stock;

/**
 * Instruction:
 * Increase the available money for the buying phase of the current move.
 *
 * Cards using this instruction include "Jahrmarkt" and "Vasall".
 */
public final class EarnMoneyInstruction implements Instruction {

    private final int earnMoney;

    public EarnMoneyInstruction(int earnMoney) {
        this.earnMoney = earnMoney;
    }

    @Override
    public void execute(PlayerAbstract player, Move move, PlayerDecision decision, Stock stock) {
        move.earnMoney(this.earnMoney);
    }

}
