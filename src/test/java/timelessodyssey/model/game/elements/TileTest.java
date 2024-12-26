package timelessodyssey.model.game.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    private Tile tile;

    @BeforeEach
    void setUp() {
        // Initialize the Tile with position (x=5, y=10) and a character representation '#'
        tile = new Tile(5, 10, '#');
    }

    @Test
    void testGetCharacter() {
        assertEquals('#', tile.getCharacter()); // Check that the character is correctly set
    }

    @Test
    void testPositionInitialization() {
        // Check that the position of the tile is initialized correctly
        assertEquals(5, tile.getPosition().x(), 0.001); // Check x position
        assertEquals(10, tile.getPosition().y(), 0.001); // Check y position
    }

    @Test
    void testTileSizeConstant() {
        assertEquals(8, Tile.SIZE); // Verify that the SIZE constant is correctly defined
    }
}
