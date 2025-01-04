package timelessodyssey.control.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.game.elements.particles.Particle;
import timelessodyssey.model.game.scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ParticleControllerTest {

    private ParticleController particleController;
    private Scene sceneMock;
    private Game gameMock;
    private List<Particle> snowParticles;
    private List<Particle> deathParticles;

    @BeforeEach
    void setUp() {
        sceneMock = mock(Scene.class);
        gameMock = mock(Game.class);
        snowParticles = new ArrayList<>();
        deathParticles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            snowParticles.add(mock(Particle.class));
            deathParticles.add(mock(Particle.class));
        }

        when(sceneMock.getSnow()).thenReturn(snowParticles);
        when(sceneMock.getDeathParticles()).thenReturn(deathParticles);

        particleController = new ParticleController(sceneMock);
    }

    @Test
    void testStep() {
        GUI.Action action = GUI.Action.NONE;
        long frameCount = 0;
        particleController.step(gameMock, action, frameCount);

        for (Particle particle : snowParticles) {
            verify(particle, times(1)).move(sceneMock);
            verify(particle, times(1)).setPosition(any());
        }

        for (Particle particle : deathParticles) {
            verify(particle, times(1)).move(sceneMock);
            verify(particle, times(1)).setPosition(any());
        }
    }
}
