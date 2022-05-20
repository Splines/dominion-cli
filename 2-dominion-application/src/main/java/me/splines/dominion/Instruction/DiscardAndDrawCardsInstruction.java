package me.splines.dominion.Instruction;

import java.util.List;

import me.splines.dominion.Card.Card;
import me.splines.dominion.Game.MoveState;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.StockAbstract;

/**
 * Instruction:
 * Discard any number of your hand cards and then draw the same number of cards.
 *
 * Cards using this instruction include "Keller".
 */
public class DiscardAndDrawCardsInstruction implements Instruction {

    @Override
    public void execute(PlayerAbstract player, MoveState moveState, StockAbstract stock) {
        List<Card> cardsToDiscard = player.decision().chooseCards(player.getHand());
        cardsToDiscard.forEach((card) -> player.discard(card));

        for (int i = 0; i < cardsToDiscard.size(); i++) {
            player.draw();
        }
    }

}
