package me.splines.dominion.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.card.Card;
import me.splines.dominion.card.CardPool;

class PlayerMoveBuyingPhaseTest {

    @Mock
    private PlayerDecision playerDecision;

    @Captor
    private ArgumentCaptor<List<Card>> boughtCardsCaptor;

    @BeforeEach
    void prepare() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buyOneCard() {
        Deck drawDeck = new Deck();
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard); // ↑ 4 additional cards for next round
        drawDeck.put(CardPool.duchyCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard);
        Stock stock = new GameStock();
        Player player = spy(new Player("player", playerDecision, drawDeck, stock));
        when(player.decision()).thenReturn(playerDecision);
        Card cardToBuy = CardPool.estateCard;
        when(playerDecision.chooseOptionalCardToBuy(anyList()))
                .thenReturn(Optional.of(cardToBuy));

        PlayerMove move = new PlayerMove(player, stock);
        move.doBuyPhase();

        verify(playerDecision).informStartBuyingPhase();
        verify(playerDecision).chooseOptionalCardToBuy(boughtCardsCaptor.capture());
        verifyNoMoreInteractions(playerDecision);
        int maxCost = CardPool.copperCard.getMoney() * 4;
        assertThat(boughtCardsCaptor.getValue())
                .hasSizeGreaterThanOrEqualTo(3) // at least: copper, silver, estate
                .allSatisfy(card -> assertThat(card.getCost())
                        .isBetween(0, maxCost));

        assertThat(player.drawNewHandCards()).contains(cardToBuy);
    }

    @Test
    void buyMultipleCards() {
        Deck drawDeck = new Deck();
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard); // ↑ 3 additional cards for next round
        drawDeck.put(CardPool.duchyCard);
        drawDeck.put(CardPool.silverCard);
        drawDeck.put(CardPool.silverCard);
        drawDeck.put(CardPool.silverCard);
        drawDeck.put(CardPool.copperCard);
        Stock stock = new GameStock();
        Player player = spy(new Player("player", playerDecision, drawDeck, stock));
        when(player.decision()).thenReturn(playerDecision);
        when(playerDecision.chooseOptionalCardToBuy(anyList()))
                .thenReturn(Optional.of(CardPool.duchyCard))
                .thenReturn(Optional.of(CardPool.estateCard));

        class MyPlayerMove extends PlayerMove {

            public MyPlayerMove(PlayerAbstract player, Stock stock) {
                super(player, stock);
            }

            public void earnBuying(int buyingsCount) {
                moveState.earnBuyings(buyingsCount);
            }

        }

        MyPlayerMove move = new MyPlayerMove(player, stock);
        move.earnBuying(1); // total now: 2 buyings
        move.doBuyPhase();

        verify(playerDecision, times(2))
                .chooseOptionalCardToBuy(boughtCardsCaptor.capture());
        int maxCostFirstBuy = CardPool.silverCard.getMoney() * 3
                + CardPool.copperCard.getMoney();
        assertThat(boughtCardsCaptor.getAllValues().get(0))
                .allSatisfy(card -> assertThat(card.getCost())
                        .isBetween(0, maxCostFirstBuy));
        int maxCostSecondBuy = maxCostFirstBuy - CardPool.duchyCard.getCost();
        assertThat(boughtCardsCaptor.getAllValues().get(1))
                .allSatisfy(card -> assertThat(card.getCost())
                        .isBetween(0, maxCostSecondBuy));

        assertThat(player.drawNewHandCards())
                .contains(CardPool.estateCard, CardPool.duchyCard);
    }

    @Test
    void noCardsBuyable() {
        Deck drawDeck = new Deck();
        drawDeck.put(CardPool.duchyCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.duchyCard);
        drawDeck.put(CardPool.duchyCard);
        drawDeck.put(CardPool.duchyCard);
        Stock stock = mock(GameStock.class);
        Player player = spy(new Player("poor player", playerDecision, drawDeck, stock));
        when(player.decision()).thenReturn(playerDecision);

        when(stock.getAvailableCardsWithMaxCosts(anyInt()))
                .thenReturn(Collections.emptyList());

        PlayerMove move = new PlayerMove(player, stock);
        move.doBuyPhase();

        verify(stock, only()).getAvailableCardsWithMaxCosts(anyInt());
        verify(playerDecision).informStartBuyingPhase();
        verify(playerDecision).informNoCardsBuyableWithMoney(1);
        verifyNoMoreInteractions(playerDecision);
    }

    @Test
    void dontWantToBuyCard() {
        Deck drawDeck = new Deck();
        drawDeck.put(CardPool.duchyCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.duchyCard);
        drawDeck.put(CardPool.duchyCard);
        drawDeck.put(CardPool.duchyCard);
        Stock stock = spy(new GameStock());
        Player player = spy(new Player("poor player", playerDecision, drawDeck, stock));
        when(player.decision()).thenReturn(playerDecision);
        when(playerDecision.chooseOptionalCardToBuy(anyList()))
                .thenReturn(Optional.empty());

        PlayerMove move = new PlayerMove(player, stock);
        move.doBuyPhase();

        verify(stock, only()).getAvailableCardsWithMaxCosts(anyInt());
        verify(playerDecision).informStartBuyingPhase();
        verify(playerDecision).chooseOptionalCardToBuy(anyList());
        verifyNoMoreInteractions(playerDecision);
    }

}
