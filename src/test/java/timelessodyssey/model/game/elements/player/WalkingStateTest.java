package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalkingStateTest {

    @Mock
    private Player player;

    @Mock
    private Scene scene;

    private WalkingState walkingState;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(player.getScene()).thenReturn(scene);
        when(player.getVelocity()).thenReturn(new Vector(1.0, 0));
        when(player.getPosition()).thenReturn(new Vector(0, 0));
        when(player.getJumpBoost()).thenReturn(3.6);
        when(player.getMaxVelocity()).thenReturn(new Vector(2.0, 3.0));
        when(scene.getFriction()).thenReturn(0.9);
        walkingState = new WalkingState(player);
    }

    @Test
    void testJump() {
        Vector result = walkingState.jump();

        assertEquals(0.9, result.x(), 0.001);
        assertEquals(-3.6, result.y(), 0.001);
    }

    @Test
    void testDashFacingRight() {
        when(player.isFacingRight()).thenReturn(true);
        when(player.getDashBoost()).thenReturn(5.0);

        Vector result = walkingState.dash();

        assertEquals(5.0, result.x(), 0.001);
        assertEquals(0.0, result.y(), 0.001);
    }

    @Test
    void testDashFacingLeft() {
        when(player.isFacingRight()).thenReturn(false);
        when(player.getDashBoost()).thenReturn(5.0);

        Vector result = walkingState.dash();

        assertEquals(-5.0, result.x(), 0.001);
        assertEquals(0.0, result.y(), 0.001);
    }

    @Test
    void testUpdateVelocity() {
        Vector initialVelocity = new Vector(1.5, 2);

        Vector result = walkingState.updateVelocity(initialVelocity);

        assertEquals(1.35, result.x(), 0.001);
        assertEquals(2, result.y(), 0.001);
    }

    @Test
    void testGetNextState_Dying() {
        when(scene.isPlayerDying()).thenReturn(true);
        PlayerState nextState = walkingState.getNextState();
        assertTrue(nextState instanceof DeadState);
    }

    @Test
    void testGetNextState_OverMaxXVelocity() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(true);
        PlayerState nextState = walkingState.getNextState();
        assertTrue(nextState instanceof DashingState);
    }

    @Test
    void testGetNextState_Falling() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(false);
        when(player.getVelocity()).thenReturn(new Vector(1.0, 1));

        PlayerState nextState = walkingState.getNextState();

        assertTrue(nextState instanceof FallingState);
    }

    @Test
    void testGetNextState_Running() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(false);
        when(player.getVelocity()).thenReturn(new Vector(RunningState.MIN_VELOCITY, 0));
        PlayerState nextState = walkingState.getNextState();
        assertFalse(nextState instanceof RunningState);
    }

    @Test
    void testGetNextState_Idle() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(false);
        when(player.getVelocity()).thenReturn(new Vector(WalkingState.MIN_VELOCITY - 0.1, 0));
        PlayerState nextState = walkingState.getNextState();
        assertFalse(nextState instanceof IdleState);
    }
}