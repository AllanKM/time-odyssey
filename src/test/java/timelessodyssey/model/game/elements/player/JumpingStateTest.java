package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JumpingStateTest {

    @Mock
    private Player player;

    @Mock
    private Scene scene;

    private JumpingState jumpingState;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(player.getScene()).thenReturn(scene);
        when(player.getVelocity()).thenReturn(new Vector(0, 5)); // Initial upward velocity
        when(player.getPosition()).thenReturn(new Vector(0, 0));
        when(player.getJumpBoost()).thenReturn(5.0);
        when(player.getMaxVelocity()).thenReturn(new Vector(10, 10));
        when(scene.getFriction()).thenReturn(0.9);
        when(scene.getGravity()).thenReturn(0.5);
        jumpingState = new JumpingState(player);
    }

    @Test
    void testJump() {
        Vector result = jumpingState.jump();

        assertEquals(0, result.x(), 0.001); // Horizontal velocity remains unchanged
        assertEquals(5.5, result.y(), 0.001); // Upward velocity remains unchanged
    }

    @Test
    void testDashFacingRight() {
        when(player.isFacingRight()).thenReturn(true);
        when(player.getDashBoost()).thenReturn(3.0);

        Vector result = jumpingState.dash();

        assertEquals(3.0, result.x(), 0.001); // Dash boost to the right
        assertEquals(5.0, result.y(), 0.001); // Vertical velocity should remain unchanged
    }

    @Test
    void testDashFacingLeft() {
        when(player.isFacingRight()).thenReturn(false);
        when(player.getDashBoost()).thenReturn(3.0);

        Vector result = jumpingState.dash();

        assertEquals(-3.0, result.x(), 0.001); // Dash boost to the left
        assertEquals(5.0, result.y(), 0.001); // Vertical velocity should remain unchanged
    }

    @Test
    void testUpdateVelocity() {
        Vector initialVelocity = new Vector(5, 2); // Initial horizontal and vertical velocity

        Vector result = jumpingState.updateVelocity(initialVelocity);

        assertEquals(4.5, result.x(), 0.001); // Friction applied to x velocity (5 * 0.9)
        assertEquals(2.5, result.y(), 0.001); // Gravity applied to y velocity (2 + 0.5)
    }

    @Test
    void testGetNextState_Dying() {
        when(scene.isPlayerDying()).thenReturn(true);

        PlayerState nextState = jumpingState.getNextState();

        assertTrue(nextState instanceof DeadState);
    }

    @Test
    void testGetNextState_OverMaxXVelocity() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(true);

        PlayerState nextState = jumpingState.getNextState();

        assertTrue(nextState instanceof DashingState);
    }

    @Test
    void testGetNextState_Falling() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(false);

        // Set vertical velocity to be non-negative to trigger falling state transition
        when(player.getVelocity()).thenReturn(new Vector(0, 1));

        PlayerState nextState = jumpingState.getNextState();

        assertTrue(nextState instanceof FallingState); // Should transition to Falling state
    }

    @Test
    void testGetNextState_StillJumping() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOverMaxXVelocity()).thenReturn(false);

        // Set vertical velocity to be positive to stay in jumping state
        when(player.getVelocity()).thenReturn(new Vector(0, -1));

        PlayerState nextState = jumpingState.getNextState();

        assertSame(jumpingState, nextState); // Should return itself if still jumping
    }
}
