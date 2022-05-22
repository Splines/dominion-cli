package me.splines.dominion.Instruction;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.Action.Instruction;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.CardPool;
import me.splines.dominion.Game.Deck;
import me.splines.dominion.Game.GameStock;
import me.splines.dominion.Game.MoveState;
import me.splines.dominion.Game.Player;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.PlayerDecision;

public class DrawCardsInstructionTest {

    private Deck drawDeck;

    @Mock
    private PlayerDecision playerDecision;

    private PlayerAbstract player;

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

        player = new Player("draw card player", playerDecision, drawDeck);
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

}
