package timelessodyssey.states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.control.Controller;
import timelessodyssey.control.menu.EntryController;
import timelessodyssey.control.menu.SettingsMenuController;
import timelessodyssey.model.menu.SettingsMenu;
import timelessodyssey.view.SpriteLoader;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.screens.MenuViewer;
import timelessodyssey.view.screens.ScreenViewer;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SettingsMenuStateTest {

    private SettingsMenu mockSettingsMenu;
    private SpriteLoader mockSpriteLoader;
    private ViewerProvider mockViewerProvider;
    private SettingsMenuState settingsMenuState;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        // Mock dependencies
        mockSettingsMenu = mock(SettingsMenu.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockViewerProvider = mock(ViewerProvider.class);

        // Create instance of SettingsMenuState
        settingsMenuState = new SettingsMenuState(mockSettingsMenu, mockSpriteLoader);
    }

    @Test
    void testCreateController() {
        // Act
        Controller<SettingsMenu> controller = settingsMenuState.createController();

        // Assert
        assertNotNull(controller, "Controller should not be null");
        assertTrue(controller instanceof SettingsMenuController, "Controller should be an instance of SettingsMenuController");

        // Verify that the SettingsMenuController is created with the correct sub-controller
        SettingsMenuController settingsMenuController = (SettingsMenuController) controller;
        assertNotNull(settingsMenuController.getModel(), "EntryController should not be null");
    }

    @Test
    void testCreateScreenViewer() {
        // Act
        ScreenViewer<SettingsMenu> screenViewer = settingsMenuState.createScreenViewer(mockViewerProvider);

        // Assert
        assertNotNull(screenViewer, "ScreenViewer should not be null");
        assertTrue(screenViewer instanceof MenuViewer, "ScreenViewer should be an instance of MenuViewer");
    }

    @Test
    void testAllowArrowSpam() {
        // Act
        boolean result = settingsMenuState.allowArrowSpam();

        // Assert
        assertFalse(result, "allowArrowSpam should return false");
    }
}
