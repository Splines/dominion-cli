package me.splines.dominion.Instruction;

import me.splines.dominion.Action.Instruction;
import me.splines.dominion.Game.MoveState;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.StockAbstract;

/**
 * Instruction:
 * Increase the number of buyings for the buying phase of the current move.
 *
 * Cards using this instruction include "Jahrmarkt" and "Ratsversammlung".
 */
public final class EarnBuyingsInstruction implements Instruction {

    private final int earnBuyingsCount;

    public EarnBuyingsInstruction(int earnActionsCount) {
        this.earnBuyingsCount = earnActionsCount;
    }

    @Override
    public void execute(PlayerAbstract player, MoveState moveState, StockAbstract stock) {
        moveState.earnBuyings(this.earnBuyingsCount);
    }

}
