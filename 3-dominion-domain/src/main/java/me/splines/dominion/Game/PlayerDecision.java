package me.splines.dominion.Game;

import java.util.List;
import java.util.Optional;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;

public interface PlayerDecision {

    List<Card> chooseCards(List<Card> cards);

    ActionCard chooseActionCard(List<Card> cards);

    MoneyCard chooseMoneyCard(List<MoneyCard> cards);

    Optional<MoneyCard> chooseOptionalMoneyCard(List<MoneyCard> cards);

}
