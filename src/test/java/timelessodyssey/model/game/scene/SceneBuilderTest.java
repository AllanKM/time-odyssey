package timelessodyssey.model.game.scene;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.Spike;
import timelessodyssey.model.game.elements.Star;
import timelessodyssey.model.game.elements.Tile;
import timelessodyssey.model.game.elements.particles.Particle;
import timelessodyssey.model.game.elements.particles.Snow;
import timelessodyssey.model.game.elements.player.Player;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SceneBuilderTest {

    private SceneBuilder sceneBuilder;
    private Scene scene;
    private Player player;

    @BeforeEach
    public void setUp() throws IOException, URISyntaxException {
        sceneBuilder = new SceneBuilder(1);
        scene = new Scene(60, 20, 1);
        player = new Player(0, 0, scene);
    }

    @Test
    public void testCreateScene() {
        Scene createdScene = sceneBuilder.createScene(player);

        assertNotNull(createdScene, "Scene should not be null");
        assertEquals(player, createdScene.getPlayer(), "Player should match");
        assertEquals(player.getPosition(), createdScene.getStartingPosition(), "Starting position should match");
        assertNotNull(createdScene.getTiles(), "Tiles should not be null");
        assertNotNull(createdScene.getSpikes(), "Spikes should not be null");
        assertNotNull(createdScene.getStars(), "Stars should not be null");
        assertNotNull(createdScene.getGoals(), "Goals should not be null");
        assertNotNull(createdScene.getTransitionPositionBegin(), "Transition start position should not be null");
        assertNotNull(createdScene.getTransitionPositionEnd(), "Transition end position should not be null");
        assertNotNull(createdScene.getSnow(), "Snow should not be null");
        assertNotNull(createdScene.getDeathParticles(), "Death particles should not be null");

        assertEquals(20, createdScene.getWidth(), "Default width should match");
        assertEquals(16, createdScene.getHeight(), "Default height should match");
    }

    @Test
    public void testInvalidSceneCode() {
        Exception exception = assertThrows(IOException.class, () -> new SceneBuilder(999));
        assertTrue(exception.getMessage().contains("Level file not found!"));
    }

    @Test
    public void testSetSceneProperties() {
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
    public void testEmptyLists() {
        scene.setSnow(Collections.emptyList());
        scene.setDeathParticles(Collections.emptyList());

        assertTrue(scene.getSnow().isEmpty(), "Snow list should be empty");
        assertTrue(scene.getDeathParticles().isEmpty(), "Death particles list should be empty");
    }

    @Test
    public void testTransitionPositionsNotNull() {
        scene.setTransitionPositionBegin(new Vector(0, 0));
        scene.setTransitionPositionEnd(new Vector(10, 10));

        assertNotNull(scene.getTransitionPositionBegin(), "Transition position begin should not be null");
        assertNotNull(scene.getTransitionPositionEnd(), "Transition position end should not be null");
    }

    @Test
    public void testSceneBuilderHandlesPlayer() {
        Player newPlayer = new Player(10, 10, scene);
        scene.setPlayer(newPlayer);
        assertEquals(newPlayer, scene.getPlayer(), "Player should match");
    }

    @Test
    public void testSceneBuilderHandlesSpikes() {
        Spike[][] spikes = new Spike[20][60];
        scene.setSpikes(spikes);
        assertEquals(spikes, scene.getSpikes(), "Spikes should match");
    }

    @Test
    public void testSceneBuilderHandlesStars() {
        Star[][] stars = new Star[20][60];
        scene.setStars(stars);
        assertEquals(stars, scene.getStars(), "Stars should match");
    }

    @Test
    public void testSceneBuilderHandlesGoals() {
        Tile[][] goals = new Tile[20][60];
        scene.setGoals(goals);
        assertEquals(goals, scene.getGoals(), "Goals should match");
    }

    @Test
    public void testSceneBuilderHandlesSnow() {
        Particle mockParticle = mock(Snow.class);
        scene.setSnow(new ArrayList<>(List.of(mockParticle)));
        assertFalse(scene.getSnow().isEmpty(), "Snow list should not be empty");
        assertEquals(mockParticle, scene.getSnow().get(0), "First snow particle should match");
    }

    @Test
    public void testSceneBuilderHandlesCustomProperties() {
        Tile[][] tiles = new Tile[12][21];
        Spike[][] spikes = new Spike[12][21];
        Star[][] stars = new Star[12][21];
        Tile[][] goals = new Tile[12][21];

        scene.setTiles(tiles);
        scene.setSpikes(spikes);
        scene.setStars(stars);
        scene.setGoals(goals);

        Scene createdScene = sceneBuilder.createScene(player);

        assertEquals(tiles.length, createdScene.getTiles().length, "Tile rows should match");
        assertEquals(tiles[0].length, createdScene.getTiles()[0].length, "Tile columns should match");

        assertEquals(spikes.length, createdScene.getSpikes().length, "Spike rows should match");
        assertEquals(spikes[0].length, createdScene.getSpikes()[0].length, "Spike columns should match");

        assertEquals(stars.length, createdScene.getStars().length, "Star rows should match");
        assertEquals(stars[0].length, createdScene.getStars()[0].length, "Star columns should match");

        assertEquals(goals.length, createdScene.getGoals().length, "Goal rows should match");
        assertEquals(goals[0].length, createdScene.getGoals()[0].length, "Goal columns should match");
    }

    @Test
    public void testCreateSceneEmptyTiles() {
        Scene createdScene = sceneBuilder.createScene(player);
        assertNotNull(createdScene.getTiles(), "Tiles should not be null");

        for (Tile[] row : createdScene.getTiles()) {
            assertNotNull(row, "Tile row should not be null");
            for (Tile tile : row) {
                if (tile != null) {
                    assertEquals(Tile.class, tile.getClass(), "Tile should be of correct type");
                }
            }
        }
    }

    @Test
    public void testSceneBuilderHandlesDefaultValues() {
        assertNotNull(scene.getSnow(), "Snow should not be null by default");
        assertNotNull(scene.getDeathParticles(), "Death particles should not be null by default");
    }
}
