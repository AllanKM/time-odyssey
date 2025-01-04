package timelessodyssey.control.menu;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.Game;
import timelessodyssey.model.menu.SettingsMenu;
import timelessodyssey.states.MainMenuState;
import timelessodyssey.view.SpriteLoader;

class SettingsMenuControllerTest {

    @Mock
    private Game game;

    @Mock
    private SettingsMenu settingsMenu;

    @Mock
    private EntryController entryController;

    @Mock
    private SpriteLoader spriteLoader;

    private SettingsMenuController settingsMenuController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        settingsMenuController = new SettingsMenuController(settingsMenu, entryController);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);
    }

    @Test
    void onQuit_shouldSetMainMenuState() throws IOException, URISyntaxException {
        settingsMenuController.onQuit(game);
        verify(game).setState(any(MainMenuState.class));
    }

    @Test
    void onQuit_shouldCreateMainMenuStateWithCorrectParameters() throws IOException, URISyntaxException {
        settingsMenuController.onQuit(game);
        verify(game).setState(any(MainMenuState.class));
    }
}
