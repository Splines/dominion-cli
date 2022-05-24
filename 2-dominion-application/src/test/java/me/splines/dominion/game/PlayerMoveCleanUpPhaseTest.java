package me.splines.dominion.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.card.Card;
import me.splines.dominion.card.CardPool;
import me.splines.dominion.interaction.PlayerDecision;
import me.splines.dominion.interaction.PlayerInformation;
import me.splines.dominion.interaction.PlayerInteraction;

class PlayerMoveCleanUpPhaseTest {

    @Mock
    private PlayerDecision decision;

    @Mock
    private PlayerInformation information;

    @BeforeEach
    void prepare() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void newHandCardsCleanedTable() {
        Deck drawDeck = new Deck();
        drawDeck.putCardSeveralTimes(CardPool.goldCard, 5);

        drawDeck.put(CardPool.curseCard);
        drawDeck.put(CardPool.estateCard);

        drawDeck.put(CardPool.duchyCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.silverCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.estateCard);

        Stock stock = new GameStock();
        PlayerInteraction interaction = new PlayerInteraction(decision, information);
        Player player = new Player("player", interaction, drawDeck, stock);
        player.draw();
        player.draw();
        player.play(CardPool.estateCard);
        player.play(CardPool.curseCard);

        PlayerMove move = new PlayerMove(player, stock);
        move.doCleanUpPhase();

        List<Card> nextRoundHandCards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nextRoundHandCards.add(CardPool.goldCard);
        }
        assertThat(player.getTable()).isEmpty();
        assertThat(player.getHand()).isEqualTo(nextRoundHandCards);
    }

}
