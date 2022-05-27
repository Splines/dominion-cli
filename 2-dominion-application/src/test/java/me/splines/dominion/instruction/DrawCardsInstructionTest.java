package me.splines.dominion.instruction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
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

class DrawCardsInstructionTest {

    private Deck drawDeck;

    @Mock
    private PlayerDecision decision;

    @Mock
    private PlayerInformation information;

    private Player player;

    private List<Card> initialHand;

    @BeforeEach
    void prepare() {
        initialHand = List.of(
                CardPool.estateCard,
                CardPool.curseCard,
                CardPool.copperCard,
                CardPool.silverCard,
                CardPool.goldCard);

        drawDeck = new Deck();
        drawDeck.put(CardPool.silverCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.estateCard);
        drawDeck.put(CardPool.provinceCard);
        drawDeck.put(CardPool.duchyCard); // ↑ other cards on bottom of deck
        for (Card card : initialHand) { // 5 initial cards
            drawDeck.put(card);
        }

        MockitoAnnotations.openMocks(this);
        PlayerInteraction interaction = new PlayerInteraction(decision, information);
        player = new GamePlayer("draw card player", interaction, drawDeck, new GameStock());
    }

    @Test
    void drawFourCards() {
        Instruction instruction = new DrawCardsInstruction(4);

        instruction.execute(player, new MoveState(), new GameStock());

        List<Card> expectedHand = new ArrayList<>();
        for (Card card : initialHand) {
            expectedHand.add(card);
        }
        // Three new cards
        expectedHand.add(CardPool.duchyCard);
        expectedHand.add(CardPool.provinceCard);
        expectedHand.add(CardPool.estateCard);
        expectedHand.add(CardPool.copperCard);
        assertThat(player.getHand())
                .containsExactlyInAnyOrderElementsOf(expectedHand);
    }

    @Test
    void drawNoNegativeNumberOfCards() {
        Throwable thrown = catchThrowable(
                () -> new DrawCardsInstruction(-42));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("draw")
                .hasMessageContaining("negative");
    }

}
