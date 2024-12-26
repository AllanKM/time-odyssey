package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerStateTest {

    @Mock
    private Player player;

    @Mock
    private Scene scene;

    private TestPlayerState testPlayerState; // A concrete implementation for testing

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(player.getScene()).thenReturn(scene);
        when(player.getVelocity()).thenReturn(new Vector(0, 0));
        when(player.getWidth()).thenReturn(6);
        when(player.getHeight()).thenReturn(8);
        when(player.getMaxVelocity()).thenReturn(new Vector(2.0, 3.0));
        when(player.getPosition()).thenReturn(new Vector(0, 0)); // Ensure position is initialized
        testPlayerState = new TestPlayerState(player); // Use a concrete subclass for testing
    }

    @Test
    void testMovePlayerLeft() {
        when(player.getVelocity()).thenReturn(new Vector(1, 0));
        Vector result = testPlayerState.movePlayerLeft();

        assertEquals(1.0, result.x(), 0.001); // Velocity should be capped at zero
        assertEquals(0, result.y(), 0.001); // Vertical velocity remains unchanged
    }

    @Test
    void testMovePlayerRight() {
        when(player.getVelocity()).thenReturn(new Vector(1, 0));
        Vector result = testPlayerState.movePlayerRight();

        assertEquals(1.0, result.x(), 0.001); // Velocity should increase by acceleration (1 + 0.75)
        assertEquals(0.0, result.y(), 0.001); // Vertical velocity remains unchanged
    }

    @Test
    void testApplyCollisions() {
        Vector velocity = new Vector(5, 5);
        when(scene.collidesDown(any(), any())).thenReturn(false);
        when(scene.collidesUp(any(), any())).thenReturn(false);
        when(scene.collidesLeft(any(), any())).thenReturn(false);
        when(scene.collidesRight(any(), any())).thenReturn(false);

        Vector result = testPlayerState.applyCollisions(velocity);

        assertEquals(5, result.x(), 0.001); // No collision, velocity remains the same
        assertEquals(5, result.y(), 0.001); // No collision, velocity remains the same

        // Simulate a collision downwards
        when(scene.collidesDown(any(), any())).thenReturn(true);

        result = testPlayerState.applyCollisions(velocity);

        assertEquals(5, result.x(), 0.001); // Horizontal velocity should remain the same
        assertEquals(0, result.y(), 0.001); // Vertical velocity should be capped at zero due to collision
    }

    @Test
    void testLimitVelocity() {
        Vector highVelocity = new Vector(10, 4); // Exceeds max x velocity and y velocity
        Vector limitedVelocity = testPlayerState.limitVelocity(highVelocity);

        assertEquals(2.0, limitedVelocity.x(), 0.001); // Should be capped to max x velocity
        assertEquals(3.0, limitedVelocity.y(), 0.001); // Should be capped to max y velocity

        Vector lowVelocity = new Vector(0.1, 2);

        limitedVelocity = testPlayerState.limitVelocity(lowVelocity);

        assertEquals(0, limitedVelocity.x(), 0.001); // Should be set to zero if below threshold
        assertEquals(2, limitedVelocity.y(), 0.001); // Should remain unchanged
    }

    private static class TestPlayerState extends PlayerState {

        public TestPlayerState(Player player) {
            super(player);
        }

        @Override
        public Vector jump() {
            return new Vector(0, -getPlayer().getJumpBoost());
        }

        @Override
        public Vector dash() {
            return new Vector(getPlayer().isFacingRight() ? getPlayer().getDashBoost() : -getPlayer().getDashBoost(), getPlayer().getVelocity().y());
        }

        @Override
        public Vector updateVelocity(Vector velocity) {
            return new Vector(velocity.x(), velocity.y());
        }

        @Override
        public PlayerState getNextState() {
            return this; // For testing purposes, just return itself.
        }
    }
}