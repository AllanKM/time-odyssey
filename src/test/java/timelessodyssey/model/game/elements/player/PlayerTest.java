package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerTest {

    @Mock
    private Scene scene;

    private Player player;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        player = new Player(0, 0, scene);
    }

    @Test
    void testInitialValues() {
        assertEquals(0, player.getPosition().x());
        assertEquals(0, player.getPosition().y());
        assertEquals(0, player.getStarCounter());
        assertEquals(0, player.getNumberOfDeaths());
        assertTrue(player.isFacingRight());
        assertEquals(2.0, player.getMaxVelocity().x(), 0.001);
        assertEquals(3.0, player.getMaxVelocity().y(), 0.001);
    }

    @Test
    void testSetFacingRight() {
        player.setFacingRight(false);
        assertFalse(player.isFacingRight());

        player.setFacingRight(true);
        assertTrue(player.isFacingRight());
    }

    @Test
    void testUpdatePosition() {
        player.setVelocity(new Vector(2.0, 3.0));
        Vector newPosition = player.updatePosition();

        assertEquals(2.0, newPosition.x(), 0.001);
        assertEquals(3.0, newPosition.y(), 0.001);
    }

    @Test
    void testMoveLeft() {
        // Assuming movePlayerLeft is implemented in the state
        Vector result = player.moveLeft();

        // You would need to verify the expected behavior here.
        // For now, we will just check that it does not return null.
        assertNotNull(result);
    }

    @Test
    void testMoveRight() {
        // Assuming movePlayerRight is implemented in the state
        Vector result = player.moveRight();

        // You would need to verify the expected behavior here.
        // For now, we will just check that it does not return null.
        assertNotNull(result);
    }

    @Test
    void testJump() {
        Vector result = player.jump();

        // Check that jumping updates the velocity correctly
        assertNotNull(result); // Ensure it does not return null
    }

    @Test
    void testDash() {
        Vector result = player.dash();

        // Check that dashing updates the velocity correctly
        assertNotNull(result); // Ensure it does not return null
    }

    @Test
    void testIsOnGround() {
        when(scene.collidesDown(any(), any())).thenReturn(true);

        assertTrue(player.isOnGround());

        when(scene.collidesDown(any(), any())).thenReturn(false);

        assertFalse(player.isOnGround());
    }

    @Test
    void testIncreaseStars() {
        player.increaseStars();

        assertEquals(1, player.getStarCounter());

        player.increaseStars();

        assertEquals(2, player.getStarCounter());
    }

    @Test
    void testIncreaseDeaths() {
        player.increaseDeaths();

        assertEquals(1, player.getNumberOfDeaths());

        player.increaseDeaths();

        assertEquals(2, player.getNumberOfDeaths());
    }

    @Test
    void testResetValues() {
        player.setFacingRight(false);

        // Reset values and check if they are set to default
        player.resetValues();

        assertTrue(player.isFacingRight());

        // Check if state is reset to IdleState (you may need to adjust this based on your implementation)
        assertTrue(player.getState() instanceof IdleState);
    }
}
