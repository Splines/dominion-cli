package me.splines.dominion.Game;

import java.util.List;

import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;

public interface Stock {

    public <T extends Card> List<T> getAvailableCardsWithMaxCosts(int maxCosts);

    public List<MoneyCard> getAvailableMoneyCards();

    public List<MoneyCard> getAvailableMoneyCardsWithMaxCosts(int maxCosts);

}
