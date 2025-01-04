package timelessodyssey.model.game.scene;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.Spike;
import timelessodyssey.model.game.elements.Star;
import timelessodyssey.model.game.elements.Tile;
import timelessodyssey.model.game.elements.player.Player;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SceneBuilderTest {
    private SceneBuilder sceneBuilder;
    private Scene scene;
    private Player player;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        sceneBuilder = new SceneBuilder(1);
        scene = new Scene(60, 20, 1);
        player = new Player(0, 0, scene);
    }

    @Test
    void testCreateScene() {
        Scene createdScene = sceneBuilder.createScene(player);
        assertNotNull(createdScene);
        assertEquals(player, createdScene.getPlayer());
        assertEquals(player.getPosition(), createdScene.getStartingPosition());
        assertNotNull(createdScene.getTiles());
        assertNotNull(createdScene.getSpikes());
        assertNotNull(createdScene.getStars());
        assertNotNull(createdScene.getGoals());
        assertNotNull(createdScene.getTransitionPositionBegin());
        assertNotNull(createdScene.getTransitionPositionEnd());
    }

    @Test
    void testSetSceneProperties() {
        Tile[][] tiles = new Tile[20][60];
        Spike[][] spikes = new Spike[20][60];
        Star[][] stars = new Star[20][60];
        Tile[][] goals = new Tile[20][60];

        scene.setTiles(tiles);
        scene.setSpikes(spikes);
        scene.setStars(stars);
        scene.setGoals(goals);
        scene.setTransitionPositionBegin(new Vector(10, 10));
        scene.setTransitionPositionEnd(new Vector(20, 20));
        scene.setStartingPosition(new Vector(5, 5));

        assertEquals(tiles, scene.getTiles());
        assertEquals(spikes, scene.getSpikes());
        assertEquals(stars, scene.getStars());
        assertEquals(goals, scene.getGoals());
        assertEquals(new Vector(10, 10), scene.getTransitionPositionBegin());
        assertEquals(new Vector(20, 20), scene.getTransitionPositionEnd());
        assertEquals(new Vector(5, 5), scene.getStartingPosition());
    }

    @Test
    void testInvalidSceneCode() {
        Exception exception = assertThrows(IOException.class, () -> new SceneBuilder(999));

        String expectedMessage = "Level file not found!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testSceneDimensions() {
        Scene createdScene = sceneBuilder.createScene(player);
        assertEquals(20, createdScene.getWidth());
        assertEquals(16, createdScene.getHeight());
    }

    @Test
    void testEmptyLists() {
        scene.setSnow(Collections.emptyList());
        scene.setDeathParticles(Collections.emptyList());

        assertTrue(scene.getSnow().isEmpty());
        assertTrue(scene.getDeathParticles().isEmpty());
    }

    @Test
    void testTransitionPositionsNotNull() {
        scene.setTransitionPositionBegin(new Vector(0, 0));
        scene.setTransitionPositionEnd(new Vector(10, 10));
        assertNotNull(scene.getTransitionPositionBegin());
        assertNotNull(scene.getTransitionPositionEnd());
    }

    @Test
    void testValidSceneCode() throws IOException, URISyntaxException {
        SceneBuilder validBuilder = new SceneBuilder(1);
        Scene validScene = validBuilder.createScene(player);
        assertNotNull(validScene);
    }

    @Test
    void testCreateSceneWithCustomProperties() {
        Tile[][] tiles = new Tile[12][21];
        Spike[][] spikes = new Spike[12][21];
        Star[][] stars = new Star[12][21];
        Tile[][] goals = new Tile[12][21];

        scene.setTiles(tiles);
        scene.setSpikes(spikes);
        scene.setStars(stars);
        scene.setGoals(goals);

        Scene createdScene = sceneBuilder.createScene(player);

        Tile[][] createdTiles = createdScene.getTiles();
        assertNotNull(createdTiles);
        assertEquals(tiles.length, createdTiles.length);
        assertEquals(tiles[0].length, createdTiles[0].length);

        Spike[][] createdSpikes = createdScene.getSpikes();
        assertNotNull(createdSpikes);
        assertEquals(spikes.length, createdSpikes.length);
        assertEquals(spikes[0].length, createdSpikes[0].length);

        Star[][] createdStars = createdScene.getStars();
        assertNotNull(createdStars);
        assertEquals(stars.length, createdStars.length);
        assertEquals(stars[0].length, createdStars[0].length);

        Tile[][] createdGoals = createdScene.getGoals();
        assertNotNull(createdGoals);
        assertEquals(goals.length, createdGoals.length);
        assertEquals(goals[0].length, createdGoals[0].length);
    }


    @Test
    void testCreateSceneEmptyTiles() {
        Scene createdScene = sceneBuilder.createScene(player);
        assertNotNull(createdScene.getTiles());
        for (Tile[] row : createdScene.getTiles()) {
            assertNotNull(row);
            for (Tile tile : row) {
                if (tile != null) {
                    assertEquals(Tile.class, tile.getClass()); // Verify tile is of the expected type
                } else {
                    assertNull(tile); // Ensure no unexpected null tiles
                }
            }
        }
    }

    @Test
    void testSceneBuilderHandlesSpikes() {
        Spike[][] spikes = new Spike[20][60];
        scene.setSpikes(spikes);
        assertEquals(spikes, scene.getSpikes());
    }

    @Test
    void testSceneBuilderHandlesGoals() {
        Tile[][] goals = new Tile[20][60];
        scene.setGoals(goals);
        assertEquals(goals, scene.getGoals());
    }

    @Test
    void testSceneBuilderHandlesStars() {
        Star[][] stars = new Star[20][60];
        scene.setStars(stars);
        assertEquals(stars, scene.getStars());
    }

    @Test
    void testSceneBuilderHandlesPlayer() {
        Player newPlayer = new Player(10, 10, scene);
        scene.setPlayer(newPlayer);
        assertEquals(newPlayer, scene.getPlayer());
    }
}
