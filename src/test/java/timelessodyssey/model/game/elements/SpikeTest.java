package timelessodyssey.model.game.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpikeTest {

    private Spike spike;

    @BeforeEach
    void setUp() {
        // Initialize the Spike with position (x=5, y=10) and a character representation '*'
        spike = new Spike(5, 10, '*');
    }

    @Test
    void testGetCharacter() {
        assertEquals('*', spike.getCharacter()); // Check that the character is correctly set
    }

    @Test
    void testPositionInitialization() {
        // Check that the position of the spike is initialized correctly
        assertEquals(5, spike.getPosition().x(), 0.001); // Check x position
        assertEquals(10, spike.getPosition().y(), 0.001); // Check y position
    }

    @Test
    void testSpikeHeightConstant() {
        assertEquals(4, Spike.SPIKE_HEIGHT); // Verify that the SPIKE_HEIGHT constant is correctly defined
    }
}
