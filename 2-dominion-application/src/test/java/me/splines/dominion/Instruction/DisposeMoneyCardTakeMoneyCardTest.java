package me.splines.dominion.Instruction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.Action.Instruction;
import me.splines.dominion.Card.CardPool;
import me.splines.dominion.Card.MoneyCard;
import me.splines.dominion.Game.Deck;
import me.splines.dominion.Game.GameStock;
import me.splines.dominion.Game.MoveState;
import me.splines.dominion.Game.Player;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.PlayerDecision;
import me.splines.dominion.Game.Stock;

public class DisposeMoneyCardTakeMoneyCardTest {

    private Instruction instruction;
    private Deck drawDeck;

    @Mock
    private PlayerDecision playerDecision;

    @Captor
    private ArgumentCaptor<List<MoneyCard>> moneyCardListCaptor;

    private PlayerAbstract player;

    @BeforeEach
    void prepare() {
        instruction = new DisposeMoneyCardTakeMoneyCardToHandInstruction();

        drawDeck = new Deck();
        drawDeck.put(CardPool.provinceCard);
        drawDeck.put(CardPool.duchyCard); // ↑ other cards on bottom of draw deck
        drawDeck.put(CardPool.goldCard); // 5 cards until here
        drawDeck.put(CardPool.silverCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.curseCard);
        drawDeck.put(CardPool.estateCard);

        MockitoAnnotations.openMocks(this);

        player = new Player("awesome player", playerDecision, drawDeck, new GameStock());
    }

    private void expectNoChangesToHand(PlayerAbstract player) {
        assertThat(player.getHand()).containsExactlyInAnyOrderElementsOf(
                List.of( // no changes to hand
                        CardPool.estateCard,
                        CardPool.curseCard,
                        CardPool.copperCard,
                        CardPool.silverCard,
                        CardPool.goldCard));
    }

    @Test
    void mayChooseACardToDisposeNoMust() {
        when(playerDecision.chooseOptionalMoneyCard(any())).thenReturn(
                Optional.empty()); // dispose no card

        instruction.execute(player, new MoveState(), new GameStock());

        expectNoChangesToHand(player);
    }

    @Test
    void noMoneyCardsOnHand() {
        PlayerAbstract playerMock = mock(PlayerAbstract.class);
        when(playerMock.getMoneyCardsOnHand()).thenReturn(new ArrayList<>());
        when(playerDecision.chooseOptionalMoneyCard(any())).thenReturn(
                Optional.of(CardPool.silverCard)); // dispose this card

        instruction.execute(playerMock, new MoveState(), new GameStock());

        expectNoChangesToHand(player);
    }

    @Test
    void newCardOnHandOldCardDisposed() {
        when(playerDecision.chooseOptionalMoneyCard(any())).thenReturn(
                Optional.of(CardPool.silverCard)); // dispose this card
        when(playerDecision.chooseMoneyCard(anyList())).thenReturn(
                CardPool.goldCard); // take this card

        instruction.execute(player, new MoveState(), new GameStock());

        assertThat(player.getHand())
                .doesNotContain(CardPool.silverCard)
                .contains(CardPool.goldCard);
    }

    @Test
    void canOnlyTakeMoneyCards() {
        when(playerDecision.chooseOptionalMoneyCard(any())).thenReturn(
                Optional.of(CardPool.silverCard)); // dispose this card

        instruction.execute(player, new MoveState(), new GameStock());

        verify(playerDecision).chooseMoneyCard(moneyCardListCaptor.capture());
        assertThat(moneyCardListCaptor.getValue())
                .containsExactlyInAnyOrderElementsOf(CardPool.moneyCards);

    }

    @Test
    void canOnlyTakeMoneyCardsThatCostMaxThreeMore() {
        when(playerDecision.chooseOptionalMoneyCard(any())).thenReturn(
                Optional.of(CardPool.copperCard)); // dispose this card

        instruction.execute(player, new MoveState(), new GameStock());

        verify(playerDecision).chooseMoneyCard(moneyCardListCaptor.capture());
        // no gold card here (!)
        assertThat(moneyCardListCaptor.getValue()).containsExactlyInAnyOrderElementsOf(
                List.of(CardPool.copperCard, CardPool.silverCard));
    }

    @Test
    void noMoneyCardsOnStock() {
        Stock stock = mock(GameStock.class);
        when(stock.getAvailableCardsWithMaxCosts(anyInt())).thenReturn(new ArrayList<>());

        when(playerDecision.chooseOptionalMoneyCard(any())).thenReturn(
                Optional.of(CardPool.silverCard)); // dispose this card

        instruction.execute(player, new MoveState(), stock);

        expectNoChangesToHand(player);
    }

}
