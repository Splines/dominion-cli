package me.splines.dominion.instruction;

import me.splines.dominion.action.Instruction;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.Player;
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
    public void execute(Player player, MoveState moveState, Stock stock) {
        moveState.earnBuyings(this.earnBuyingsCount);
    }

    @Override
    public String getName() {
        String buyingSingularPlural = (earnBuyingsCount == 1) ? "Kauf" : "Käufe";
        return "+" + earnBuyingsCount + " " + buyingSingularPlural;
    }

}
