package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IdleStateTest {

    @Mock
    private Player player;

    @Mock
    private Scene scene;

    private IdleState idleState;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(player.getScene()).thenReturn(scene);
        when(player.getVelocity()).thenReturn(new Vector(0, 0));
        when(player.getPosition()).thenReturn(new Vector(0, 0));
        when(player.getJumpBoost()).thenReturn(5.0);
        when(player.getMaxVelocity()).thenReturn(new Vector(10, 10));
        when(scene.getFriction()).thenReturn(0.9);
        idleState = new IdleState(player);
    }

    @Test
    void testJump() {
        Vector result = idleState.jump();

        assertEquals(0, result.x(), 0.001);
        assertEquals(-5.0, result.y(), 0.001);
    }

    @Test
    void testDashFacingRight() {
        when(player.isFacingRight()).thenReturn(true);
        when(player.getDashBoost()).thenReturn(3.0);

        Vector result = idleState.dash();

        assertEquals(3.0, result.x(), 0.001);
        assertEquals(0.0, result.y(), 0.001);
    }

    @Test
    void testDashFacingLeft() {
        when(player.isFacingRight()).thenReturn(false);
        when(player.getDashBoost()).thenReturn(3.0);

        Vector result = idleState.dash();

        assertEquals(-3.0, result.x(), 0.001);
        assertEquals(0.0, result.y(), 0.001);
    }

    @Test
    void testUpdateVelocity() {
        Vector initialVelocity = new Vector(5, 2);

        Vector result = idleState.updateVelocity(initialVelocity);

        assertEquals(4.5, result.x(), 0.001);
        assertEquals(2, result.y(), 0.001);
    }

    @Test
    void testGetNextState_Dying() {
        when(scene.isPlayerDying()).thenReturn(true);

        PlayerState nextState = idleState.getNextState();

        assertTrue(nextState instanceof DeadState);
    }

    @Test
    void testGetNextState_OverMaxXVelocity() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(true);

        PlayerState nextState = idleState.getNextState();

        assertTrue(nextState instanceof DashingState);
    }

    @Test
    void testGetNextState_OnAir() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(false);
        when(player.isOnGround()).thenReturn(false);

        PlayerState nextState = idleState.getNextState();
    }

    @Test
    void testGetNextState_Walking() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(false);
        when(player.isOnGround()).thenReturn(true);

        when(player.getVelocity()).thenReturn(new Vector(WalkingState.MIN_VELOCITY, 0));

        PlayerState nextState = idleState.getNextState();

        assertTrue(nextState instanceof WalkingState);
    }
}
