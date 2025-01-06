package timelessodyssey.model.game.elements.particles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.Tile;
import timelessodyssey.model.game.scene.Scene;

class SnowTest {

    @Mock
    private Scene scene;

    private Snow snow;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        snow = new Snow(10, 20, 2, 1.5, 0.5);
        when(scene.getWidth()).thenReturn(100);
        when(scene.getHeight()).thenReturn(50);
    }

    @Test
    public void testConstructor() {
        assertEquals(10, snow.getPosition().x());
        assertEquals(20, snow.getPosition().y());
        assertEquals(2, snow.getSize());
        assertEquals(TextColor.ANSI.WHITE_BRIGHT, snow.getColor());
        assertEquals(1.5, snow.getVelocity().x());
        assertEquals(0, snow.getVelocity().y());
    }

    @Test
    public void testMove() {
        Vector newPosition = snow.move(scene);

        double expectedX = (10 + 1.5) % (100 * Tile.SIZE);
        double expectedY = (20 + Math.sin((expectedX + 0.5) / 20)) % (50 * Tile.SIZE);

        assertEquals(expectedX, newPosition.x(), 0.001);
        assertEquals(expectedY, newPosition.y(), 0.001);
    }

    @Test
    public void testMoveWraparound() {
        Snow wrapSnow = new Snow(99 * Tile.SIZE, 49 * Tile.SIZE, 2, 1.5, 0.5);
        Vector newPosition = wrapSnow.move(scene);

        double expectedX = (99 * Tile.SIZE + 1.5) % (100 * Tile.SIZE);
        double expectedY = (49 * Tile.SIZE + Math.sin((expectedX + 0.5) / 20)) % (50 * Tile.SIZE);

        assertEquals(expectedX, newPosition.x(), 0.001);
        assertEquals(expectedY, newPosition.y(), 0.001);
    }


    @Test
    void testFloorMod() {
        try {
            java.lang.reflect.Method floorMod = Snow.class.getDeclaredMethod("floorMod", double.class, int.class);
            floorMod.setAccessible(true);

            assertEquals(3.5, (double) floorMod.invoke(snow, 13.5, 10), 0.001);
            assertEquals(9.5, (double) floorMod.invoke(snow, -0.5, 10), 0.001);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }
}
