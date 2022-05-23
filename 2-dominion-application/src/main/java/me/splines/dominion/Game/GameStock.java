package me.splines.dominion.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.CardPool;
import me.splines.dominion.Card.CardStock;
import me.splines.dominion.Card.MoneyCard;

public class GameStock implements Stock {

    // Money card stocks
    private final CardStock<Card> copperCardStock = new CardStock<>(CardPool.copperCard, 60);
    private final CardStock<Card> silverCardStock = new CardStock<>(CardPool.silverCard, 40);
    private final CardStock<Card> goldCardStock = new CardStock<>(CardPool.goldCard, 30);
    private final List<CardStock<Card>> moneyCardStocks = List.of(copperCardStock, silverCardStock, goldCardStock);

    // Point card stocks
    private final CardStock<Card> provinceCardStock = new CardStock<>(CardPool.provinceCard, 12);
    private final CardStock<Card> duchyCardStock = new CardStock<>(CardPool.duchyCard, 12);
    private final CardStock<Card> estateCardStock = new CardStock<>(CardPool.estateCard, 24);
    private final List<CardStock<Card>> pointCardStocks = List.of(provinceCardStock, duchyCardStock, estateCardStock);

    // Action card stocks
    private final List<CardStock<Card>> actionCardStocks = CardPool.actionCards
            .stream()
            .map(actionCard -> new CardStock<Card>(actionCard, 10))
            .toList();

    private final List<CardStock<Card>> cardStocks = Stream
            .of(moneyCardStocks.stream(),
                    pointCardStocks.stream(),
                    actionCardStocks.stream())
            .flatMap(c -> c).toList();

    /////////////////////////////// Utility ////////////////////////////////////

    /**
     *
     * @param <T>        card type
     * @param cardStocks empty and/or non-empty card stocks
     * @return cards from non-empty card stocks
     */
    private <T extends Card> List<T> getCardsFromNonEmptyCardStocks(List<CardStock<T>> cardStocks) {
        List<T> cards = new ArrayList<>();
        for (CardStock<T> cardStock : cardStocks) {
            if (!cardStock.isEmpty()) {
                cards.add(cardStock.getCard());
            }
        }
        return cards;
    }

    private <T extends Card> List<T> getAvailableCardsWithMaxCostsGeneric(
            List<CardStock<T>> cardStocks, int maxCost) {
        List<T> cards = new ArrayList<>();
        for (CardStock<T> cardStock : cardStocks) {
            if (cardStock.isEmpty()) {
                continue;
            }
            T card = cardStock.getCard();
            if (card.getCost() <= maxCost) {
                cards.add(card);
            }
        }
        return cards;
    }

    ///////////////////////// Overwritten methods //////////////////////////////

    @Override
    public List<Card> getAvailableCardsWithMaxCosts(int maxCost) {
        return getAvailableCardsWithMaxCostsGeneric(cardStocks, maxCost);
    }

    @Override
    public List<MoneyCard> getAvailableMoneyCardsWithMaxCosts(int maxCosts) {
        return getAvailableCardsWithMaxCostsGeneric(moneyCardStocks, maxCosts)
                .stream().map(MoneyCard.class::cast).toList();
    }

    @Override
    public List<MoneyCard> getAvailableMoneyCards() {
        return getCardsFromNonEmptyCardStocks(moneyCardStocks)
                .stream().map(MoneyCard.class::cast).toList();
    }

    @Override
    public void takeCard(Card card) {
        CardStock<Card> cardStock = getCardStock(card);
        cardStock.takeOneCard();
    }

    @Override
    public CardStock<Card> getCardStock(Card card) {
        // TODO: greatly improve performance by using a map
        for (CardStock<Card> cardStock : cardStocks) {
            if (cardStock.getCard() == card)
                return cardStock;
        }
        throw new NoCardStockForCardException(card);
    }

    @Override
    public int getNumberOfEmptyCardStocks() {
        int emptyCount = 0;
        for (CardStock<Card> cardStock : cardStocks) {
            if (cardStock.isEmpty())
                emptyCount++;
        }
        return emptyCount;
    }

}
