package me.splines.dominion.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

import me.splines.dominion.action.Action;
import me.splines.dominion.action.Instruction;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.Player;
import me.splines.dominion.game.Stock;

class ActionCardBuilderTest {

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
    void createActionCardWithBuilder() {
        String name = "Super Action Card";
        CardType type = CardType.ACTION;
        int cost = 2;
        ActionCard actionCard = new ActionCardBuilder(name, cost)
                .with(dummyAction).build();

        assertThat(actionCard.getName()).isEqualTo(name);
        assertThat(actionCard.getType()).isEqualTo(type);
        assertThat(actionCard.getCost()).isEqualTo(cost);
        assertThat(actionCard.getAction()).isEqualTo(dummyAction);
    }

    @Test
    void createActionAttackCardWithBuilder() {
        ActionCard actionCard = new ActionCardBuilder("halleluja", 42)
                .with(dummyAction).asAttack().build();
        assertThat(actionCard.getType()).isEqualTo(CardType.ACTION_ATTACK);
    }

    @Test
    void createActionReactionCardWithBuilder() {
        ActionCard actionCard = new ActionCardBuilder("halleluja", 42)
                .with(dummyAction).asReaction().build();
        assertThat(actionCard.getType()).isEqualTo(CardType.ACTION_REACTION);
    }

    @Test
    void createActionAttackOrderDoesNotMatter() {
        ActionCard actionCard = new ActionCardBuilder("halleluja", 42)
                .with(dummyAction).asAttack().build();
        ActionCard actionCard2 = new ActionCardBuilder("halleluja", 42)
                .asAttack().with(dummyAction).build();
        assertThat(actionCard).isEqualTo(actionCard2);
    }

    @Test
    void noAction() {
        Throwable thrown = catchThrowable(
                () -> new ActionCardBuilder("no action", 42).build());
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
