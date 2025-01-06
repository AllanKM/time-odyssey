package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DashingStateTest {

    @Mock
    private Player player;

    @Mock
    private Scene scene;

    private DashingState dashingState;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(player.getScene()).thenReturn(scene);
        when(player.getVelocity()).thenReturn(new Vector(5, 0));
        when(player.getPosition()).thenReturn(new Vector(0, 0));
        when(player.getMaxVelocity()).thenReturn(new Vector(10, 10));
        when(scene.getFriction()).thenReturn(0.9);
        when(scene.getGravity()).thenReturn(0.5);
        dashingState = new DashingState(player);
    }

    @Test
    public void testJump() {
        Vector result = dashingState.jump();

        assertEquals(4.5, result.x(), 0.001);
        assertEquals(0.5, result.y(), 0.001);
    }

    @Test
    public void testDash() {
        Vector result = dashingState.dash();

        assertEquals(4.5, result.x(), 0.001);
        assertEquals(0.5, result.y(), 0.001);
    }

    @Test
    public void testUpdateVelocity() {
        Vector initialVelocity = new Vector(5, 0);

        Vector result = dashingState.updateVelocity(initialVelocity);

        assertEquals(4.5, result.x(), 0.001);
        assertEquals(0.5, result.y(), 0.001);
    }

    @Test
    public void testGetNextState_Dying() {
        when(scene.isPlayerDying()).thenReturn(true);

        PlayerState nextState = dashingState.getNextState();

        assertTrue(nextState instanceof DeadState);
    }

    @Test
    public void testGetNextState_BelowMinVelocity() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.getVelocity()).thenReturn(new Vector(1.5, 0));

        PlayerState nextState = dashingState.getNextState();

        assertTrue(nextState instanceof AfterDashState);
    }

    @Test
    public void testGetNextState_AboveMinVelocity() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.getVelocity()).thenReturn(new Vector(3, 0));

        PlayerState nextState = dashingState.getNextState();

        assertSame(dashingState, nextState);
    }
}
