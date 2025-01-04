package timelessodyssey.control.menu;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.menu.Menu;

class MenuControllerTest {

    private static class TestMenuController extends MenuController<Menu> {
        public TestMenuController(Menu menu, EntryController entryController) {
            super(menu, entryController);
        }

        @Override
        protected void onQuit(Game game) {
            game.setState(null);
        }
    }

    private Menu menu;
    private EntryController entryController;
    private MenuController<Menu> menuController;
    private Game game;

    @BeforeEach
    void setUp() {
        menu = mock(Menu.class);
        entryController = mock(EntryController.class);
        menuController = spy(new TestMenuController(menu, entryController));
        game = mock(Game.class);
    }

    @Test
    void testStepUpAction() throws IOException, URISyntaxException, FontFormatException {
        menuController.step(game, GUI.Action.UP, 0);
        verify(menu, times(1)).moveUp();
        verify(entryController, never()).step(any(), any(), anyLong());
    }

    @Test
    void testStepDownAction() throws IOException, URISyntaxException, FontFormatException {
        menuController.step(game, GUI.Action.DOWN, 0);
        verify(menu, times(1)).moveDown();
        verify(entryController, never()).step(any(), any(), anyLong());
    }

    @Test
    void testStepQuitAction() throws IOException, URISyntaxException, FontFormatException {
        menuController.step(game, GUI.Action.QUIT, 0);
        verify(menu, never()).moveUp();
        verify(menu, never()).moveDown();
        verify(entryController, never()).step(any(), any(), anyLong());
        verify(menuController, times(1)).onQuit(game);
    }

    @Test
    void testStepOtherActions() throws IOException, URISyntaxException, FontFormatException {
        menuController.step(game, GUI.Action.LEFT, 0);
        verify(menu, never()).moveUp();
        verify(menu, never()).moveDown();
        verify(menuController, never()).onQuit(any());
        verify(entryController, times(1)).step(game, GUI.Action.LEFT, 0);
    }
}
