package me.splines.dominion.instruction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.action.Instruction;
import me.splines.dominion.card.Card;
import me.splines.dominion.card.CardPool;
import me.splines.dominion.game.Deck;
import me.splines.dominion.game.GameStock;
import me.splines.dominion.game.MoveState;
import me.splines.dominion.game.Player;
import me.splines.dominion.game.PlayerAbstract;
import me.splines.dominion.game.PlayerDecision;

class EarnMoneyInstructionTest {

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