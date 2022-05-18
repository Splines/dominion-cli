package me.splines.dominion.Game;

import java.util.List;

import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;

public interface StockAbstract {

    public List<MoneyCard> getAvailableMoneyCards();

    public List<Card> getAvailableCardsWithMaxCosts(int maxCosts);

}
