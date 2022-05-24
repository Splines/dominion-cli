package me.splines.dominion.game;

import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.splines.dominion.interaction.PlayerDecision;
import me.splines.dominion.interaction.PlayerInformation;
import me.splines.dominion.interaction.PlayerInteraction;

/**
 * The Game is really hard to test without Powermock as the list mainly
 * consists of private method calls and private fields. Sadly, Powermock is
 * not yet ported to JUnit5, so we can't use it in this project.
 * The only thing we can test right now is that there is no initialization
 * during the construction of a Game. Apart from that, testing start()
 * would end in integration instead of unit tests since we can't mock the inner
 * workings of the class and can only interact by mocking the PlayerDecision.
 */
class GameTest {

    @Mock
    private PlayerDecision decision;

    @Mock
    private PlayerInformation information;

    @BeforeEach
    void prepare() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void gameInitializationWithoutError() {
        List<String> playerNames = List.of("Player1", "Player2");
        PlayerInteraction interaction = new PlayerInteraction(decision, information);

        new Game(interaction, playerNames);
        verifyNoMoreInteractions(decision);
        verifyNoMoreInteractions(information);
    }

}
