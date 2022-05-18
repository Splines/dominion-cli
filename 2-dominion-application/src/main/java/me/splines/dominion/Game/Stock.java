package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.CardPool;
import me.splines.dominion.Card.CardStock;
import me.splines.dominion.Card.MoneyCard;
import me.splines.dominion.Card.PointCard;

public class Stock implements StockAbstract {

    // Money card stocks
    private final CardStock<MoneyCard> copperCardStock = new CardStock<>(CardPool.copperCard, 60);
    private final CardStock<MoneyCard> silverCardStock = new CardStock<>(CardPool.silverCard, 40);
    private final CardStock<MoneyCard> goldCardStock = new CardStock<>(CardPool.goldCard, 30);
    private final List<CardStock<MoneyCard>> moneyCardStocks = List.of(copperCardStock, silverCardStock, goldCardStock);

    // Point card stocks
    private final CardStock<PointCard> provinceCardStock = new CardStock<>(CardPool.provinceCard, 12);
    private final CardStock<PointCard> duchyCardStock = new CardStock<>(CardPool.duchyCard, 12);
    private final CardStock<PointCard> estateCardStock = new CardStock<>(CardPool.estateCard, 24);
    private final List<CardStock<PointCard>> pointCardStocks = List.of(provinceCardStock, duchyCardStock,
            estateCardStock);

    // Action card stocks
    private final List<CardStock<ActionCard>> actionCardStocks = CardPool.actionCards
            .stream()
            .map(actionCard -> new CardStock<>(actionCard, 10))
            .collect(Collectors.toList());

    @Override
    public List<MoneyCard> getAvailableMoneyCards() {
        return getCardsFromNonEmptyCardStocks(moneyCardStocks);
    }

    /**
     *
     * @param <T>        card type
     * @param cardStocks empty and/or non-empty card stocks
     * @return cards from non-empty card stocks
     */
    private <T extends Card> List<T> getCardsFromNonEmptyCardStocks(
            List<CardStock<T>> cardStocks) {
        List<T> cards = new ArrayList<>();
        for (CardStock<T> cardStock : cardStocks) {
            if (!cardStock.isEmpty())
                cards.add(cardStock.getCard());
        }
        return cards;
    }

    @Override
    public List<Card> getAvailableCardsWithMaxCosts(int maxCost) {
        List<Card> cards = new ArrayList<>();
        cards.addAll(getAvailableCardsWithMaxCosts(moneyCardStocks, maxCost));
        cards.addAll(getAvailableCardsWithMaxCosts(pointCardStocks, maxCost));
        cards.addAll(getAvailableCardsWithMaxCosts(actionCardStocks, maxCost));
        return cards;
    }

    private <T extends Card> List<Card> getAvailableCardsWithMaxCosts(
            List<CardStock<T>> cardStocks, int maxCost) {
        List<Card> cards = new ArrayList<>();
        for (CardStock<T> cardStock : cardStocks) {
            if (cardStock.isEmpty()) {
                continue;
            }
            Card card = cardStock.getCard();
            if (card.getCost() <= maxCost) {
                cards.add(card);
            }
        }
        return cards;
    }

}
