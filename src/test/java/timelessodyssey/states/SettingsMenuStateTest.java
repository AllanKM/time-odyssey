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
        mockSettingsMenu = mock(SettingsMenu.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockViewerProvider = mock(ViewerProvider.class);
        settingsMenuState = new SettingsMenuState(mockSettingsMenu, mockSpriteLoader);
    }

    @Test
    void testCreateController() {
        Controller<SettingsMenu> controller = settingsMenuState.createController();
        assertNotNull(controller, "Controller should not be null");
        assertTrue(controller instanceof SettingsMenuController, "Controller should be an instance of SettingsMenuController");

        SettingsMenuController settingsMenuController = (SettingsMenuController) controller;
        assertNotNull(settingsMenuController.getModel(), "EntryController should not be null");
    }

    @Test
    void testCreateScreenViewer() {
        ScreenViewer<SettingsMenu> screenViewer = settingsMenuState.createScreenViewer(mockViewerProvider);
        assertNotNull(screenViewer, "ScreenViewer should not be null");
        assertTrue(screenViewer instanceof MenuViewer, "ScreenViewer should be an instance of MenuViewer");
    }

    @Test
    void testAllowArrowSpam() {
        boolean result = settingsMenuState.allowArrowSpam();
        assertFalse(result, "allowArrowSpam should return false");
    }
}
