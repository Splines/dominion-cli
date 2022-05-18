package me.splines.dominion.Instruction;

import me.splines.dominion.Game.Move;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.PlayerDecision;
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
    public void execute(PlayerAbstract player, Move move, PlayerDecision decision, StockAbstract stock) {
        move.earnBuyings(this.earnBuyingsCount);
    }

}
