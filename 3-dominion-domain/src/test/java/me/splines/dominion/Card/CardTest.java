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
        assertThat(x.hashCode() == y.hashCode());
        assertThat(x.equals(y) && y.equals(x));
    }

    @Test
    void differentHashCodeNotEquals() {
        Card x = new TestableCard("card", CardType.MONEY, 42);
        Card y = new TestableCard("card-another", CardType.MONEY, 42);
        assertThat(x.hashCode() != y.hashCode());
        assertThat(!x.equals(y) && !y.equals(x));
    }

    @Test
    void equalsNullComparison() {
        Card x = new TestableCard("card", CardType.MONEY, 42);
        assertThat(!x.equals(null));
    }

}
