package timelessodyssey.model.game.elements.particles;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.Test;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParticleTest {

    private static class TestParticle extends Particle {
        public TestParticle(double x, double y, int size, TextColor color, Vector velocity) {
            super(x, y, size, color, velocity);
        }

        @Override
        public Vector move(Scene scene) {
            return new Vector(getPosition().x() + getVelocity().x(), getPosition().y() + getVelocity().y());
        }
    }

    @Test
    void testConstructorAndGetters() {
        Vector velocity = new Vector(2, 3);
        TestParticle particle = new TestParticle(10, 20, 5, TextColor.ANSI.BLUE, velocity);

        assertEquals(10, particle.getPosition().x());
        assertEquals(20, particle.getPosition().y());
        assertEquals(5, particle.getSize());
        assertEquals(TextColor.ANSI.BLUE, particle.getColor());
        assertEquals(velocity, particle.getVelocity());
    }

    @Test
    void testMove() {
        Vector velocity = new Vector(2, 3);
        TestParticle particle = new TestParticle(10, 20, 5, TextColor.ANSI.BLUE, velocity);
        Scene mockScene = mock(Scene.class);

        Vector newPosition = particle.move(mockScene);

        assertEquals(12, newPosition.x());
        assertEquals(23, newPosition.y());
    }
}
