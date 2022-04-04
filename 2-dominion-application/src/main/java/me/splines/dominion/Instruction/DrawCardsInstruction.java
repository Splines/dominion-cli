package me.splines.dominion.Instruction;

import me.splines.dominion.Game.Move;
import me.splines.dominion.Game.PlayerAbstract;

public final class DrawCardsInstruction implements Instruction {

    private final int cardsToDrawCount;

    public DrawCardsInstruction(int cardsToDrawCount) {
        this.cardsToDrawCount = cardsToDrawCount;
    }

    @Override
    public void execute(PlayerAbstract player, Move move) {
        for (int i = 0; i < this.cardsToDrawCount; i++) {
            player.draw();
        }
    }

}
