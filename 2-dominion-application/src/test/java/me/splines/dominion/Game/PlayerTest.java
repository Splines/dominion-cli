package me.splines.dominion.Game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.Card.ActionCard;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.CardPool;
import me.splines.dominion.Card.MoneyCard;
import me.splines.dominion.Game.Deck.EmptyDeckException;
import me.splines.dominion.Game.Deck.NotEnoughCardsOnDeckException;
import me.splines.dominion.Game.PlayerAbstract.HandDoesNotHaveCard;

class PlayerTest {

    private Deck drawDeck;
    private List<Card> initialHand;

    private Card specialCard = new MoneyCard("my", 42, 180);

    @Mock
    private PlayerDecision playerDecision;

    private PlayerAbstract player;

    @BeforeEach
    void prepare() {
        initialHand = List.of(
                CardPool.estateCard,
                CardPool.curseCard,
                CardPool.copperCard,
                CardPool.silverCard,
                CardPool.goldCard);

        drawDeck = new Deck();
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.copperCard);
        drawDeck.put(CardPool.estateCard);
        drawDeck.put(CardPool.provinceCard);
        drawDeck.put(CardPool.duchyCard); // ↑ other cards on bottom of deck
        for (Card card : initialHand) { // 5 initial cards
            drawDeck.put(card);
        }

        MockitoAnnotations.openMocks(this);
        player = new Player("draw card player", playerDecision, drawDeck, new GameStock());
    }

    ////////////////////////////// Move ////////////////////////////////////////

    @Test
    void playerMove() {
        player.makeMove();

        verify(playerDecision).informYourTurn(player.name);
        // Would need Powermock with JUnit5 to test order
        // -> see here: https://stackoverflow.com/a/30137217/9655481
        // Sadly, Powermock is still not yet available for JUnit5
        // -> see here: https://github.com/powermock/powermock/issues/929
        // InOrder orderVerifier = inOrder(playerMove);
        // orderVerifier.verify(playerMove).doActionPhase();
        // orderVerifier.verify(playerMove).doBuyPhase();
        // orderVerifier.verify(playerMove).doCleanUpPhase();
        // orderVerifier.verifyNoMoreInteractions();
    }

    ////////////////////////////// Draw cards //////////////////////////////////

    @Test
    void construction() {
        assertThat(player.getHand())
                .containsExactlyInAnyOrderElementsOf(initialHand);
    }

    @Test
    void draw() {
        assertThat(player.draw()).isEqualTo(CardPool.duchyCard);
        assertThat(player.draw()).isEqualTo(CardPool.provinceCard);
        assertThat(player.draw()).isEqualTo(CardPool.estateCard);
        assertThat(player.draw()).isEqualTo(CardPool.copperCard);
        assertThat(player.draw()).isEqualTo(CardPool.copperCard);
    }

    @Test
    void drawWithNewDeck() {
        player.discardDeck.put(specialCard);
        player.drawNewHandCards();
        assertThat(player.draw()).isEqualTo(specialCard);
    }

    @Test
    void emptyDeckNoCardsToRefillException() {
        player.drawNewHandCards(); // initial + 5 more cards used
        Throwable thrown = catchThrowable(() -> player.draw());
        assertThat(thrown).isInstanceOf(EmptyDeckException.class);
    }

    @Test
    void drawNewHandCardsException() {
        player.drawNewHandCards(); // initial + 5 more cards used
        player.drawDeck.put(specialCard);
        player.discardDeck.put(specialCard);
        player.discardDeck.put(specialCard);
        Throwable thrown = catchThrowable(() -> player.drawNewHandCards());
        assertThat(thrown)
                .isInstanceOf(NotEnoughCardsOnDeckException.class)
                .hasMessageContaining("Draw deck")
                .hasMessageContaining("1")
                .hasMessageContaining("discard deck")
                .hasMessageContaining("2");
    }

    //////////////////////////// Hand & Table //////////////////////////////////

    @Test
    void discard() {
        player.drawNewHandCards(); // initial + 5 more cards used
        player.discard(CardPool.provinceCard);
        assertThat(player.getHand()).doesNotContain(CardPool.provinceCard);
        assertThat(player.draw()).isEqualTo(CardPool.provinceCard);
    }

    @Test
    void dispose() {
        player.drawNewHandCards(); // initial + 5 more cards used
        player.dispose(CardPool.provinceCard);
        Throwable thrown = catchThrowable(() -> player.draw());
        assertThat(player.getHand()).doesNotContain(CardPool.provinceCard);
        assertThat(thrown).isInstanceOf(EmptyDeckException.class);
    }

    @Test
    void disposeCardNotInHandException() {
        Throwable thrown = catchThrowable(() -> player.dispose(CardPool.provinceCard));
        assertThat(thrown)
                .isInstanceOf(HandDoesNotHaveCard.class)
                .hasMessageContaining("not present")
                .hasMessageContaining("hand");
    }

    @Test
    void play() {
        player.drawNewHandCards(); // initial + 5 more cards used
        player.play(CardPool.provinceCard);
        assertThat(player.getHand()).doesNotContain(CardPool.provinceCard);
        assertThat(player.getTable()).contains(CardPool.provinceCard);
    }

    @Test
    void take() {
        player.drawNewHandCards(); // initial + 5 more cards used
        player.take(specialCard);
        assertThat(player.draw()).isEqualTo(specialCard);
    }

    @Test
    void takeToHand() {
        player.takeToHand(specialCard);
        assertThat(player.getHand()).contains(specialCard);
    }

    @Test
    void getHandUnmodifiable() {
        List<Card> hand = player.getHand();
        Throwable thrown = catchThrowable(() -> hand.add(specialCard));
        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void clearHand() {
        player.clearHand();
        assertThat(player.getHand()).isEmpty();
    }

    @Test
    void getActionCardsOnHand() {
        ActionCard actionCard = CardPool.actionCards.get(0);
        player.hand.add(actionCard);
        assertThat(player.getActionCardsOnHand()).isEqualTo(List.of(actionCard));
    }

    @Test
    void getMoneyCardsOnHand() {
        player.hand.add(CardPool.goldCard);
        assertThat(player.getMoneyCardsOnHand())
                .containsExactlyInAnyOrder(
                        CardPool.copperCard, CardPool.silverCard,
                        CardPool.goldCard, CardPool.goldCard);
    }

    @Test
    void getTableUnmodifiable() {
        List<Card> table = player.getTable();
        Throwable thrown = catchThrowable(() -> table.add(specialCard));
        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void clearTable() {
        player.clearTable();
        assertThat(player.getTable()).isEmpty();
    }

    ///////////////////////////////// Other ////////////////////////////////////

    @Test
    void calculatePoints() {
        player.draw();
        player.discard(CardPool.estateCard);
        int points = 2 * CardPool.estateCard.getPoints()
                + CardPool.curseCard.getCurse()
                + CardPool.provinceCard.getPoints()
                + CardPool.duchyCard.getPoints();
        assertThat(player.calculatePoints()).isEqualTo(points);
    }

}
