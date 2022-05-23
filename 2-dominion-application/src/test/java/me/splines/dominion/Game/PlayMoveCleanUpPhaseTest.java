package me.splines.dominion.Game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.CardPool;

public class PlayMoveCleanUpPhaseTest {

    @Mock
    private PlayerDecision playerDecision;

    @BeforeEach
    void prepare() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void newHandCardsCleanedTable() {
        Deck drawDeck = new Deck();
        drawDeck.putCardSeveralTimes(CardPool.goldCard, 5);
        drawDeck.put(CardPool.duchyCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.silverCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.estateCard);

        Stock stock = new GameStock();
        Player player = new Player("player", playerDecision, drawDeck, stock);
        player.table.add(CardPool.estateCard);
        player.table.add(CardPool.curseCard);

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
