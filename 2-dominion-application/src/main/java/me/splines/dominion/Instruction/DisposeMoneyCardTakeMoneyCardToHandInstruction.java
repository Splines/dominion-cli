package me.splines.dominion.Instruction;

import java.util.List;
import java.util.Optional;

import me.splines.dominion.Action.Instruction;
import me.splines.dominion.Card.MoneyCard;
import me.splines.dominion.Game.MoveState;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.Stock;

/**
 * Instruction:
 * You MAY dispose a money card from your hand. Take a money card to your hand
 * that costs up to 3 "money" more than the disposed card.
 *
 * Cards using this instruction include "Mine".
 */
public class DisposeMoneyCardTakeMoneyCardToHandInstruction implements Instruction {

    @Override
    public void execute(PlayerAbstract player, MoveState moveState, Stock stock) {
        // Do we have money cards on the hand?
        List<MoneyCard> moneyCardsOnHand = player.getMoneyCardsOnHand();
        if (moneyCardsOnHand.isEmpty())
            return;

        // Dispose money cards from hand
        Optional<MoneyCard> cardToDispose = player.decision()
                .chooseOptionalMoneyCard(moneyCardsOnHand);
        if (!cardToDispose.isPresent())
            return;
        player.dispose(cardToDispose.get());

        // Choose a money card to take
        int maxCost = cardToDispose.get().getCost() + 3;
        List<MoneyCard> moneyCardsToChoose = stock.getAvailableMoneyCardsWithMaxCosts(maxCost);
        if (moneyCardsToChoose.isEmpty())
            return;

        MoneyCard cardToTake = player.decision().chooseMoneyCard(moneyCardsToChoose);
        player.takeToHand(cardToTake);
    }

}
