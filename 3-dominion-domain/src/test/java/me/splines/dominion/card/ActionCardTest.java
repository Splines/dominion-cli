package me.splines.dominion.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

import me.splines.dominion.action.Action;
import me.splines.dominion.action.Instruction;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.Player;
import me.splines.dominion.game.Stock;

class ActionCardTest {

    class DummyInstruction implements Instruction {
        @Override
        public void execute(Player player, MoveState moveState, Stock stock) {
            return;
        }

        @Override
        public String getName() {
            return "I'm a dummy";
        }
    }

    private Action dummyAction = new Action(
            new DummyInstruction(),
            new DummyInstruction(),
            new DummyInstruction(),
            new DummyInstruction());

    @Test
    void createActionCard() {
        String name = "Super Action Card";
        CardType type = CardType.ACTION;
        int cost = 2;
        ActionCard actionCard = new ActionCard(name, type, cost, dummyAction);

        assertThat(actionCard.getName()).isEqualTo(name);
        assertThat(actionCard.getType()).isEqualTo(type);
        assertThat(actionCard.getCost()).isEqualTo(cost);
        assertThat(actionCard.getAction()).isEqualTo(dummyAction);
    }

    @Test
    void invalidActionType() {
        CardType type = CardType.MONEY;
        Throwable thrown = catchThrowable(
                () -> new ActionCard("A", type, 42, dummyAction));
        assertThat(thrown)
                .hasMessageContaining("Invalid type")
                .hasMessageContaining("action")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nullAction() {
        Action action = null;
        Throwable thrown = catchThrowable(
                () -> new ActionCard("A", CardType.ACTION, 42, action));
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

}
