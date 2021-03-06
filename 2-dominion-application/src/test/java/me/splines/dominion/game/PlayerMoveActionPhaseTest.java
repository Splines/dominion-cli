package me.splines.dominion.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.action.Action;
import me.splines.dominion.action.Instruction;
import me.splines.dominion.card.ActionCard;
import me.splines.dominion.card.CardPool;
import me.splines.dominion.card.CardType;
import me.splines.dominion.interaction.PlayerDecision;
import me.splines.dominion.interaction.PlayerInformation;
import me.splines.dominion.interaction.PlayerInteraction;

class PlayerMoveActionPhaseTest {

    @Mock
    private PlayerDecision decision;

    @Mock
    private PlayerInformation information;

    @Mock
    private Player player;

    private final Instruction earnActionInstruction = new Instruction() {
        @Override
        public void execute(Player player, MoveState moveState, Stock stock) {
            moveState.earnActions(1);
        }

        public String getName() {
            return "earn an instruction and give yourself a pat on the back";
        };
    };

    @Captor
    private ArgumentCaptor<List<ActionCard>> actionCardListCaptor;

    @BeforeEach
    void prepare() {
        MockitoAnnotations.openMocks(this);
        when(player.decide()).thenReturn(decision);
        when(player.inform()).thenReturn(information);
    }

    @Test
    void noActionCardsOnHand() {
        PlayerMove move = new PlayerMove(player, new GameStock());
        move.doActionPhase();

        verify(player).getActionCardsOnHand();
        verify(information).startActionPhase();
        verify(information).noActionCardsPlayable();
        verifyNoMoreInteractions(information);
        verifyNoMoreInteractions(decision);
    }

    @Test
    void dontWantToPlayActionCards() {
        when(player.getActionCardsOnHand()).thenReturn(List.of(
                CardPool.actionCards.get(0),
                CardPool.actionCards.get(2)));
        when(decision.chooseOptionalActionCard(anyList()))
                .thenReturn(Optional.empty());
        PlayerMove move = new PlayerMove(player, new GameStock());
        move.doActionPhase();

        verify(player, atMostOnce()).getActionCardsOnHand();
        verify(information, never()).noActionCardsPlayable();
    }

    @Test
    void playOneActionCard() {
        Instruction instr = mock(Instruction.class);
        Instruction instr2 = mock(Instruction.class);
        ActionCard playCard = new ActionCard("a",
                CardType.ACTION, 1, new Action(instr, instr2));

        when(player.getActionCardsOnHand()).thenReturn(List.of(
                playCard, CardPool.actionCards.get(2)));
        when(decision.chooseOptionalActionCard(anyList()))
                .thenReturn(Optional.of(playCard));
        PlayerMove move = new PlayerMove(player, new GameStock());
        move.doActionPhase();

        verify(player, atMostOnce()).getActionCardsOnHand();
        verify(information, never()).noActionCardsPlayable();
        verify(instr, only()).execute(any(), any(), any());
        verify(instr2, only()).execute(any(), any(), any());
    }

    @Test
    void playTwoActionCards() {
        Instruction instr = mock(Instruction.class);
        Instruction instr2 = spy(earnActionInstruction);
        ActionCard playCard = new ActionCard("action card 1",
                CardType.ACTION, 1, new Action(instr, instr2));

        Instruction instr3 = mock(Instruction.class);
        Instruction instr4 = mock(Instruction.class);
        ActionCard otherPlayCard = new ActionCard("action card 2",
                CardType.ACTION, 2, new Action(instr3, instr4));

        when(player.getActionCardsOnHand())
                .thenReturn(List.of(playCard, otherPlayCard))
                .thenReturn(List.of(otherPlayCard)); // stub consecutive call
        when(decision.chooseOptionalActionCard(anyList()))
                .thenReturn(Optional.of(playCard))
                .thenReturn(Optional.of(otherPlayCard));
        PlayerMove move = new PlayerMove(player, new GameStock());
        move.doActionPhase();

        verify(player, times(2)).getActionCardsOnHand();
        verify(decision, times(2)).chooseOptionalActionCard(any());
        verify(information, never()).noActionCardsPlayable();
        verify(instr, only()).execute(any(), any(), any());
        verify(instr2, only()).execute(any(), any(), any());
        verify(instr3, only()).execute(any(), any(), any());
        verify(instr4, only()).execute(any(), any(), any());
    }

    @Test
    void cantPlaySameActionCardMultipleTimes() {
        Instruction instr = mock(Instruction.class);
        Instruction instr2 = spy(earnActionInstruction);
        ActionCard playCard = new ActionCard("a",
                CardType.ACTION, 1, new Action(instr, instr2));

        Deck drawDeck = new Deck();
        drawDeck.put(playCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard);
        PlayerInteraction interaction = new PlayerInteraction(decision, information);
        GamePlayer ourPlayer = spy(new GamePlayer("our player", interaction, drawDeck, new GameStock()));

        when(decision.chooseOptionalActionCard(anyList()))
                .thenReturn(Optional.of(playCard));
        PlayerMove move = new PlayerMove(ourPlayer, new GameStock());
        move.doActionPhase();

        verify(ourPlayer, times(2)).getActionCardsOnHand();
        verify(decision, times(1))
                .chooseOptionalActionCard(actionCardListCaptor.capture());
        assertThat(actionCardListCaptor.getValue()).isEqualTo(List.of(playCard));
    }

}
