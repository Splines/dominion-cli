package me.splines.dominion.Instruction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.Action.Instruction;
import me.splines.dominion.Card.CardPool;
import me.splines.dominion.Game.Deck;
import me.splines.dominion.Game.GameStock;
import me.splines.dominion.Game.MoveState;
import me.splines.dominion.Game.Player;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.PlayerDecision;

public class DiscardAndDrawCardsInstructionTest {

    private Instruction instruction;
    private Deck drawDeck;

    @Mock
    private PlayerDecision playerDecision;

    private PlayerAbstract player;

    @BeforeEach
    void prepare() {
        instruction = new DiscardAndDrawCardsInstruction();

        drawDeck = new Deck();
        drawDeck.put(CardPool.provinceCard);
        drawDeck.put(CardPool.duchyCard); // ↑ other cards on bottom of deck
        drawDeck.put(CardPool.goldCard); // 5 cards until here
        drawDeck.put(CardPool.silverCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.curseCard);
        drawDeck.put(CardPool.estateCard);

        MockitoAnnotations.openMocks(this);

        player = new Player("awesome player", playerDecision, drawDeck);
    }

    @Test
    void discardAndDrawCardsInstruction() {
        when(playerDecision.chooseCards(anyList())).thenReturn(List.of(
                CardPool.copperCard,
                CardPool.curseCard));

        instruction.execute(player, new MoveState(), new GameStock());

        assertThat(player.getHand()).containsExactlyInAnyOrder(
                CardPool.estateCard,
                CardPool.silverCard,
                CardPool.goldCard,
                CardPool.duchyCard,
                CardPool.provinceCard);
    }

}
