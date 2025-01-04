package timelessodyssey.states;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.control.Controller;
import timelessodyssey.control.menu.MainMenuController;
import timelessodyssey.model.menu.MainMenu;
import timelessodyssey.view.SpriteLoader;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.screens.MenuViewer;
import timelessodyssey.view.screens.ScreenViewer;

class MainMenuStateTest {

    private MainMenu mockMainMenu;
    private SpriteLoader mockSpriteLoader;
    private ViewerProvider mockViewerProvider;
    private MainMenuState mainMenuState;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        mockMainMenu = mock(MainMenu.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockViewerProvider = mock(ViewerProvider.class);
        mainMenuState = new MainMenuState(mockMainMenu, mockSpriteLoader);
    }

    @Test
    void testCreateScreenViewer() {
        ScreenViewer<MainMenu> screenViewer = mainMenuState.createScreenViewer(mockViewerProvider);
        assertNotNull(screenViewer, "ScreenViewer should not be null");
        assertTrue(screenViewer instanceof MenuViewer, "ScreenViewer should be an instance of MenuViewer");
    }

    @Test
    void testCreateController() {
        Controller<MainMenu> controller = mainMenuState.createController();
        assertNotNull(controller, "Controller should not be null");
        assertTrue(controller instanceof MainMenuController, "Controller should be an instance of MainMenuController");

        MainMenuController mainMenuController = (MainMenuController) controller;
        assertNotNull(mainMenuController.getModel(), "EntryController should not be null");
    }

    @Test
    void testAllowArrowSpam() {
        boolean result = mainMenuState.allowArrowSpam();
        assertFalse(result, "allowArrowSpam should return false");
    }
}
