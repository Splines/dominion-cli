package me.splines.dominion.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

class MoneyCardTest {

    @Test
    void moneyValue() {
        int money = 3;
        MoneyCard card = new MoneyCard("Money Card", 5, money);
        assertThat(card.getMoney()).isEqualTo(money);
        assertThat(card.getMoney()).isEqualTo(card.getValue());
    }

    @Test
    void noNegativeMoney() {
        Throwable thrown = catchThrowable(
                () -> new MoneyCard("Negative Money Card", 5, -3));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Money")
                .hasMessageContaining("positive");
    }

}
