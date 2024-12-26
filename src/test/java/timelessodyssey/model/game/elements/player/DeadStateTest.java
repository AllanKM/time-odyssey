package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.particles.DeathParticle;
import timelessodyssey.model.game.elements.particles.Particle;
import timelessodyssey.model.game.scene.Scene;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeadStateTest {

    @Mock
    private Player player;

    @Mock
    private Scene scene;

    private DeadState deadState;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(player.getScene()).thenReturn(scene);
        when(player.getPosition()).thenReturn(new Vector(10, 20));
        deadState = new DeadState(player, 5); // Set initial duration to 5
    }

    @Test
    void testGetDuration() {
        assertEquals(5, deadState.getDuration());
    }

    @Test
    void testCreateDeathParticles() throws Exception {
        // Use reflection to access the private method createDeathParticles
        Method createDeathParticlesMethod = DeadState.class.getDeclaredMethod("createDeathParticles");
        createDeathParticlesMethod.setAccessible(true);

        List<Particle> particles = (List<Particle>) createDeathParticlesMethod.invoke(deadState);

        assertEquals(21, particles.size()); // 20 particles + 1 for loop condition
        for (Particle particle : particles) {
            assertTrue(particle instanceof DeathParticle);
            assertEquals(10, particle.getPosition().x(), 0.001);
            assertEquals(20, particle.getPosition().y(), 0.001);
        }
    }

    @Test
    void testUpdateVelocity() {
        Vector initialVelocity = new Vector(5, 0);

        Vector result = deadState.updateVelocity(initialVelocity);

        assertEquals(0, result.x(), 0.001);
        assertEquals(0, result.y(), 0.001);
        assertEquals(4, deadState.getDuration()); // Duration should decrease by 1
    }

    @Test
    void testGetNextState_DurationNotExpired() {
        PlayerState nextState = deadState.getNextState();

        assertSame(deadState, nextState); // Should return itself
    }

    @Test
    void testGetNextState_DurationExpired() {
        deadState = new DeadState(player, 0); // Set duration to 0

        PlayerState nextState = deadState.getNextState();

        assertNull(nextState); // Should return null when duration is expired
    }
}
