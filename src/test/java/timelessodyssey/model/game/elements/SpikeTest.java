package timelessodyssey.model.game.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpikeTest {

    private Spike spike;

    @BeforeEach
    void setUp() {
        spike = new Spike(5, 10, '*');
    }

    @Test
    void testGetCharacter() {
        assertEquals('*', spike.getCharacter());
    }

    @Test
    void testPositionInitialization() {
        assertEquals(5, spike.getPosition().x(), 0.001);
        assertEquals(10, spike.getPosition().y(), 0.001);
    }

    @Test
    void testSpikeHeightConstant() {
        assertEquals(4, Spike.SPIKE_HEIGHT);

    }
}
