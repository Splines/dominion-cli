package me.splines.dominion.Game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.CardType;
import me.splines.dominion.Card.ValueCard;
import me.splines.dominion.Game.Pile.EmptyPileException;

class PileTest {

    Card[] mockCards = {
            new ValueCard("Value Card 1", CardType.POINTS, 42, 43),
            new ValueCard("Value Card 2", CardType.MONEY, 2, 3),
            new ValueCard("Value Card 3", CardType.CURSE, 5, -50)
            // TODO add more mock cards, e.g. Action cards later
    };

    @Test
    void putCardDrawSameCard() {
        Pile pile = new Pile();
        Card mockCard = mockCards[0];

        // Put and draw one card
        pile.putCard(mockCard);
        Card drawedCard = pile.drawCard();

        assertThat(drawedCard)
                .isSameAs(mockCard)
                .isEqualTo(mockCard);
    }

    @Test
    void putAndDrawMultipleCards() {
        Pile pile = new Pile();

        // Put two cards
        pile.putCard(mockCards[0]);
        pile.putCard(mockCards[1]);

        // Draw one card
        Card drawedCard = pile.drawCard();
        assertThat(drawedCard).isSameAs(mockCards[1]);

        // Draw another card
        drawedCard = pile.drawCard();
        assertThat(drawedCard).isSameAs(mockCards[0]);

        // Empty
        Throwable thrown = catchThrowable(() -> pile.drawCard());
        assertThat(thrown).isInstanceOf(EmptyPileException.class);

        // Put one card, immediately draw it
        pile.putCard(mockCards[2]);
        drawedCard = pile.drawCard();
        assertThat(drawedCard).isSameAs(mockCards[2]);

        // Empty
        thrown = catchThrowable(() -> pile.drawCard());
        assertThat(thrown).isInstanceOf(EmptyPileException.class);
    }

    @Test
    void drawFromEmptyPile() {
        Pile pile = new Pile();
        Throwable thrown = catchThrowable(() -> pile.drawCard());
        assertThat(thrown)
                .isInstanceOf(EmptyPileException.class)
                .hasMessageContaining("empty")
                .hasMessageContaining("Pile");
    }

}
