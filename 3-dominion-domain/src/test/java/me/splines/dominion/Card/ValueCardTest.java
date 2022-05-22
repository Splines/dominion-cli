package me.splines.dominion.Card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

class ValueCardTest {

    class TestableValueCard extends ValueCard {
        public TestableValueCard(String name, CardType type, int cost, int value) {
            super(name, type, cost, value);
        }
    }

    @Test
    void createValueCard() {
        String name = "My Value Card";
        CardType type = CardType.MONEY;
        int cost = 2;
        int value = 1;
        ValueCard valueCard = new TestableValueCard(name, type, cost, value);

        assertThat(valueCard.getName()).isEqualTo(name);
        assertThat(valueCard.getType()).isEqualTo(type);
        assertThat(valueCard.getCost()).isEqualTo(cost);
        assertThat(valueCard.getValue()).isEqualTo(value);
    }

    @Test
    void valueCardInvalidType() {
        CardType type = CardType.ACTION;
        Throwable thrown = catchThrowable(
                () -> new TestableValueCard("MyTestCard", type, 2, 1));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid type");
    }

    @Test
    void valueCardInvalidZeroValue() {
        int value = 0;
        Throwable thrown = catchThrowable(
                () -> new TestableValueCard("MyTestCard", CardType.POINTS, 2, value));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("value")
                .hasMessageContaining("non-zero");
    }

}
