package me.splines.dominion.Card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void createValueCard() {
        String name = "MyTestCard";
        CardType type = CardType.MONEY;
        int cost = 2;
        int value = 1;
        ValueCard valueCard = new ValueCard(name, type, cost, value);

        assertThat(valueCard.getName()).isEqualTo(name);
        assertThat(valueCard.getType()).isEqualTo(type);
        assertThat(valueCard.getCost()).isEqualTo(cost);
        assertThat(valueCard.getValue()).isEqualTo(value);
    }

    @Test
    void cardInvalidCost() {
        int cost = -1;
        Throwable thrown = catchThrowable(
                () -> new ValueCard("MyTestCard", CardType.MONEY, cost, 42));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cost");
    }

    @Test
    void valueCardInvalidType() {
        CardType type = CardType.ACTION;
        Throwable thrown = catchThrowable(
                () -> new ValueCard("MyTestCard", type, 2, 1));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid type");
    }

    @Test
    void valueCardInvalidZeroValue() {
        int value = 0;
        Throwable thrown = catchThrowable(
                () -> new ValueCard("MyTestCard", CardType.POINTS, 2, value));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("non-zero");
    }

}
