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

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0, Entry.Type.START_GAME));
        entries.add(new Entry(0, 1, Entry.Type.SETTINGS));
        entries.add(new Entry(0, 2, Entry.Type.EXIT));
        entries.add(new Entry(0, 3, Entry.Type.RESOLUTION));
        entries.add(new Entry(0, 4, Entry.Type.TO_MAIN_MENU));
        when(menuMock.getEntries()).thenReturn(entries);
    }

    @Test
    void testExitAction() throws IOException, URISyntaxException, FontFormatException {
        GUI.Action action = GUI.Action.SELECT;
        List<Entry> entries = menuMock.getEntries();
        when(menuMock.getCurrentEntry()).thenReturn(entries.get(2));
        entryController.step(gameMock, action, 0);
        verify(gameMock).setState(null);
    }


    @Test
    void testStartGameAction() throws IOException, URISyntaxException, FontFormatException {
        GUI.Action action = GUI.Action.SELECT;
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0, Entry.Type.START_GAME)); // START_GAME Entry
        entries.add(new Entry(0, 1, Entry.Type.SETTINGS));
        entries.add(new Entry(0, 2, Entry.Type.EXIT));
        entries.add(new Entry(0, 3, Entry.Type.RESOLUTION));
        entries.add(new Entry(0, 4, Entry.Type.TO_MAIN_MENU));
        when(menuMock.getEntries()).thenReturn(entries);
        when(menuMock.getCurrentEntry()).thenReturn(entries.get(0));
        when(gameMock.getSpriteLoader()).thenReturn(mock(SpriteLoader.class));

        entryController.step(gameMock, action, 0);
        verify(gameMock).setState(any(GameState.class));
    }


    @Test
    void testSettingsAction() throws IOException, URISyntaxException, FontFormatException {
        GUI.Action action = GUI.Action.SELECT;

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0, Entry.Type.START_GAME));
        entries.add(new Entry(0, 1, Entry.Type.SETTINGS));
        entries.add(new Entry(0, 2, Entry.Type.EXIT));
        entries.add(new Entry(0, 3, Entry.Type.RESOLUTION));
        entries.add(new Entry(0, 4, Entry.Type.TO_MAIN_MENU));

        when(menuMock.getEntries()).thenReturn(entries);
        when(menuMock.getCurrentEntry()).thenReturn(entries.get(1));

        when(gameMock.getSpriteLoader()).thenReturn(mock(SpriteLoader.class));

        entryController.step(gameMock, action, 0);

        verify(gameMock).setState(any(SettingsMenuState.class));
    }


    @Test
    void testResolutionRightAction() throws IOException, URISyntaxException, FontFormatException {
        GUI.Action action = GUI.Action.RIGHT;

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0, Entry.Type.START_GAME));
        entries.add(new Entry(0, 1, Entry.Type.SETTINGS));
        entries.add(new Entry(0, 2, Entry.Type.EXIT));
        entries.add(new Entry(0, 3, Entry.Type.RESOLUTION));
        entries.add(new Entry(0, 4, Entry.Type.TO_MAIN_MENU));

        when(menuMock.getEntries()).thenReturn(entries);
        when(menuMock.getCurrentEntry()).thenReturn(entries.get(3));

        ResizableGUI.Resolution[] resolutions = ResizableGUI.Resolution.values();
        ResizableGUI.Resolution currentResolution = resolutions[0];
        when(gameMock.getResolution()).thenReturn(currentResolution);

        entryController.step(gameMock, action, 0);

        verify(gameMock).setResolution(resolutions[1]);
    }


    @Test
    void testResolutionLeftAction() throws IOException, URISyntaxException, FontFormatException {
        GUI.Action action = GUI.Action.LEFT;

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0, Entry.Type.START_GAME));
        entries.add(new Entry(0, 1, Entry.Type.SETTINGS));
        entries.add(new Entry(0, 2, Entry.Type.EXIT));
        entries.add(new Entry(0, 3, Entry.Type.RESOLUTION));
        entries.add(new Entry(0, 4, Entry.Type.TO_MAIN_MENU));

        when(menuMock.getEntries()).thenReturn(entries);
        when(menuMock.getCurrentEntry()).thenReturn(entries.get(3));

        ResizableGUI.Resolution[] resolutions = ResizableGUI.Resolution.values();
        ResizableGUI.Resolution currentResolution = resolutions[1];
        when(gameMock.getResolution()).thenReturn(currentResolution);

        entryController.step(gameMock, action, 0);

        verify(gameMock).setResolution(resolutions[0]);
    }



    @Test
    void testToMainMenuAction() throws IOException, URISyntaxException, FontFormatException {
        GUI.Action action = GUI.Action.SELECT;
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0, Entry.Type.START_GAME));
        entries.add(new Entry(0, 1, Entry.Type.SETTINGS));
        entries.add(new Entry(0, 2, Entry.Type.EXIT));
        entries.add(new Entry(0, 3, Entry.Type.RESOLUTION));
        entries.add(new Entry(0, 4, Entry.Type.TO_MAIN_MENU));

        when(menuMock.getEntries()).thenReturn(entries);
        when(menuMock.getCurrentEntry()).thenReturn(entries.get(4));

        when(gameMock.getSpriteLoader()).thenReturn(mock(SpriteLoader.class));
        entryController.step(gameMock, action, 0);

        verify(gameMock).setState(any(MainMenuState.class));
    }

}

