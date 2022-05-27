package me.splines.dominion.instruction;

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

import me.splines.dominion.action.Instruction;
import me.splines.dominion.card.CardPool;
import me.splines.dominion.card.MoneyCard;
import me.splines.dominion.game.Deck;
import me.splines.dominion.game.GamePlayer;
import me.splines.dominion.game.GameStock;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.Player;
import me.splines.dominion.game.Stock;
import me.splines.dominion.interaction.PlayerDecision;
import me.splines.dominion.interaction.PlayerInformation;
import me.splines.dominion.interaction.PlayerInteraction;

class DisposeMoneyCardTakeMoneyCardTest {

    private Instruction instruction;
    private Deck drawDeck;

    @Mock
    private PlayerDecision decision;

    @Mock
    private PlayerInformation information;

    @Captor
    private ArgumentCaptor<List<MoneyCard>> moneyCardListCaptor;

    private Player player;

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

        PlayerInteraction interaction = new PlayerInteraction(decision, information);
        player = new GamePlayer("awesome player", interaction, drawDeck, new GameStock());
    }

    private void expectNoChangesToHand(Player player) {
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
        when(decision.chooseOptionalMoneyCard(any())).thenReturn(
                Optional.empty()); // dispose no card

        instruction.execute(player, new MoveState(), new GameStock());

        expectNoChangesToHand(player);
    }

    @Test
    void noMoneyCardsOnHand() {
        Player playerMock = mock(Player.class);
        when(playerMock.getMoneyCardsOnHand()).thenReturn(new ArrayList<>());
        when(decision.chooseOptionalMoneyCard(any())).thenReturn(
                Optional.of(CardPool.silverCard)); // dispose this card

        instruction.execute(playerMock, new MoveState(), new GameStock());

        expectNoChangesToHand(player);
    }

    @Test
    void newCardOnHandOldCardDisposed() {
        when(decision.chooseOptionalMoneyCard(any())).thenReturn(
                Optional.of(CardPool.silverCard)); // dispose this card
        when(decision.chooseMoneyCard(anyList())).thenReturn(
                CardPool.goldCard); // take this card

        instruction.execute(player, new MoveState(), new GameStock());

        assertThat(player.getHand())
                .doesNotContain(CardPool.silverCard)
                .contains(CardPool.goldCard);
    }

    @Test
    void canOnlyTakeMoneyCards() {
        when(decision.chooseOptionalMoneyCard(any())).thenReturn(
                Optional.of(CardPool.silverCard)); // dispose this card

        instruction.execute(player, new MoveState(), new GameStock());

        verify(decision).chooseMoneyCard(moneyCardListCaptor.capture());
        assertThat(moneyCardListCaptor.getValue())
                .containsExactlyInAnyOrderElementsOf(CardPool.moneyCards);

    }

    @Test
    void canOnlyTakeMoneyCardsThatCostMaxThreeMore() {
        when(decision.chooseOptionalMoneyCard(any())).thenReturn(
                Optional.of(CardPool.copperCard)); // dispose this card

        instruction.execute(player, new MoveState(), new GameStock());

        verify(decision).chooseMoneyCard(moneyCardListCaptor.capture());
        // no gold card here (!)
        assertThat(moneyCardListCaptor.getValue()).containsExactlyInAnyOrderElementsOf(
                List.of(CardPool.copperCard, CardPool.silverCard));
    }

    @Test
    void noMoneyCardsOnStock() {
        Stock stock = mock(GameStock.class);
        when(stock.getAvailableCardsWithMaxCosts(anyInt())).thenReturn(new ArrayList<>());

        when(decision.chooseOptionalMoneyCard(any())).thenReturn(
                Optional.of(CardPool.silverCard)); // dispose this card

        instruction.execute(player, new MoveState(), stock);

        expectNoChangesToHand(player);
    }

}
