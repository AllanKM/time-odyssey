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
    public void setUp() {
        sceneMock = mock(Scene.class);
        playerMock = mock(Player.class);
        gameMock = mock(Game.class);

        when(playerMock.getScene()).thenReturn(sceneMock);
        when(sceneMock.getPlayer()).thenReturn(playerMock);
        playerController = new PlayerController(sceneMock);
    }

    @Test
    public void testStepLeftAction() {
        GUI.Action action = GUI.Action.LEFT;
        long frameCount = 0;

        when(playerMock.moveLeft()).thenReturn(mock(Vector.class));
        playerController.step(gameMock, action, frameCount);

        verify(playerMock).setVelocity(any(Vector.class));
        verify(playerMock).setFacingRight(false);
        verify(playerMock).updatePosition();
        verify(playerMock).getNextState();
    }

    @Test
    public void testStepRightAction() {
        GUI.Action action = GUI.Action.RIGHT;
        long frameCount = 0;
        when(playerMock.moveRight()).thenReturn(mock(Vector.class));
        playerController.step(gameMock, action, frameCount);
        verify(playerMock).setVelocity(any(Vector.class));
        verify(playerMock).setFacingRight(true);
        verify(playerMock).updatePosition();
        verify(playerMock).getNextState();
    }

    @Test
    public void testStepJumpAction() {
        GUI.Action action = GUI.Action.JUMP;
        long frameCount = 0;
        when(playerMock.jump()).thenReturn(mock(Vector.class));
        playerController.step(gameMock, action, frameCount);
        verify(playerMock).setVelocity(any(Vector.class));
        verify(playerMock).updatePosition();
        verify(playerMock).getNextState();
    }

    @Test
    public void testStepDashAction() {
        GUI.Action action = GUI.Action.DASH;
        long frameCount = 0;
        when(playerMock.dash()).thenReturn(mock(Vector.class));
        playerController.step(gameMock, action, frameCount);
        verify(playerMock).setVelocity(any(Vector.class));
        verify(playerMock).updatePosition();
        verify(playerMock).getNextState();
    }

    @Test
    public void testStepDefaultAction() {
        GUI.Action action = GUI.Action.NONE;
        long frameCount = 0;
        when(playerMock.updateVelocity()).thenReturn(mock(Vector.class));
        playerController.step(gameMock, action, frameCount);
        verify(playerMock).setVelocity(any(Vector.class));
        verify(playerMock).updatePosition();
        verify(playerMock).getNextState();
    }

    @Test
    public void testStepNullState() {
        GUI.Action action = GUI.Action.NONE;
        long frameCount = 0;

        when(playerMock.getNextState()).thenReturn(null);
        when(playerMock.getScene()).thenReturn(sceneMock);
        when(sceneMock.getStartingPosition()).thenReturn(mock(Vector.class));
        playerController.step(gameMock, action, frameCount);

        verify(playerMock).increaseDeaths();
        verify(sceneMock).setDeathParticles(any(ArrayList.class));
        verify(playerMock).setPosition(any(Vector.class));
        verify(playerMock).setState(any(IdleState.class));
    }
}
