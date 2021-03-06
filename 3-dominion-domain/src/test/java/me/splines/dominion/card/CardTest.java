package me.splines.dominion.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

class CardTest {

    class TestableCard extends Card {
        public TestableCard(String name, CardType type, int cost) {
            super(name, type, cost);
        }
    }

    @Test
    void nullNameException() {
        Throwable thrown = catchThrowable(
                () -> new TestableCard(null, CardType.MONEY, 42));
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void nullTypeException() {
        Throwable thrown = catchThrowable(
                () -> new TestableCard("a", null, 42));
        assertThat(thrown).isInstanceOf(NullPointerException.class);
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

    @Test
    void sameHashCodeEquals() {
        Card x = new TestableCard("card", CardType.MONEY, 42);
        Card y = new TestableCard("card", CardType.MONEY, 42);
        assertThat(x)
                .hasSameHashCodeAs(y)
                .isEqualTo(y);
        assertThat(y).isEqualTo(x);
    }

    @Test
    void differentHashCodeNotEquals() {
        Card x = new TestableCard("card", CardType.MONEY, 42);
        Card y = new TestableCard("card-another", CardType.MONEY, 42);
        assertThat(x)
                .doesNotHaveSameHashCodeAs(y)
                .isNotEqualTo(y);
        assertThat(y).isNotEqualTo(x);
    }

    @Test
    void equalsNullComparison() {
        Card x = new TestableCard("card", CardType.MONEY, 42);
        assertThat(x).isNotEqualTo(null);
    }

}
