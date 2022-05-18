package me.splines.dominion.Instruction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import me.splines.dominion.Card.MoneyCard;
import me.splines.dominion.Game.Move;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.PlayerDecision;
import me.splines.dominion.Game.StockAbstract;

/**
 * Instruction:
 * You MAY dispose a money card from your hand. Take a money card to your hand
 * that costs up to 3 "money" more than the disposed card.
 *
 * Cards using this instruction include "Mine".
 */
public class DisposeHandCardTakeMoneyCardInstruction implements Instruction {

    @Override
    public void execute(PlayerAbstract player, Move move, PlayerDecision decision, StockAbstract stock) {
        // Do we have money cards on the hand?
        List<MoneyCard> moneyCardsOnHand = player.getMoneyCardsOnHand();
        if (moneyCardsOnHand.isEmpty())
            return;

        // Dispose money cards from hand
        Optional<MoneyCard> cardToDispose = decision.chooseOptionalMoneyCard(moneyCardsOnHand);
        if (!cardToDispose.isPresent())
            return;
        player.dispose(cardToDispose.get());

        // Choose a money card to take
        int maxCost = cardToDispose.get().getCost() + 3;
        List<MoneyCard> moneyCardsToChoose = stock.getAvailableMoneyCards()
                .stream()
                .filter((card) -> card.getCost() <= maxCost)
                .collect(Collectors.toList());
        if (moneyCardsToChoose.isEmpty())
            return;

        MoneyCard cardToTake = decision.chooseMoneyCard(moneyCardsToChoose);
        player.takeToHand(cardToTake);
    }

}
