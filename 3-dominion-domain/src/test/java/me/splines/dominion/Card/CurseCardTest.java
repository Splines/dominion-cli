package me.splines.dominion.Card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

public class CurseCardTest {

    @Test
    void curseValue() {
        int curse = -13;
        CurseCard card = new CurseCard("Curse Card", 7, curse);
        assertThat(card.getCurse()).isEqualTo(curse);
        assertThat(card.getCurse()).isEqualTo(card.getValue());
    }

    @Test
    void noPositiveCurse() {
        Throwable thrown = catchThrowable(
                () -> new CurseCard("Positive Curse Card", 5, 7));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Curse")
                .hasMessageContaining("negative");
    }

}
