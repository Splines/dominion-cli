package me.splines.dominion.Instruction;

import java.util.List;

import me.splines.dominion.Card.Card;
import me.splines.dominion.Game.Move;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.PlayerDecision;
import me.splines.dominion.Game.Stock;

/**
 * Instruction:
 * Discard any number of your hand cards and then draw the same number of cards.
 *
 * Cards using this instruction include "Keller".
 */
public class DiscardAndDrawCardsInstruction implements Instruction {

    @Override
    public void execute(PlayerAbstract player, Move move, PlayerDecision decision, Stock stock) {
        List<Card> cardsToDiscard = decision.chooseCards(player.getHand());
        cardsToDiscard.forEach((card) -> player.discard(card));

        for (int i = 0; i < cardsToDiscard.size(); i++) {
            player.draw();
        }
    }

}
