package timelessodyssey.control.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.model.menu.MainMenu;

import static org.mockito.Mockito.*;

class MainMenuControllerTest {

    private MainMenuController mainMenuController;
    private MainMenu mainMenuMock;
    private EntryController entryControllerMock;
    private Game gameMock;

    @BeforeEach
    void setUp() {
        mainMenuMock = mock(MainMenu.class);
        entryControllerMock = mock(EntryController.class);
        gameMock = mock(Game.class);

        mainMenuController = new MainMenuController(mainMenuMock, entryControllerMock);
    }

    @Test
    void testOnQuit() {
        mainMenuController.onQuit(gameMock);
        verify(gameMock).setState(null);
    }
}
