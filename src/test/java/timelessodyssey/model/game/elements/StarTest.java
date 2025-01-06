package timelessodyssey.model.game.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StarTest {

    private Star star;

    @BeforeEach
    public void setUp() {
        star = new Star(5, 10);
    }

    @Test
    public void testPositionInitialization() {
        assertEquals(5, star.getPosition().x(), 0.001);
        assertEquals(10, star.getPosition().y(), 0.001);
    }
}
