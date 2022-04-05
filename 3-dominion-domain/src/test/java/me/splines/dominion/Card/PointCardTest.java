package me.splines.dominion.Card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

public class PointCardTest {

    @Test
    void pointValue() {
        int points = 100;
        PointCard card = new PointCard("Point Card", 7, points);
        assertThat(card.getPoints()).isEqualTo(points);
        assertThat(card.getPoints()).isEqualTo(card.getValue());
    }

    @Test
    void noNegativePoints() {
        Throwable thrown = catchThrowable(
                () -> new PointCard("Negative Point Card", 5, -100));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Point")
                .hasMessageContaining("positive");
    }

}
