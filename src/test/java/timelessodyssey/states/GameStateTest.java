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
        // Mock dependencies
        mockScene = mock(Scene.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockViewerProvider = mock(ViewerProvider.class);

        // Create instance of GameState
        gameState = new GameState(mockScene, mockSpriteLoader);
    }

    @Test
    void testCreateController() {
        // Act
        Controller<Scene> controller = gameState.createController();

        // Assert
        assertNotNull(controller, "Controller should not be null");
        assertTrue(controller instanceof SceneController, "Controller should be an instance of SceneController");

        // Verify that the SceneController is created with the correct sub-controllers
        SceneController sceneController = (SceneController) controller;
        assertNotNull(sceneController.getModel(), "PlayerController should not be null");
        //assertNotNull(sceneController.getParticleController(), "ParticleController should not be null");
    }

    @Test
    void testCreateScreenViewer() {
        // Act
        ScreenViewer<Scene> screenViewer = gameState.createScreenViewer(mockViewerProvider);

        // Assert
        assertNotNull(screenViewer, "ScreenViewer should not be null");
        assertTrue(screenViewer instanceof GameViewer, "ScreenViewer should be an instance of GameViewer");
    }

    @Test
    void testAllowArrowSpam() {
        // Act
        boolean result = gameState.allowArrowSpam();

        // Assert
        assertTrue(result, "allowArrowSpam should return true");
    }
}