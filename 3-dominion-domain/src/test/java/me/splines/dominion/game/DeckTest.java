package me.splines.dominion.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import me.splines.dominion.card.Card;
import me.splines.dominion.card.CurseCard;
import me.splines.dominion.card.MoneyCard;
import me.splines.dominion.card.PointCard;
import me.splines.dominion.game.Deck.EmptyDeckException;

class DeckTest {

    Card[] mockCards = {
            new PointCard("Point Card", 42, 43),
            new MoneyCard("Money Card", 2, 3),
            new CurseCard("Curse Card", 5, -50),
            new MoneyCard("Money Card2", 3, 4)
            // TODO add more mock cards, e.g. Action cards later
    };

    @Test
    void putDrawSameCard() {
        Deck deck = new Deck();
        Card mockCard = mockCards[0];

        // Put and draw one card
        deck.put(mockCard);
        Card drawnCard = deck.draw();

        assertThat(drawnCard)
                .isSameAs(mockCard)
                .isEqualTo(mockCard);
    }

    @Test
    void putAndDrawSeveralCards() {
        Deck deck = new Deck();

        // Put two cards
        deck.put(mockCards[0]);
        deck.put(mockCards[1]);

        // Draw one card
        Card drawnCard = deck.draw();
        assertThat(drawnCard).isSameAs(mockCards[1]);

        // Draw another card
        drawnCard = deck.draw();
        assertThat(drawnCard).isSameAs(mockCards[0]);

        // Empty
        Throwable thrown = catchThrowable(() -> deck.draw());
        assertThat(thrown).isInstanceOf(EmptyDeckException.class);

        // Put one card, immediately draw it
        deck.put(mockCards[2]);
        drawnCard = deck.draw();
        assertThat(drawnCard).isSameAs(mockCards[2]);

        // Empty
        thrown = catchThrowable(() -> deck.draw());
        assertThat(thrown).isInstanceOf(EmptyDeckException.class);
    }

    @Test
    void drawFromEmptyDeck() {
        Deck deck = new Deck();
        testEmpty(deck);
    }

    private void testEmpty(Deck deck) {
        Throwable thrown = catchThrowable(() -> deck.draw());
        assertThat(thrown)
                .isInstanceOf(EmptyDeckException.class)
                .hasMessageContaining("empty")
                .hasMessageContaining("Deck");
    }

    @Test
    void putMultipleCardsOnDeck() {
        Deck deck = new Deck();
        Card card = this.mockCards[0];
        int count = 10;
        deck.putCardSeveralTimes(card, count);

        for (int i = 0; i < count; i++) {
            assertThat(deck.draw()).isEqualTo(card);
        }
        testEmpty(deck);
    }

    @Test
    void shuffleDeck() {
        List<List<Integer>> shuffledOrders = new ArrayList<>();
        List<Card> mockCards = Arrays.asList(this.mockCards);

        // Construct deck, shuffle it and save new order of cards
        for (int i = 0; i < 6; i++) {
            Deck deck = new Deck();
            deck.put(this.mockCards[0]);
            deck.put(this.mockCards[2]);
            deck.put(this.mockCards[1]);
            deck.put(this.mockCards[1]);
            deck.put(this.mockCards[2]);
            deck.put(this.mockCards[0]);
            deck.put(this.mockCards[1]);
            deck.put(this.mockCards[3]);

            deck.shuffle();

            List<Integer> indices = new ArrayList<>();
            for (int j = 0; j < this.mockCards.length; j++) {
                Card drawnCard = deck.draw();
                int index = mockCards.indexOf(drawnCard);
                indices.add(index);
            }
            shuffledOrders.add(indices);
        }

        // Out of 5 trials, the first one must differ from at least
        // three other trials, or else our shuffle algorithm is really bad
        List<Integer> compareOrder = shuffledOrders.get(0);
        int differenceCount = 0;
        for (int i = 1; i < shuffledOrders.size(); i++) {
            List<Integer> otherOrder = shuffledOrders.get(i);
            if (!otherOrder.equals(compareOrder)) {
                differenceCount++;
            }
        }
        assertThat(differenceCount).isGreaterThanOrEqualTo(3);
    }

}
