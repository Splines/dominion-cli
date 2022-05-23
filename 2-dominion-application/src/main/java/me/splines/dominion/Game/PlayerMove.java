package me.splines.dominion.Game;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;

public class PlayerMove extends Move {

    public PlayerMove(PlayerAbstract player, Stock stock) {
        super(player, stock);
    }

    /**
     * 1st PHASE - Action phase
     * Player MAY play as many action cards as he/she wants.
     *
     * @param move
     */
    @Override
    public void doActionPhase() {
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

            player.play(actionCard.get());
            moveState.looseAction();
            actionCard.get().getAction().getInstructions()
                    .forEach(instr -> instr.execute(player, moveState, stock));

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
    public void doBuyPhase() {
        // Earn money from money cards
        Stream.of(player.getHand().stream(), player.getTable().stream()).flatMap(c -> c)
                .forEach(card -> {
                    if (card instanceof MoneyCard) {
                        int money = ((MoneyCard) card).getMoney();
                        moveState.earnMoney(money);
                    }
                    // Money from action cards is automatically earned
                    // by having executed the instructions
                });

        // Buy cards
        List<Card> buyableCards;
        int i = 0;
        while (moveState.getBuyingsCount() > 0) {
            buyableCards = stock.getAvailableCardsWithMaxCosts(moveState.getMoney());
            if (buyableCards.isEmpty()) {
                if (i == 0)
                    player.decision().informNoCardsBuyableWithMoney(moveState.getMoney());
                return;
            }

            Optional<Card> boughtCardOptional = player.decision()
                    .chooseOptionalCardToBuy(buyableCards);
            if (boughtCardOptional.isEmpty())
                return; // player chose not to buy a card

            Card boughtCard = boughtCardOptional.get();
            stock.takeCard(boughtCard);
            player.take(boughtCard);
            moveState.looseBuying();
            moveState.looseMoney(boughtCard.getCost());

            i++;
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
    public void doCleanUpPhase() {
        player.getHand().forEach(handCard -> player.discardDeck.put(handCard));
        player.getTable().forEach(tableCard -> player.discardDeck.put(tableCard));
        player.clearHand();
        player.clearTable();
        player.drawNewHandCards();
    }

}
