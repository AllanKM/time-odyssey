package timelessodyssey.model.game.scene;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.Spike;
import timelessodyssey.model.game.elements.Star;
import timelessodyssey.model.game.elements.Tile;
import timelessodyssey.model.game.elements.particles.Particle;
import timelessodyssey.model.game.elements.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SceneTest {

    @Mock
    private Player player;

    private Scene scene;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        scene = new Scene(10, 10, 1);
        scene.setPlayer(player);
        when(player.getWidth()).thenReturn(6);
        when(player.getHeight()).thenReturn(8);
        when(player.getPosition()).thenReturn(new Vector(5, 5));
    }

    @Test
    public void testSceneInitialization() {
        assertEquals(10, scene.getWidth());
        assertEquals(10, scene.getHeight());
        assertEquals(1, scene.getSceneCode());
        assertEquals(0.25, scene.getGravity(), 0.001);
        assertEquals(0.75, scene.getFriction(), 0.001);
    }

    @Test
    public void testIsPlayerDying_NoCollision() {
        Spike[][] spikes = new Spike[10][10];
        scene.setSpikes(spikes);
        assertFalse(scene.isPlayerDying());
    }

    @Test
    public void testIsPlayerDying_WithCollision() {
        Spike[][] spikes = new Spike[10][10];
        spikes[1][1] = new Spike(1 * Tile.SIZE, 1 * Tile.SIZE, 'S');
        scene.setSpikes(spikes);
        when(player.getPosition()).thenReturn(new Vector(1 * Tile.SIZE, 1 * Tile.SIZE));
        assertTrue(scene.isPlayerDying());
    }

    @Test
    public void testUpdateStars_CatchStar() {
        Star[][] stars = new Star[10][10];
        stars[0][0] = new Star(0 * Tile.SIZE, 0 * Tile.SIZE);
        scene.setStars(stars);
        when(player.getPosition()).thenReturn(new Vector(0 * Tile.SIZE, 0 * Tile.SIZE));
        boolean caughtStar = scene.updateStars();
        assertTrue(caughtStar);
        assertNull(stars[0][0]);
    }

    @Test
    public void testUpdateStars_NoStarCaught() {
        Star[][] stars = new Star[10][10];
        scene.setStars(stars);
        assertFalse(scene.updateStars());
    }

    @Test
    public void testTransitionPositions() {
        scene.setTransitionPositionBegin(new Vector(1, 1));
        scene.setTransitionPositionEnd(new Vector(5, 5));
        assertEquals(new Vector(1, 1), scene.getTransitionPositionBegin());
        assertEquals(new Vector(5, 5), scene.getTransitionPositionEnd());
    }

    @Test
    public void testIsAtTransitionPosition() {
        scene.setTransitionPositionBegin(new Vector(1, 1));
        scene.setTransitionPositionEnd(new Vector(5, 5));
        when(player.getPosition()).thenReturn(new Vector(3, 3));
        assertTrue(scene.isAtTransitionPosition());
        when(player.getPosition()).thenReturn(new Vector(6, 6));
        assertFalse(scene.isAtTransitionPosition());
    }

    @Test
    public void testCheckCollision_OutsideScene() {
        Tile[][] tiles = new Tile[10][10];
        scene.setTiles(tiles);
        assertTrue(scene.collidesLeft(new Vector(-1, 0), new Vector(2, 2)));
    }

    @Test
    public void testCheckCollision_InsideScene() {
        Tile[][] tiles = new Tile[10][10];
        tiles[1][1] = new Tile(1 * Tile.SIZE, 1 * Tile.SIZE, 'T');
        scene.setTiles(tiles);
        assertTrue(scene.collidesUp(new Vector(1 * Tile.SIZE, 1 * Tile.SIZE), new Vector(Tile.SIZE, Tile.SIZE)));
    }

    @Test
    public void testCollidesUp_NoCollision() {
        Tile[][] tiles = new Tile[10][10];
        scene.setTiles(tiles);
        assertFalse(scene.collidesUp(new Vector(5, 5), new Vector(2, 2)));
    }

    @Test
    public void testCollidesDown_NoCollision() {
        Tile[][] tiles = new Tile[10][10];
        scene.setTiles(tiles);
        assertFalse(scene.collidesDown(new Vector(5, 5), new Vector(2, 2)));
    }

    @Test
    public void testCollidesDown_WithCollision() {
        Tile[][] tiles = new Tile[10][10];
        tiles[7][5] = new Tile(5 * Tile.SIZE, 7 * Tile.SIZE, 'T');
        scene.setTiles(tiles);
        assertFalse(scene.collidesDown(new Vector(5 * Tile.SIZE, 6 * Tile.SIZE), new Vector(Tile.SIZE, Tile.SIZE)));
    }

    @Test
    public void testGetDeathParticles() {
        scene.setDeathParticles(Collections.emptyList());
        assertTrue(scene.getDeathParticles().isEmpty());
    }

    @Test
    public void testCheckOutsideScene() {
        assertTrue(scene.collidesLeft(new Vector(-1, 0), new Vector(1, 1)));
        assertTrue(scene.collidesRight(new Vector(scene.getWidth() * Tile.SIZE + 1, 0), new Vector(1, 1)));
        assertTrue(scene.collidesUp(new Vector(0, -1), new Vector(1, 1)));
        assertTrue(scene.collidesDown(new Vector(0, scene.getHeight() * Tile.SIZE + 1), new Vector(1, 1)));
    }

    @Test
    public void testUpdateStars_MultipleStarsCaught() {
        Star[][] stars = new Star[10][10];
        stars[0][0] = new Star(0 * Tile.SIZE, 0 * Tile.SIZE);
        stars[0][1] = new Star(1 * Tile.SIZE, 0 * Tile.SIZE);
        scene.setStars(stars);
        when(player.getPosition()).thenReturn(new Vector(0 * Tile.SIZE, 0 * Tile.SIZE));
        boolean caughtStar = scene.updateStars();
        assertTrue(caughtStar);
        assertNull(stars[0][0]);
        assertNotNull(stars[0][1]); // Player should not catch this one
    }
}
