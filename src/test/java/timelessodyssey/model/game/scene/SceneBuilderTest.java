package timelessodyssey.model.game.scene;

import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.player.Player;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SceneBuilderTest {
    private SceneBuilder sceneBuilder;
    private Scene scene;
    private Player player;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        // Initialize SceneBuilder with a valid scene code
        sceneBuilder = new SceneBuilder(1); // Assuming level file "scene1.lvl" exists
        scene = new Scene(640, 480, 1); // Sample dimensions and code
        player = new Player(0, 0, scene); // Initialize player with starting position (0, 0)
    }

    @Test
    void testCreateScene() {
        Scene createdScene = sceneBuilder.createScene(player);

        // Verify that the scene is not null
        assertNotNull(createdScene);

        // Verify that the player is correctly set in the scene
        assertEquals(player, createdScene.getPlayer());
        assertEquals(player.getPosition(), createdScene.getStartingPosition());

        // Verify that walls are created correctly
        assertNotNull(createdScene.getTiles());

        // Check if actual height matches expected height
        int expectedHeight = 12; // Adjust based on your level file content
        assertEquals(expectedHeight, createdScene.getTiles().length);

        // Verify that spikes are created correctly
        assertNotNull(createdScene.getSpikes());

        // Verify that stars are created correctly
        assertNotNull(createdScene.getStars());

        // Verify that goals are created correctly
        assertNotNull(createdScene.getGoals());

        // Verify transition positions are set correctly
        Vector transitionBegin = createdScene.getTransitionPositionBegin();
        Vector transitionEnd = createdScene.getTransitionPositionEnd();

        assertNotNull(transitionBegin);
        assertNotNull(transitionEnd);
    }


//    @Test
//    void testCreatePlayer() {
//        Player resultPlayer = sceneBuilder.createPlayer(scene, player);
//
//        // Verify that the player is placed correctly in the scene
//        assertEquals(new Vector(8 * 5, 8 * 5), resultPlayer.getPosition()); // Assuming 'P' is at (5,5) in the level file.
//
//        // Additional checks on player attributes can be added here if needed.
//    }

    @Test
    void testInvalidSceneCode() {
        Exception exception = assertThrows(IOException.class, () -> {
            new SceneBuilder(999); // Assuming this code does not exist.
        });

        String expectedMessage = "Level file not found!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}