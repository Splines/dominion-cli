package me.splines.dominion.game;

import java.util.List;

import me.splines.dominion.card.Card;
import me.splines.dominion.card.CardStock;
import me.splines.dominion.card.MoneyCard;

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

    public CardStock<Card> getCardStock(Card card);

    public int getNumberOfEmptyCardStocks();

}
