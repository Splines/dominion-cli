package me.splines.dominion.instruction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.action.Instruction;
import me.splines.dominion.card.Card;
import me.splines.dominion.card.CardPool;
import me.splines.dominion.game.Deck;
import me.splines.dominion.game.GamePlayer;
import me.splines.dominion.game.GameStock;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.Player;
import me.splines.dominion.interaction.PlayerDecision;
import me.splines.dominion.interaction.PlayerInformation;
import me.splines.dominion.interaction.PlayerInteraction;

class EarnActionsInstructionTest {

    private Deck drawDeck;

    @Mock
    private PlayerDecision decision;

    @Mock
    private PlayerInformation information;

    private Player player;

    @BeforeEach
    void prepare() {
        List<Card> initialHand = List.of(
                CardPool.estateCard,
                CardPool.curseCard,
                CardPool.copperCard,
                CardPool.silverCard,
                CardPool.goldCard);
        drawDeck = new Deck();
        for (Card card : initialHand) { // 5 initial cards
            drawDeck.put(card);
        }

        MockitoAnnotations.openMocks(this);

        PlayerInteraction interaction = new PlayerInteraction(decision, information);
        player = new GamePlayer("action player", interaction, drawDeck, new GameStock());
    }

    @Test
    void earn42Actions() {
        int earnActions = 42;
        Instruction instruction = new EarnActionsInstruction(earnActions);
        MoveState moveState = new MoveState();
        int actionsCountInitial = moveState.getActionsCount();

        instruction.execute(player, moveState, new GameStock());

        assertThat(moveState.getActionsCount())
                .isEqualTo(actionsCountInitial + earnActions);
    }

    @Test
    void earnNoNegativeActions() {
        Throwable thrown = catchThrowable(
                () -> new EarnActionsInstruction(-42));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("action")
                .hasMessageContaining("negative");
    }

}
