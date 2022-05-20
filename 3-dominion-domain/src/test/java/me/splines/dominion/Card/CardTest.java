package me.splines.dominion.Card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

public class CardTest {

    class TestableCard extends Card {
        public TestableCard(String name, CardType type, int cost) {
            super(name, type, cost);
        }
    }

    @Test
    void cardInvalidCost() {
        int cost = -1;
        Throwable thrown = catchThrowable(
                () -> new TestableCard("MyTestCard", CardType.MONEY, cost));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cost");
    }

}
