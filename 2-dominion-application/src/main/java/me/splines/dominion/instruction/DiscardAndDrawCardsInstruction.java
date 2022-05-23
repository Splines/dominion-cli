package me.splines.dominion.instruction;

import java.util.List;

import me.splines.dominion.action.Instruction;
import me.splines.dominion.card.Card;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.PlayerAbstract;
import me.splines.dominion.game.Stock;

/**
 * Instruction:
 * Discard any number of your hand cards and then draw the same number of cards.
 *
 * Cards using this instruction include "Keller".
 */
public class DiscardAndDrawCardsInstruction implements Instruction {

    @Override
    public void execute(PlayerAbstract player, MoveState moveState, Stock stock) {
        List<Card> cardsToDiscard = player.decision().chooseCards(player.getHand());
        cardsToDiscard.forEach(card -> player.discard(card));

        for (int i = 0; i < cardsToDiscard.size(); i++) {
            player.draw();
        }
    }

}
