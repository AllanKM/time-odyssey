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
        // Mock the Scene and Game objects
        sceneMock = mock(Scene.class);
        gameMock = mock(Game.class);

        // Mock Particle lists
        snowParticles = new ArrayList<>();
        deathParticles = new ArrayList<>();

        // Populate particle lists with mocked particles
        for (int i = 0; i < 5; i++) {
            snowParticles.add(mock(Particle.class));
            deathParticles.add(mock(Particle.class));
        }

        // Configure the scene to return the mocked particle lists
        when(sceneMock.getSnow()).thenReturn(snowParticles);
        when(sceneMock.getDeathParticles()).thenReturn(deathParticles);

        // Instantiate the ParticleController with the mocked scene
        particleController = new ParticleController(sceneMock);
    }

    @Test
    void testStep() {
        // Arrange
        GUI.Action action = GUI.Action.NONE;
        long frameCount = 0;

        // Act
        particleController.step(gameMock, action, frameCount);

        // Assert
        // Verify that the move and setPosition methods are called for each snow particle
        for (Particle particle : snowParticles) {
            verify(particle, times(1)).move(sceneMock);
            verify(particle, times(1)).setPosition(any());
        }

        // Verify that the move and setPosition methods are called for each death particle
        for (Particle particle : deathParticles) {
            verify(particle, times(1)).move(sceneMock);
            verify(particle, times(1)).setPosition(any());
        }
    }
}
