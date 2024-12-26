package timelessodyssey.control.game;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.player.IdleState;
import timelessodyssey.model.game.elements.player.Player;
import timelessodyssey.model.game.scene.Scene;

class PlayerControllerTest {

    private PlayerController playerController;
    private Scene sceneMock;
    private Player playerMock;
    private Game gameMock;

    @BeforeEach
    void setUp() {
        // Mock the Scene, Player, and Game objects
        sceneMock = mock(Scene.class);
        playerMock = mock(Player.class);
        gameMock = mock(Game.class);

        // Configure the player to return the mocked scene
        when(playerMock.getScene()).thenReturn(sceneMock);

        // Configure the scene to return the mocked player
        when(sceneMock.getPlayer()).thenReturn(playerMock);

        // Instantiate the PlayerController with the mocked scene
        playerController = new PlayerController(sceneMock);
    }

    @Test
    void testStepLeftAction() {
        // Arrange
        GUI.Action action = GUI.Action.LEFT;
        long frameCount = 0;

        // Mock player behavior
        when(playerMock.moveLeft()).thenReturn(mock(Vector.class));

        // Act
        playerController.step(gameMock, action, frameCount);

        // Assert
        verify(playerMock).setVelocity(any(Vector.class));
        verify(playerMock).setFacingRight(false);
        verify(playerMock).updatePosition();
        verify(playerMock).getNextState();
    }

    @Test
    void testStepRightAction() {
        // Arrange
        GUI.Action action = GUI.Action.RIGHT;
        long frameCount = 0;

        // Mock player behavior
        when(playerMock.moveRight()).thenReturn(mock(Vector.class));

        // Act
        playerController.step(gameMock, action, frameCount);

        // Assert
        verify(playerMock).setVelocity(any(Vector.class));
        verify(playerMock).setFacingRight(true);
        verify(playerMock).updatePosition();
        verify(playerMock).getNextState();
    }

    @Test
    void testStepJumpAction() {
        // Arrange
        GUI.Action action = GUI.Action.JUMP;
        long frameCount = 0;

        // Mock player behavior
        when(playerMock.jump()).thenReturn(mock(Vector.class));

        // Act
        playerController.step(gameMock, action, frameCount);

        // Assert
        verify(playerMock).setVelocity(any(Vector.class));
        verify(playerMock).updatePosition();
        verify(playerMock).getNextState();
    }

    @Test
    void testStepDashAction() {
        // Arrange
        GUI.Action action = GUI.Action.DASH;
        long frameCount = 0;

        // Mock player behavior
        when(playerMock.dash()).thenReturn(mock(Vector.class));

        // Act
        playerController.step(gameMock, action, frameCount);

        // Assert
        verify(playerMock).setVelocity(any(Vector.class));
        verify(playerMock).updatePosition();
        verify(playerMock).getNextState();
    }

    @Test
    void testStepDefaultAction() {
        // Arrange
        GUI.Action action = GUI.Action.NONE;
        long frameCount = 0;

        // Mock player behavior
        when(playerMock.updateVelocity()).thenReturn(mock(Vector.class));

        // Act
        playerController.step(gameMock, action, frameCount);

        // Assert
        verify(playerMock).setVelocity(any(Vector.class));
        verify(playerMock).updatePosition();
        verify(playerMock).getNextState();
    }

    @Test
    void testStepNullState() {
        // Arrange
        GUI.Action action = GUI.Action.NONE;
        long frameCount = 0;

        // Mock player state to be null
        when(playerMock.getNextState()).thenReturn(null);
        when(playerMock.getScene()).thenReturn(sceneMock);
        when(sceneMock.getStartingPosition()).thenReturn(mock(Vector.class));

        // Act
        playerController.step(gameMock, action, frameCount);

        // Assert
        verify(playerMock).increaseDeaths();
        verify(sceneMock).setDeathParticles(any(ArrayList.class));
        verify(playerMock).setPosition(any(Vector.class));
        verify(playerMock).setState(any(IdleState.class));
    }
}
