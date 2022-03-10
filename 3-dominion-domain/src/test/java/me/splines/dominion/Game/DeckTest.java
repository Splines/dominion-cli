package me.splines.dominion.Game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.CardType;
import me.splines.dominion.Card.ValueCard;
import me.splines.dominion.Game.Deck.EmptyDeckException;

class DeckTest {

    Card[] mockCards = {
            new ValueCard("Value Card Name1", CardType.POINTS, 42, 43),
            new ValueCard("Value Card Name2", CardType.MONEY, 2, 3),
            new ValueCard("Value Card Name3", CardType.CURSE, 5, -50)
            // TODO add more mock cards, e.g. Action cards later
    };

    @Test
    void putCardDrawSameCard() {
        Deck deck = new Deck();
        Card mockCard = mockCards[0];

        // Put and draw one card
        deck.putCard(mockCard);
        Card drawedCard = deck.drawCard();

        assertThat(drawedCard)
                .isSameAs(mockCard)
                .isEqualTo(mockCard);
    }

    @Test
    void putAndDrawMultipleCards() {
        Deck deck = new Deck();

        // Put two cards
        deck.putCard(mockCards[0]);
        deck.putCard(mockCards[1]);

        // Draw one card
        Card drawedCard = deck.drawCard();
        assertThat(drawedCard).isSameAs(mockCards[1]);

        // Draw another card
        drawedCard = deck.drawCard();
        assertThat(drawedCard).isSameAs(mockCards[0]);

        // Empty
        Throwable thrown = catchThrowable(() -> deck.drawCard());
        assertThat(thrown).isInstanceOf(EmptyDeckException.class);

        // Put one card, immediately draw it
        deck.putCard(mockCards[2]);
        drawedCard = deck.drawCard();
        assertThat(drawedCard).isSameAs(mockCards[2]);

        // Empty
        thrown = catchThrowable(() -> deck.drawCard());
        assertThat(thrown).isInstanceOf(EmptyDeckException.class);
    }

    @Test
    void drawFromEmptydeck() {
        Deck deck = new Deck();
        Throwable thrown = catchThrowable(() -> deck.drawCard());
        assertThat(thrown)
                .isInstanceOf(EmptyDeckException.class)
                .hasMessageContaining("empty")
                .hasMessageContaining("Deck");
    }

    @Test
    void shuffleDeck() {
        List<List<Integer>> shuffledOrders = new ArrayList<>();
        List<Card> mockCards = Arrays.asList(this.mockCards);

        // Construct deck, shuffle it and save new order of cards
        for (int i = 0; i < 6; i++) {
            Deck deck = new Deck();
            deck.putCard(this.mockCards[0]);
            deck.putCard(this.mockCards[2]);
            deck.putCard(this.mockCards[1]);
            deck.putCard(this.mockCards[1]);
            deck.putCard(this.mockCards[2]);
            deck.putCard(this.mockCards[0]);

            deck.shuffle();

            List<Integer> indices = new ArrayList<>();
            for (int j = 0; j < this.mockCards.length; j++) {
                Card drawnCard = deck.drawCard();
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
