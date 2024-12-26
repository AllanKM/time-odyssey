package timelessodyssey.model.game.scene;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.player.Player;
import timelessodyssey.model.game.elements.Spike;
import timelessodyssey.model.game.elements.Tile;
import timelessodyssey.model.game.elements.Star;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SceneTest {

    @Mock
    private Player player;

    private Scene scene;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scene = new Scene(10, 10, 1); // Initialize a scene with width 10 and height 10
        scene.setPlayer(player); // Set the player in the scene
        when(player.getWidth()).thenReturn(6);
        when(player.getHeight()).thenReturn(8);
        when(player.getPosition()).thenReturn(new Vector(5, 5)); // Set initial position of player
    }

    @Test
    void testSceneInitialization() {
        assertEquals(10, scene.getWidth());
        assertEquals(10, scene.getHeight());
        assertEquals(1, scene.getSceneCode());
        assertEquals(0.25, scene.getGravity(), 0.001);
        assertEquals(0.75, scene.getFriction(), 0.001);
    }

//    @Test
//    void testCollidesLeft_NoCollision() {
//        Vector position = new Vector(5, 5);
//        Vector size = new Vector(player.getWidth(), player.getHeight());
//
//        // Ensure no collision is detected
//        when(scene.collidesLeft(position, size)).thenReturn(false);
//
//        assertFalse(scene.collidesLeft(position, size));
//    }

//    @Test
//    void testCollidesLeft_WithCollision() {
//        Vector position = new Vector(5, 5);
//        Vector size = new Vector(6, 8); // Use actual values directly instead of calling player methods
//
//        // Ensure collision is detected
//        when(scene.collidesLeft(position, size)).thenReturn(true);
//
//        assertTrue(scene.collidesLeft(position, size));
//    }

    @Test
    void testIsPlayerDying_NoCollision() {
        Spike[][] spikes = new Spike[10][10];
        scene.setSpikes(spikes); // Ensure no spikes are present

        // Simulate player position not colliding with spikes
        when(player.getPosition()).thenReturn(new Vector(5, 5));

        assertFalse(scene.isPlayerDying());
    }

    @Test
    void testIsPlayerDying_WithCollision() {
        // Set up a spike in the scene to simulate a collision
        Spike[][] spikes = new Spike[10][10];
        spikes[1][1] = new Spike(1 * Tile.SIZE, 1 * Tile.SIZE, 'S'); // Place a spike at (1,1)
        scene.setSpikes(spikes);

        when(player.getPosition()).thenReturn(new Vector(1 * Tile.SIZE, 1 * Tile.SIZE)); // Simulate player position on spike

        assertTrue(scene.isPlayerDying());
    }

    @Test
    void testUpdateStars_CatchStar() {
        Star[][] stars = new Star[10][10];
        stars[0][0] = new Star(0 * Tile.SIZE, 0 * Tile.SIZE); // Place a star at (0,0)
        scene.setStars(stars);

        when(player.getPosition()).thenReturn(new Vector(0 * Tile.SIZE, 0 * Tile.SIZE)); // Simulate player position on star

        boolean caughtStar = scene.updateStars();

        assertTrue(caughtStar); // Ensure the star was caught
        assertNull(stars[0][0]); // Ensure the star is removed from the scene
    }

    @Test
    void testUpdateStars_NoStarCaught() {
        Star[][] stars = new Star[10][10];
        stars[1][1] = new Star(1 * Tile.SIZE, 1 * Tile.SIZE); // Place a star at (1,1)
        scene.setStars(stars);

        when(player.getPosition()).thenReturn(new Vector(0 * Tile.SIZE, 0 * Tile.SIZE)); // Simulate player position not on star

        boolean caughtStar = scene.updateStars();

        assertFalse(caughtStar); // Ensure no star was caught
    }
}
