package me.splines.dominion.instruction;

import me.splines.dominion.action.Instruction;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.Player;
import me.splines.dominion.game.Stock;

/**
 * Instruction:
 * You MUST draw X cards from the draw deck.
 *
 * Cards using this instruction include "Schmiede" and "Markt".
 */
public final class DrawCardsInstruction implements Instruction {

    private final int cardsToDrawCount;

    public DrawCardsInstruction(int cardsToDrawCount) {
        if (cardsToDrawCount < 0) {
            throw new IllegalArgumentException("Cannot draw a negative number of cards");
        }
        this.cardsToDrawCount = cardsToDrawCount;
    }

    @Override
    public void execute(Player player, MoveState moveState, Stock stock) {
        for (int i = 0; i < this.cardsToDrawCount; i++) {
            player.draw();
        }
    }

    @Override
    public String getName() {
        String cardSingularPlural = (cardsToDrawCount == 1) ? "Karte" : "Karten";
        return "+" + cardsToDrawCount + " " + cardSingularPlural;
    }

}
