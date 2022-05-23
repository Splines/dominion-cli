package me.splines.dominion.Game;

import java.util.List;
import java.util.Optional;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;

public interface PlayerDecision {

    Card chooseCard(List<Card> cards);

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
