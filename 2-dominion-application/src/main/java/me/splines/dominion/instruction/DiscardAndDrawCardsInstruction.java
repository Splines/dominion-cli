package me.splines.dominion.instruction;

import java.util.List;

import me.splines.dominion.action.Instruction;
import me.splines.dominion.card.Card;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.Player;
import me.splines.dominion.game.Stock;

/**
 * Instruction:
 * Discard any number of your hand cards and then draw the same number of cards.
 *
 * Cards using this instruction include "Keller".
 */
public class DiscardAndDrawCardsInstruction implements Instruction {

    @Override
    public void execute(Player player, MoveState moveState, Stock stock) {
        List<Card> cardsToDiscard = player.decide().chooseCards(player.getHand());
        cardsToDiscard.forEach(card -> player.discard(card));

        for (int i = 0; i < cardsToDiscard.size(); i++) {
            player.draw();
        }
    }

    @Override
    public String getName() {
        return "Lege beliebig viele deiner Handkarten ab und ziehe dann die "
                + "gleiche Anzahl Karten.";
    }

}
