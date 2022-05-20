package me.splines.dominion.Instruction;

import me.splines.dominion.Action.Instruction;
import me.splines.dominion.Game.MoveState;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.Stock;

/**
 * Instruction:
 * You MUST draw X cards from the draw deck.
 *
 * Cards using this instruction include "Schmiede" and "Markt".
 */
public final class DrawCardsInstruction implements Instruction {

    private final int cardsToDrawCount;

    public DrawCardsInstruction(int cardsToDrawCount) {
        this.cardsToDrawCount = cardsToDrawCount;
    }

    @Override
    public void execute(PlayerAbstract player, MoveState moveState, Stock stock) {
        for (int i = 0; i < this.cardsToDrawCount; i++) {
            player.draw();
        }

    }

}
