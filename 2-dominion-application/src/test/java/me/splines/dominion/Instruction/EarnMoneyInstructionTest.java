package me.splines.dominion.Instruction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.Action.Instruction;
import me.splines.dominion.Card.Card;
import me.splines.dominion.Card.CardPool;
import me.splines.dominion.Game.Deck;
import me.splines.dominion.Game.GameStock;
import me.splines.dominion.Game.MoveState;
import me.splines.dominion.Game.Player;
import me.splines.dominion.Game.PlayerAbstract;
import me.splines.dominion.Game.PlayerDecision;

public class EarnMoneyInstructionTest {

    private Deck drawDeck;

    @Mock
    private PlayerDecision playerDecision;

    private PlayerAbstract player;

    @BeforeEach
    void prepare() {
        List<Card> initialHand = List.of(
                CardPool.estateCard,
                CardPool.curseCard,
                CardPool.copperCard,
                CardPool.silverCard,
                CardPool.goldCard);
        drawDeck = new Deck();
        for (Card card : initialHand) { // 5 initial cards
            drawDeck.put(card);
        }

        MockitoAnnotations.openMocks(this);

        player = new Player("money player", playerDecision, drawDeck, new GameStock());
    }

    @Test
    void earn42Money() {
        int earnMoney = 42;
        Instruction instruction = new EarnMoneyInstruction(earnMoney);
        MoveState moveState = new MoveState();
        int moneyInitial = moveState.getMoney();

        instruction.execute(player, moveState, new GameStock());

        assertThat(moveState.getMoney()).isEqualTo(moneyInitial + earnMoney);
    }

    @Test
    void earnNoNegativeMoney() {
        Throwable thrown = catchThrowable(
                () -> new EarnMoneyInstruction(-42));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("money")
                .hasMessageContaining("negative");
    }

}
