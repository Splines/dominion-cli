package me.splines.dominion.instruction;

import java.util.List;
import java.util.Optional;

import me.splines.dominion.action.Instruction;
import me.splines.dominion.card.MoneyCard;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.Player;
import me.splines.dominion.game.Stock;

/**
 * Instruction:
 * You MAY dispose a money card from your hand. Take a money card to your hand
 * that costs up to 3 "money" more than the disposed card.
 *
 * Cards using this instruction include "Mine".
 */
public class DisposeMoneyCardTakeMoneyCardToHandInstruction implements Instruction {

    @Override
    public void execute(Player player, MoveState moveState, Stock stock) {
        // Do we have money cards on the hand?
        List<MoneyCard> moneyCardsOnHand = player.getMoneyCardsOnHand();
        if (moneyCardsOnHand.isEmpty())
            return;

        // Dispose money cards from hand
        Optional<MoneyCard> cardToDispose = player.decide()
                .chooseOptionalMoneyCard(moneyCardsOnHand);
        if (!cardToDispose.isPresent())
            return;

        // Choose a money card to take
        int maxCost = cardToDispose.get().getCost() + 3;
        List<MoneyCard> moneyCardsToChoose = stock.getAvailableMoneyCardsWithMaxCosts(maxCost);
        if (moneyCardsToChoose.isEmpty())
            return;

        player.dispose(cardToDispose.get());
        MoneyCard cardToTake = player.decide().chooseMoneyCard(moneyCardsToChoose);
        player.takeToHand(cardToTake);
    }

    @Override
    public String getName() {
        return "Du darfst eine Geldkarte aus deiner Hand entsorgen. Nimm eine "
                + "Geldkarte auf deine Hand, die bis zu 3💰 mehr als sie kostet.";
    }

}
