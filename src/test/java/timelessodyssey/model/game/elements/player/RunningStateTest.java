package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RunningStateTest {

    @Mock
    private Player player;

    @Mock
    private Scene scene;

    private RunningState runningState;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(player.getScene()).thenReturn(scene);
        when(player.getVelocity()).thenReturn(new Vector(1.8, 0));
        when(player.getPosition()).thenReturn(new Vector(0, 0));
        when(player.getJumpBoost()).thenReturn(3.6);
        when(player.getMaxVelocity()).thenReturn(new Vector(2.0, 3.0));
        when(scene.getFriction()).thenReturn(0.9);
        runningState = new RunningState(player);
    }

    @Test
    public void testJump() {
        Vector result = runningState.jump();

        assertEquals(1.62, result.x(), 0.001);
        assertEquals(-3.6, result.y(), 0.001);
    }

    @Test
    public void testDashFacingRight() {
        when(player.isFacingRight()).thenReturn(true);
        when(player.getDashBoost()).thenReturn(5.0);

        Vector result = runningState.dash();

        assertEquals(5.0, result.x(), 0.001);
        assertEquals(0.0, result.y(), 0.001);
    }

    @Test
    public void testDashFacingLeft() {
        when(player.isFacingRight()).thenReturn(false);
        when(player.getDashBoost()).thenReturn(5.0);

        Vector result = runningState.dash();

        assertEquals(-5.0, result.x(), 0.001);
        assertEquals(0.0, result.y(), 0.001);
    }

    @Test
    public void testUpdateVelocity() {
        Vector initialVelocity = new Vector(1.8, 2);
        Vector result = runningState.updateVelocity(initialVelocity);
        assertEquals(1.62, result.x(), 0.001);
        assertEquals(2, result.y(), 0.001);
    }

    @Test
    public void testGetNextState_Dying() {
        when(scene.isPlayerDying()).thenReturn(true);
        PlayerState nextState = runningState.getNextState();
        assertTrue(nextState instanceof DeadState);
    }

    @Test
    public void testGetNextState_OverMaxXVelocity() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(true);

        PlayerState nextState = runningState.getNextState();

        assertTrue(nextState instanceof DashingState);
    }

    @Test
    public void testGetNextState_Falling() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(false);
        when(player.getVelocity()).thenReturn(new Vector(1.8, 1));

        PlayerState nextState = runningState.getNextState();

        assertTrue(nextState instanceof FallingState);
    }

    @Test
    public void testGetNextState_Walking() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(false);
        when(player.getVelocity()).thenReturn(new Vector(RunningState.MIN_VELOCITY - 0.1, 0));

        PlayerState nextState = runningState.getNextState();

        assertFalse(nextState instanceof WalkingState);
    }
}
