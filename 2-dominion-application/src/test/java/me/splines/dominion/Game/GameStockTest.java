package me.splines.dominion.Game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.CardPool;
import me.splines.dominion.Card.CardStock.EmptyCardStockException;
import me.splines.dominion.Card.MoneyCard;
import me.splines.dominion.Game.Stock.NoCardStockForCardException;

public class GameStockTest {

    private Stock stock;

    @BeforeEach
    void prepare() {
        stock = new GameStock();
    }

    private void forceEmptyCopperCardStock() {
        int numberOfCopperCards = 60;
        for (int i = 0; i < numberOfCopperCards; i++) {
            stock.takeCard(CardPool.copperCard);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 2, 3, 7 })
    void cardsWithMaxCost(int maxCost) {
        assertThat(stock.getAvailableCardsWithMaxCosts(maxCost))
                .allSatisfy(card -> assertThat(card.getCost()).isBetween(0, maxCost));
    }

    @ParameterizedTest
    @ValueSource(ints = { 2, 3, 7 })
    void cardsWithMaxCostOneCardStockEmpty(int maxCost) {
        forceEmptyCopperCardStock();
        cardsWithMaxCost(maxCost);
    }

    @ParameterizedTest
    @ValueSource(ints = { 2, 3, 7 })
    void moneyCardsWithMaxCost(int maxCost) {
        assertThat(stock.getAvailableMoneyCardsWithMaxCosts(maxCost))
                .allSatisfy(card -> {
                    assertThat(card.getCost()).isBetween(0, maxCost);
                    assertThat(card).isInstanceOf(MoneyCard.class);
                });
    }

    @Test
    void getMoneyCards() {
        assertThat(stock.getAvailableMoneyCards()).allSatisfy(card -> {
            assertThat(card).isInstanceOf(MoneyCard.class);
        });
    }

    @Test
    void getMoneyCardsOneCardStockEmpty() {
        forceEmptyCopperCardStock();
        getMoneyCards();
    }

    @Test
    void takeCardFromExistingCardStockException() {
        Card specialCard = new MoneyCard("my money card", 42, 43);
        Throwable thrown = catchThrowable(() -> stock.takeCard(specialCard));
        assertThat(thrown)
                .isInstanceOf(NoCardStockForCardException.class)
                .hasMessageContaining(specialCard.toString());
    }

    @Test
    void takeCardFromEmptyCardStockException() {
        forceEmptyCopperCardStock();
        Throwable thrown = catchThrowable(() -> stock.takeCard(CardPool.copperCard));
        assertThat(thrown)
                .isInstanceOf(EmptyCardStockException.class)
                .hasMessageContaining(CardPool.copperCard.toString());
    }

}
