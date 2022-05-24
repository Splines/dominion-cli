package me.splines.dominion.game;

import java.util.List;
import java.util.Optional;

import me.splines.dominion.card.ActionCard;
import me.splines.dominion.card.Card;
import me.splines.dominion.card.MoneyCard;

public interface PlayerDecision {

    Card chooseCard(List<Card> cards);

    void informStartActionPhase();

    void informNoActionCardsPlayable();

    void informNoCardsBuyableWithMoney(int money);

    void announceResults(List<PlayerResult> results);

    void announceWinners(String... names);

    void informYourTurn(String name);

    List<Card> chooseCards(List<Card> cards);

    ActionCard chooseActionCard(List<ActionCard> cards);

    Optional<ActionCard> chooseOptionalActionCard(List<ActionCard> cards);

    MoneyCard chooseMoneyCard(List<MoneyCard> cards);

    List<Card> chooseCardsToBuy(List<Card> cards);

    Optional<Card> chooseOptionalCardToBuy(List<Card> cards);

    Optional<MoneyCard> chooseOptionalMoneyCard(List<MoneyCard> cards);

}
