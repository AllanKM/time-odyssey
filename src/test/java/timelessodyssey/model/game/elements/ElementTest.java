package timelessodyssey.model.game.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.model.Vector;

import static org.junit.jupiter.api.Assertions.*;

class ElementTest {

    private Element element;

    @BeforeEach
    void setUp() {
        // Initialize the Element with a starting position (x=10, y=20)
        element = new Element(10, 20);
    }

    @Test
    void testGetPosition() {
        Vector position = element.getPosition();

        assertEquals(10, position.x(), 0.001); // Check x position
        assertEquals(20, position.y(), 0.001); // Check y position
    }

    @Test
    void testSetPosition() {
        Vector newPosition = new Vector(30, 40);
        element.setPosition(newPosition);

        Vector position = element.getPosition();

        assertEquals(30, position.x(), 0.001); // Check updated x position
        assertEquals(40, position.y(), 0.001); // Check updated y position
    }
}
