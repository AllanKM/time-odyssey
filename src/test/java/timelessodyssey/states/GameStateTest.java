package timelessodyssey.states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.control.Controller;
import timelessodyssey.control.game.ParticleController;
import timelessodyssey.control.game.PlayerController;
import timelessodyssey.control.game.SceneController;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.view.SpriteLoader;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.screens.GameViewer;
import timelessodyssey.view.screens.ScreenViewer;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    private Scene mockScene;
    private SpriteLoader mockSpriteLoader;
    private ViewerProvider mockViewerProvider;
    private GameState gameState;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        mockScene = mock(Scene.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockViewerProvider = mock(ViewerProvider.class);
        gameState = new GameState(mockScene, mockSpriteLoader);
    }

    @Test
    void testCreateController() {
        Controller<Scene> controller = gameState.createController();
        assertNotNull(controller, "Controller should not be null");
        assertTrue(controller instanceof SceneController, "Controller should be an instance of SceneController");
        SceneController sceneController = (SceneController) controller;
        assertNotNull(sceneController.getModel(), "PlayerController should not be null");
    }

    @Test
    void testCreateScreenViewer() {
        ScreenViewer<Scene> screenViewer = gameState.createScreenViewer(mockViewerProvider);
        assertNotNull(screenViewer, "ScreenViewer should not be null");
        assertTrue(screenViewer instanceof GameViewer, "ScreenViewer should be an instance of GameViewer");
    }

    @Test
    void testAllowArrowSpam() {
        boolean result = gameState.allowArrowSpam();
        assertTrue(result, "allowArrowSpam should return true");
    }
}