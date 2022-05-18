package me.splines.dominion.Instruction;

import me.splines.dominion.Game.Move;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.PlayerDecision;
import me.splines.dominion.Game.StockAbstract;

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
    public void execute(PlayerAbstract player, Move move, PlayerDecision decision, StockAbstract stock) {
        for (int i = 0; i < this.cardsToDrawCount; i++) {
            player.draw();
        }

    }

}
