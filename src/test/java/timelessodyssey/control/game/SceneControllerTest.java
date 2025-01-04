package timelessodyssey.control.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.credits.Credits;
import timelessodyssey.model.game.elements.player.Player;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.model.game.scene.SceneBuilder;
import timelessodyssey.states.CreditsState;
import timelessodyssey.states.GameState;
import timelessodyssey.view.SpriteLoader;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SceneControllerTest {

    private SceneController sceneController;
    private Scene sceneMock;
    private PlayerController playerControllerMock;
    private ParticleController particleControllerMock;
    private Game gameMock;
    private Player playerMock;
    private SpriteLoader spriteLoaderMock;
    private SceneBuilder sceneBuilderMock;

    @BeforeEach
    void setUp() {
        sceneMock = mock(Scene.class);
        playerControllerMock = mock(PlayerController.class);
        particleControllerMock = mock(ParticleController.class);
        gameMock = mock(Game.class);
        playerMock = mock(Player.class);
        spriteLoaderMock = mock(SpriteLoader.class);
        sceneBuilderMock = mock(SceneBuilder.class);

        when(sceneMock.getPlayer()).thenReturn(playerMock);
        when(gameMock.getSpriteLoader()).thenReturn(spriteLoaderMock);
        when(sceneMock.getSceneCode()).thenReturn(1);
        when(sceneMock.isAtTransitionPosition()).thenReturn(true);
        when(gameMock.getNumberOfLevels()).thenReturn(2);
        when(sceneMock.getSceneCode()).thenReturn(1);
        when(sceneBuilderMock.createScene(playerMock)).thenReturn(mock(Scene.class));
        sceneController = new SceneController(sceneMock, playerControllerMock, particleControllerMock);
    }

    @Test
    void testStepQuitAction() throws IOException, URISyntaxException {
        GUI.Action action = GUI.Action.QUIT;
        long frameCount = 0;
        when(sceneMock.isAtTransitionPosition()).thenReturn(false);
        sceneController.step(gameMock, action, frameCount);
        verify(gameMock).setState(null);
        verifyNoInteractions(playerControllerMock, particleControllerMock);
    }

    @Test
    void testStepTransitionToCredits() throws IOException, URISyntaxException {
        GUI.Action action = GUI.Action.NONE;
        long frameCount = 0;

        when(sceneMock.isAtTransitionPosition()).thenReturn(true);
        when(sceneMock.getSceneCode()).thenReturn(1);
        when(gameMock.getNumberOfLevels()).thenReturn(2);
        when(gameMock.getSpriteLoader()).thenReturn(spriteLoaderMock);
        when(sceneBuilderMock.createScene(playerMock)).thenReturn(mock(Scene.class));
        sceneController.step(gameMock, action, frameCount);

        verify(gameMock).setState(any(CreditsState.class));
        verify(playerControllerMock).step(gameMock, action, frameCount);
        verify(particleControllerMock).step(gameMock, action, frameCount);
    }

    @Test
    void testStepTransitionToNextScene() throws IOException, URISyntaxException {
        // Arrange
        GUI.Action action = GUI.Action.NONE;
        long frameCount = 0;

        when(sceneMock.isAtTransitionPosition()).thenReturn(true);
        when(sceneMock.getSceneCode()).thenReturn(0);
        when(gameMock.getNumberOfLevels()).thenReturn(2);
        when(sceneBuilderMock.createScene(playerMock)).thenReturn(mock(Scene.class));
        sceneController.step(gameMock, action, frameCount);

        verify(gameMock).setState(any(GameState.class));
        verify(playerControllerMock).step(gameMock, action, frameCount);
        verify(particleControllerMock).step(gameMock, action, frameCount);
    }

    @Test
    void testStepUpdateStarsAndParticles() throws IOException, URISyntaxException {
        GUI.Action action = GUI.Action.NONE;
        long frameCount = 0;
        when(sceneMock.isAtTransitionPosition()).thenReturn(false);
        sceneController.step(gameMock, action, frameCount);
        verify(playerControllerMock).step(gameMock, action, frameCount);
        verify(sceneMock).updateStars();
        verify(particleControllerMock).step(gameMock, action, frameCount);
    }
}
