package me.splines.dominion.Card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

import me.splines.dominion.Action.Action;
import me.splines.dominion.Action.Instruction;
import me.splines.dominion.Game.MoveState;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.Stock;

class ActionCardTest {

    class DummyInstruction implements Instruction {
        @Override
        public void execute(PlayerAbstract player, MoveState moveState, Stock stock) {
            return;
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
