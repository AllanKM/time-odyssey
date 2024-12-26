package timelessodyssey.control.menu;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.menu.Entry;
import timelessodyssey.model.menu.Menu;
import timelessodyssey.states.GameState;
import timelessodyssey.states.MainMenuState;
import timelessodyssey.states.SettingsMenuState;
import timelessodyssey.view.SpriteLoader;

class EntryControllerTest {

    private EntryController entryController;
    private Menu menuMock;
    private Game gameMock;

    @BeforeEach
    void setUp() {
        menuMock = mock(Menu.class);
        gameMock = mock(Game.class);
        entryController = new EntryController(menuMock);

        // Use a real list for menu entries
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0, Entry.Type.START_GAME));
        entries.add(new Entry(0, 1, Entry.Type.SETTINGS));
        entries.add(new Entry(0, 2, Entry.Type.EXIT));
        entries.add(new Entry(0, 3, Entry.Type.RESOLUTION));
        entries.add(new Entry(0, 4, Entry.Type.TO_MAIN_MENU));

        // Stub the behavior for menu
        when(menuMock.getEntries()).thenReturn(entries);
    }

    @Test
    void testExitAction() throws IOException, URISyntaxException, FontFormatException {
        // Arrange
        GUI.Action action = GUI.Action.SELECT;

        // Ensure the correct entry is returned
        List<Entry> entries = menuMock.getEntries();
        when(menuMock.getCurrentEntry()).thenReturn(entries.get(2)); // EXIT

        // Act
        entryController.step(gameMock, action, 0);

        // Assert
        verify(gameMock).setState(null);
    }


    @Test
    void testStartGameAction() throws IOException, URISyntaxException, FontFormatException {
        // Arrange
        GUI.Action action = GUI.Action.SELECT;

        // Use a real list for entries
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0, Entry.Type.START_GAME)); // START_GAME Entry
        entries.add(new Entry(0, 1, Entry.Type.SETTINGS));
        entries.add(new Entry(0, 2, Entry.Type.EXIT));
        entries.add(new Entry(0, 3, Entry.Type.RESOLUTION));
        entries.add(new Entry(0, 4, Entry.Type.TO_MAIN_MENU));

        // Stub menu behavior
        when(menuMock.getEntries()).thenReturn(entries);
        when(menuMock.getCurrentEntry()).thenReturn(entries.get(0)); // START_GAME

        // Mock sprite loader
        when(gameMock.getSpriteLoader()).thenReturn(mock(SpriteLoader.class));

        // Act
        entryController.step(gameMock, action, 0);

        // Assert
        verify(gameMock).setState(any(GameState.class));
    }


    @Test
    void testSettingsAction() throws IOException, URISyntaxException, FontFormatException {
        // Arrange
        GUI.Action action = GUI.Action.SELECT;

        // Use a real list for entries
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0, Entry.Type.START_GAME));
        entries.add(new Entry(0, 1, Entry.Type.SETTINGS)); // SETTINGS Entry
        entries.add(new Entry(0, 2, Entry.Type.EXIT));
        entries.add(new Entry(0, 3, Entry.Type.RESOLUTION));
        entries.add(new Entry(0, 4, Entry.Type.TO_MAIN_MENU));

        // Stub menu behavior
        when(menuMock.getEntries()).thenReturn(entries);
        when(menuMock.getCurrentEntry()).thenReturn(entries.get(1)); // SETTINGS

        // Mock sprite loader
        when(gameMock.getSpriteLoader()).thenReturn(mock(SpriteLoader.class));

        // Act
        entryController.step(gameMock, action, 0);

        // Assert
        verify(gameMock).setState(any(SettingsMenuState.class));
    }


    @Test
    void testResolutionRightAction() throws IOException, URISyntaxException, FontFormatException {
        // Arrange
        GUI.Action action = GUI.Action.RIGHT;

        // Use a real list for entries
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0, Entry.Type.START_GAME));
        entries.add(new Entry(0, 1, Entry.Type.SETTINGS));
        entries.add(new Entry(0, 2, Entry.Type.EXIT));
        entries.add(new Entry(0, 3, Entry.Type.RESOLUTION)); // RESOLUTION Entry
        entries.add(new Entry(0, 4, Entry.Type.TO_MAIN_MENU));

        // Stub menu behavior
        when(menuMock.getEntries()).thenReturn(entries);
        when(menuMock.getCurrentEntry()).thenReturn(entries.get(3)); // RESOLUTION

        // Mock resolution logic
        ResizableGUI.Resolution[] resolutions = ResizableGUI.Resolution.values();
        ResizableGUI.Resolution currentResolution = resolutions[0]; // Ensure it is not the last resolution
        when(gameMock.getResolution()).thenReturn(currentResolution);

        // Act
        entryController.step(gameMock, action, 0);

        // Assert
        verify(gameMock).setResolution(resolutions[1]); // Verify it was set to the next resolution
    }


    @Test
    void testResolutionLeftAction() throws IOException, URISyntaxException, FontFormatException {
        // Arrange
        GUI.Action action = GUI.Action.LEFT;

        // Use real entries list
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0, Entry.Type.START_GAME));
        entries.add(new Entry(0, 1, Entry.Type.SETTINGS));
        entries.add(new Entry(0, 2, Entry.Type.EXIT));
        entries.add(new Entry(0, 3, Entry.Type.RESOLUTION)); // RESOLUTION Entry
        entries.add(new Entry(0, 4, Entry.Type.TO_MAIN_MENU));

        // Stub menu behavior
        when(menuMock.getEntries()).thenReturn(entries);
        when(menuMock.getCurrentEntry()).thenReturn(entries.get(3)); // RESOLUTION

        // Mock resolution logic
        ResizableGUI.Resolution[] resolutions = ResizableGUI.Resolution.values();
        ResizableGUI.Resolution currentResolution = resolutions[1]; // Ensure it is not the first resolution
        when(gameMock.getResolution()).thenReturn(currentResolution);

        // Act
        entryController.step(gameMock, action, 0);

        // Assert
        verify(gameMock).setResolution(resolutions[0]); // Verify it was set to the previous resolution
    }



    @Test
    void testToMainMenuAction() throws IOException, URISyntaxException, FontFormatException {
        // Arrange
        GUI.Action action = GUI.Action.SELECT;

        // Use a real list for entries
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0, Entry.Type.START_GAME));
        entries.add(new Entry(0, 1, Entry.Type.SETTINGS));
        entries.add(new Entry(0, 2, Entry.Type.EXIT));
        entries.add(new Entry(0, 3, Entry.Type.RESOLUTION));
        entries.add(new Entry(0, 4, Entry.Type.TO_MAIN_MENU)); // TO_MAIN_MENU Entry

        // Stub menu behavior
        when(menuMock.getEntries()).thenReturn(entries);
        when(menuMock.getCurrentEntry()).thenReturn(entries.get(4)); // TO_MAIN_MENU

        // Mock sprite loader
        when(gameMock.getSpriteLoader()).thenReturn(mock(SpriteLoader.class));

        // Act
        entryController.step(gameMock, action, 0);

        // Assert
        verify(gameMock).setState(any(MainMenuState.class));
    }

}

