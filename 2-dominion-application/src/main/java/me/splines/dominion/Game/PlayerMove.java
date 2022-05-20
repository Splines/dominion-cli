package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.splines.dominion.Action.Instruction;
import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.CardType;
import me.splines.dominion.Card.MoneyCard;

public class PlayerMove implements Move {

    private MoveState moveState = new MoveState();

    /**
     * 1st PHASE - Action phase
     * Player MAY play as many action card as he/she wants.
     *
     * @param move
     */
    @Override
    public void doActionPhase(PlayerAbstract player) {

        while (moveState.getActionsCount() > 0
                && player.decision().checkWantToPlayActionCard()) {
            moveState.looseAction();
            // Choose action card to play
            List<ActionCard> actionCardsInHand = player.hand.stream()
                    .filter(card -> card.getType() == CardType.ACTION)
                    .map(ActionCard.class::cast)
                    .collect(Collectors.toList());
            ActionCard actionCard = player.decision().chooseActionCard(actionCardsInHand);
            // Execute all instructions of action card
            List<Instruction> instructions = actionCard.getAction().getInstructions();
            instructions.forEach((i) -> i.execute(player, moveState, GameState.stock));
        }
    }

    /**
     * 2nd PHASE - Buy phase
     * Player MAY buy cards according to his/her available money.
     *
     * @param move
     */
    @Override
    public void doBuyPhase(PlayerAbstract player) {
        // Earn money from money cards
        for (Card card : player.hand) {
            if (card instanceof MoneyCard) {
                int money = ((MoneyCard) card).getMoney();
                moveState.earnMoney(money);
            }
        }

        // Buy cards
        List<Card> buyableCards = GameState.stock.getAvailableCardsWithMaxCosts(moveState.getMoney());
        if (!buyableCards.isEmpty()) {
            while (!buyableCards.isEmpty() && moveState.getMoney() >= 1 &&
                    player.decision().checkWantToBuy()) {
                List<Card> boughtCards = new ArrayList<>();
                player.decision().chooseCardsToBuy(buyableCards);
                Card boughtCard = player.decision().chooseCard(buyableCards);
                boughtCards.add(boughtCard);

                moveState.looseBuying();
                moveState.looseMoney(boughtCard.getCost());

                buyableCards = GameState.stock.getAvailableCardsWithMaxCosts(moveState.getMoney());
            }
        }
    }

    /**
     * 3rd PHASE - "Clean up" phase
     * Player MUST put all hand & table cards to the discard deck.
     * Player MUST draw 5 new cards for the next move.
     *
     * @param move
     */
    @Override
    public void doCleanUpPhase(PlayerAbstract player) {
        player.hand.forEach(h -> player.discardDeck.put(h));
        player.hand.clear();
        player.drawNewHandCards();
    }

}
