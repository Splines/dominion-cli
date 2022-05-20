package me.splines.dominion.Card;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CardTypeTest {

    @Test
    void cardTypeName() {
        // Just a few to see that enum names work correctly
        assertThat(CardType.ACTION.getName()).isEqualTo("Aktion");
        assertThat(CardType.ACTION_ATTACK.getName()).isEqualTo("Aktion - Angriff");
    }

}
