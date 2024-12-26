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
        // Mock dependencies
        mockMainMenu = mock(MainMenu.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockViewerProvider = mock(ViewerProvider.class);

        // Create instance of MainMenuState
        mainMenuState = new MainMenuState(mockMainMenu, mockSpriteLoader);
    }

    @Test
    void testCreateScreenViewer() {
        // Act
        ScreenViewer<MainMenu> screenViewer = mainMenuState.createScreenViewer(mockViewerProvider);

        // Assert
        assertNotNull(screenViewer, "ScreenViewer should not be null");
        assertTrue(screenViewer instanceof MenuViewer, "ScreenViewer should be an instance of MenuViewer");
    }

    @Test
    void testCreateController() {
        // Act
        Controller<MainMenu> controller = mainMenuState.createController();

        // Assert
        assertNotNull(controller, "Controller should not be null");
        assertTrue(controller instanceof MainMenuController, "Controller should be an instance of MainMenuController");

        // Verify that the MainMenuController is created with the correct sub-controller
        MainMenuController mainMenuController = (MainMenuController) controller;
        assertNotNull(mainMenuController.getModel(), "EntryController should not be null");
    }

    @Test
    void testAllowArrowSpam() {
        // Act
        boolean result = mainMenuState.allowArrowSpam();

        // Assert
        assertFalse(result, "allowArrowSpam should return false");
    }
}
