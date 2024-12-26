package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AfterDashStateTest {

    @Mock
    private Player player;

    @Mock
    private Scene scene;

    private AfterDashState afterDashState;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(player.getScene()).thenReturn(scene);
        when(player.getVelocity()).thenReturn(new Vector(0, 0));
        when(player.getPosition()).thenReturn(new Vector(0, 0));
        when(player.getMaxVelocity()).thenReturn(new Vector(10, 10));
        when(scene.getFriction()).thenReturn(0.9);
        when(scene.getGravity()).thenReturn(0.5);
        afterDashState = new AfterDashState(player);
    }

    @Test
    void testJump() {
        Vector initialVelocity = new Vector(5, 0);
        when(player.getVelocity()).thenReturn(initialVelocity);

        Vector result = afterDashState.jump();

        assertEquals(4.5, result.x(), 0.001);
        assertEquals(0.5, result.y(), 0.001);
    }

    @Test
    void testDash() {
        Vector initialVelocity = new Vector(5, 0);
        Vector initialPosition = new Vector(10, 10);
        when(player.getVelocity()).thenReturn(initialVelocity);
        when(player.getPosition()).thenReturn(initialPosition);

        Vector result = afterDashState.dash();

        assertEquals(4.5, result.x(), 0.001);
        assertEquals(0.5, result.y(), 0.001);
    }

    @Test
    void testUpdateVelocity() {
        Vector initialVelocity = new Vector(5, 0);

        Vector result = afterDashState.updateVelocity(initialVelocity);

        assertEquals(4.5, result.x(), 0.001);
        assertEquals(0.5, result.y(), 0.001);
    }

    @Test
    void testGetNextState_Dying() {
        when(scene.isPlayerDying()).thenReturn(true);

        PlayerState nextState = afterDashState.getNextState();

        assertTrue(nextState instanceof DeadState);
    }

    @Test
    void testGetNextState_OnGround() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOnGround()).thenReturn(true);

        PlayerState nextState = afterDashState.getNextState();

        assertInstanceOf(IdleState.class, nextState);
    }

    @Test
    void testGetNextState_InAir() {
        when(scene.isPlayerDying()).thenReturn(false);
        when(player.isOnGround()).thenReturn(false);

        PlayerState nextState = afterDashState.getNextState();

        assertSame(afterDashState, nextState);
    }
}
