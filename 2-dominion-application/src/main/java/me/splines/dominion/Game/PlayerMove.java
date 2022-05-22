package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;

public class PlayerMove implements Move {

    private MoveState moveState = new MoveState();

    /**
     * 1st PHASE - Action phase
     * Player MAY play as many action cards as he/she wants.
     *
     * @param move
     */
    @Override
    public void doActionPhase(PlayerAbstract player) {
        List<ActionCard> actionCardsOnHand;

        int i = 0;
        while (moveState.getActionsCount() > 0) {
            actionCardsOnHand = player.getActionCardsOnHand();
            if (actionCardsOnHand.isEmpty()) {
                if (i == 0)
                    player.decision().informNoActionCardsPlayable();
                return;
            }

            Optional<ActionCard> actionCard = player.decision()
                    .chooseOptionalActionCard(actionCardsOnHand);
            if (actionCard.isEmpty())
                return; // player chose not to play an action card

            // Actual execution of action
            moveState.looseAction();
            actionCard.get().getAction().getInstructions()
                    .forEach(instr -> instr.execute(player, moveState, GameState.stock));

            i++;
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
