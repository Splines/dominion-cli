package me.splines.dominion.Game;

import java.util.List;

import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.MoneyCard;

public interface Stock {

    public static class NoCardStockForCardException extends RuntimeException {
        public NoCardStockForCardException(Card card) {
            super("No card stock could be found for card: " + card);
        }
    }

    public List<Card> getAvailableCardsWithMaxCosts(int maxCosts);

    public List<MoneyCard> getAvailableMoneyCards();

    public List<MoneyCard> getAvailableMoneyCardsWithMaxCosts(int maxCosts);

    public void takeCard(Card card);

}
