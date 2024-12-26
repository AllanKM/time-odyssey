package timelessodyssey.model.game.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StarTest {

    private Star star;

    @BeforeEach
    void setUp() {
        // Initialize the Star with position (x=5, y=10)
        star = new Star(5, 10);
    }

    @Test
    void testPositionInitialization() {
        // Check that the position of the star is initialized correctly
        assertEquals(5, star.getPosition().x(), 0.001); // Check x position
        assertEquals(10, star.getPosition().y(), 0.001); // Check y position
    }
}
