package timelessodyssey.control.menu;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.model.menu.MainMenu;
import timelessodyssey.model.menu.Menu;
import timelessodyssey.states.State;

class MenuControllerTest {

    private MenuController menuController;
    private Menu menu;
    private EntryController entryControllerMock;
    private Game gameMock;
    private State stateMock;

    @BeforeEach
    void setUp() {
        menuController = mock(MenuController.class);
        entryControllerMock = mock(EntryController.class);
        gameMock = mock(Game.class);
        gameMock.setState(stateMock);

        menuController = new MenuController(menu, entryControllerMock) {
            @Override
            protected void onQuit(Game game) throws IOException, URISyntaxException {

            }
        };
    }

    @Test
    void testOnQuit() throws IOException, URISyntaxException {
        // Act
        menuController.onQuit(gameMock);

        // Assert
        verify(gameMock).setState(null);
    }
}