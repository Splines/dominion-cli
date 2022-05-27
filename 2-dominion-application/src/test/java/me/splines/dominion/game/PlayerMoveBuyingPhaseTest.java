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
import me.splines.dominion.interaction.PlayerDecision;
import me.splines.dominion.interaction.PlayerInformation;
import me.splines.dominion.interaction.PlayerInteraction;

class PlayerMoveBuyingPhaseTest {

    private PlayerInteraction interaction;

    @Mock
    private PlayerDecision decision;

    @Mock
    private PlayerInformation information;

    @Captor
    private ArgumentCaptor<List<Card>> boughtCardsCaptor;

    @BeforeEach
    void prepare() {
        interaction = new PlayerInteraction(decision, information);
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
        GamePlayer player = spy(new GamePlayer("player", interaction, drawDeck, stock));
        when(player.decide()).thenReturn(decision);
        when(player.inform()).thenReturn(information);
        Card cardToBuy = CardPool.estateCard;
        when(decision.chooseOptionalCardToBuy(anyList()))
                .thenReturn(Optional.of(cardToBuy));

        PlayerMove move = new PlayerMove(player, stock);
        move.doBuyPhase();

        verify(information).startBuyingPhase();
        verify(decision).chooseOptionalCardToBuy(boughtCardsCaptor.capture());
        verifyNoMoreInteractions(decision);
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
        GamePlayer player = spy(new GamePlayer("player", interaction, drawDeck, stock));
        when(player.decide()).thenReturn(decision);
        when(player.inform()).thenReturn(information);
        when(decision.chooseOptionalCardToBuy(anyList()))
                .thenReturn(Optional.of(CardPool.duchyCard))
                .thenReturn(Optional.of(CardPool.estateCard));

        class MyPlayerMove extends PlayerMove {

            public MyPlayerMove(Player player, Stock stock) {
                super(player, stock);
            }

            public void earnBuying(int buyingsCount) {
                moveState.earnBuyings(buyingsCount);
            }

        }

        MyPlayerMove move = new MyPlayerMove(player, stock);
        move.earnBuying(1); // total now: 2 buyings
        move.doBuyPhase();

        verify(decision, times(2))
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
        GamePlayer player = spy(new GamePlayer("poor player", interaction, drawDeck, stock));
        when(player.decide()).thenReturn(decision);
        when(player.inform()).thenReturn(information);

        when(stock.getAvailableCardsWithMaxCosts(anyInt()))
                .thenReturn(Collections.emptyList());

        PlayerMove move = new PlayerMove(player, stock);
        move.doBuyPhase();

        verify(stock, only()).getAvailableCardsWithMaxCosts(anyInt());
        verify(information).startBuyingPhase();
        verify(information).noCardsBuyableWithMoney(1);
        verifyNoMoreInteractions(information);
        verifyNoMoreInteractions(decision);
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
        GamePlayer player = spy(new GamePlayer("poor player", interaction, drawDeck, stock));
        when(player.decide()).thenReturn(decision);
        when(player.inform()).thenReturn(information);
        when(decision.chooseOptionalCardToBuy(anyList()))
                .thenReturn(Optional.empty());

        PlayerMove move = new PlayerMove(player, stock);
        move.doBuyPhase();

        verify(stock, only()).getAvailableCardsWithMaxCosts(anyInt());
        verify(information).startBuyingPhase();
        verify(decision).chooseOptionalCardToBuy(anyList());
        verifyNoMoreInteractions(information);
        verifyNoMoreInteractions(decision);
    }

}
