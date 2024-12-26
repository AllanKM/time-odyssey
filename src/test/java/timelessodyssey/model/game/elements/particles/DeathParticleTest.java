package timelessodyssey.model.game.elements.particles;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.jupiter.api.Assertions.*;

class DeathParticleTest {

    private DeathParticle deathParticle;
    @Mock
    private Scene scene;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deathParticle = new DeathParticle(10, 20, 5, Math.PI / 4);
    }

    @Test
    void testConstructor() {
        assertEquals(10, deathParticle.getPosition().x());
        assertEquals(20, deathParticle.getPosition().y());
        assertEquals(1, deathParticle.getSize());
        assertEquals(TextColor.ANSI.RED_BRIGHT, deathParticle.getColor());

        double expectedVelocityX = 5 * Math.cos(Math.PI / 4);
        double expectedVelocityY = 5 * Math.sin(Math.PI / 4);
        assertEquals(expectedVelocityX, deathParticle.getVelocity().x(), 0.001);
        assertEquals(expectedVelocityY, deathParticle.getVelocity().y(), 0.001);
    }

    @Test
    void testMove() {
        Vector newPosition = deathParticle.move(scene);

        double expectedX = 10 + 5 * Math.cos(Math.PI / 4);
        double expectedY = 20 + 5 * Math.sin(Math.PI / 4);

        assertEquals(expectedX, newPosition.x(), 0.001);
        assertEquals(expectedY, newPosition.y(), 0.001);
    }
}
