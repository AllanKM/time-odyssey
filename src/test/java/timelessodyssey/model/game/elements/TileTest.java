package timelessodyssey.model.game.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    private Tile tile;

    @BeforeEach
    void setUp() {
        tile = new Tile(5, 10, '#');
    }

    @Test
    void testGetCharacter() {
        assertEquals('#', tile.getCharacter());
    }

    @Test
    void testPositionInitialization() {
        assertEquals(5, tile.getPosition().x(), 0.001);
        assertEquals(10, tile.getPosition().y(), 0.001);
    }

    @Test
    void testTileSizeConstant() {
        assertEquals(8, Tile.SIZE);
    }
}
