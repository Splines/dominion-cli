package me.splines.dominion.instruction;

import me.splines.dominion.action.Instruction;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.PlayerAbstract;
import me.splines.dominion.game.Stock;

/**
 * Instruction:
 * Increase the available money for the buying phase of the current move.
 *
 * Cards using this instruction include "Jahrmarkt" and "Vasall".
 */
public final class EarnMoneyInstruction implements Instruction {

    private final int earnMoney;

    public EarnMoneyInstruction(int earnMoney) {
        if (earnMoney < 0) {
            throw new IllegalArgumentException("Cannot earn a negative amount of money");
        }
        this.earnMoney = earnMoney;
    }

    @Override
    public void execute(PlayerAbstract player, MoveState moveState, Stock stock) {
        moveState.earnMoney(this.earnMoney);
    }

}
