package timelessodyssey.control.credits;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.credits.Credits;
import timelessodyssey.states.MainMenuState;
import timelessodyssey.view.SpriteLoader;

class CreditsControllerTest {

    private CreditsController creditsController;
    private Credits credits;
    private Game game;

    @BeforeEach
    public void setUp() {
        credits = mock(Credits.class);
        creditsController = new CreditsController(credits);
        game = mock(Game.class);
    }

    @Test
    public void testStepQuitAction() throws IOException, URISyntaxException {
        GUI.Action action = GUI.Action.QUIT;
        long frameCount = 0;
        SpriteLoader spriteLoader = mock(SpriteLoader.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);

        MainMenuState mainMenuState = mock(MainMenuState.class);
        doAnswer(invocation -> {
            MainMenuState state = invocation.getArgument(0);
            assertTrue(state instanceof MainMenuState);
            return null;
        }).when(game).setState(any(MainMenuState.class));

        creditsController.step(game, action, frameCount);
        verify(game, times(1)).setState(any(MainMenuState.class));
    }

    @Test
    public void testStepNonQuitAction() throws IOException, URISyntaxException {
        GUI.Action action = GUI.Action.NONE;
        long frameCount = 0;
        creditsController.step(game, action, frameCount);
        verify(game, never()).setState(any());
    }
}