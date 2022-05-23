package me.splines.dominion.instruction;

import me.splines.dominion.action.Instruction;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.PlayerAbstract;
import me.splines.dominion.game.Stock;

/**
 * Instruction:
 * Increase the number of buyings for the buying phase of the current move.
 *
 * Cards using this instruction include "Jahrmarkt" and "Ratsversammlung".
 */
public final class EarnBuyingsInstruction implements Instruction {

    private final int earnBuyingsCount;

    public EarnBuyingsInstruction(int earnBuyingsCount) {
        if (earnBuyingsCount < 0) {
            throw new IllegalArgumentException("Cannot earn a negative amount of buyings");
        }
        this.earnBuyingsCount = earnBuyingsCount;
    }

    @Override
    public void execute(PlayerAbstract player, MoveState moveState, Stock stock) {
        moveState.earnBuyings(this.earnBuyingsCount);
    }

}
