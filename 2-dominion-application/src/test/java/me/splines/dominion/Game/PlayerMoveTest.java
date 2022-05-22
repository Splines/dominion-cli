package me.splines.dominion.Game;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.Action.Action;
import me.splines.dominion.Action.Instruction;
import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.CardPool;
import me.splines.dominion.Card.CardType;

public class PlayerMoveTest {

    @Mock
    private PlayerDecision playerDecision;

    @Mock
    private PlayerAbstract player;

    @BeforeEach
    void prepare() {
        MockitoAnnotations.openMocks(this);
        when(player.decision()).thenReturn(playerDecision);
    }

    @Test
    void noActionCardsOnHand() {
        PlayerMove move = new PlayerMove();
        move.doActionPhase(player);

        verify(player).getActionCardsOnHand();
        verify(playerDecision, only()).informNoActionCardsPlayable();
    }

    @Test
    void dontWantToPlayActionCards() {
        when(player.getActionCardsOnHand()).thenReturn(List.of(
                CardPool.actionCards.get(0),
                CardPool.actionCards.get(2)));
        when(playerDecision.chooseOptionalActionCard(anyList()))
                .thenReturn(Optional.empty());
        PlayerMove move = new PlayerMove();
        move.doActionPhase(player);

        verify(player, atMostOnce()).getActionCardsOnHand();
        verify(playerDecision, never()).informNoActionCardsPlayable();
    }

    @Test
    void playOneActionCard() {
        Instruction instr = mock(Instruction.class);
        Instruction instr2 = mock(Instruction.class);
        ActionCard playCard = new ActionCard("a", CardType.ACTION,
                1, new Action(instr, instr2));

        when(player.getActionCardsOnHand()).thenReturn(List.of(
                playCard, CardPool.actionCards.get(2)));
        when(playerDecision.chooseOptionalActionCard(anyList()))
                .thenReturn(Optional.of(playCard));
        PlayerMove move = new PlayerMove();
        move.doActionPhase(player);

        verify(player, atMostOnce()).getActionCardsOnHand();
        verify(playerDecision, never()).informNoActionCardsPlayable();
        verify(instr, only()).execute(any(), any(), any());
        verify(instr2, only()).execute(any(), any(), any());
    }

}
