package timelessodyssey.view.elements;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.particles.Particle;

import static org.mockito.Mockito.*;

class ParticleViewerTest {

    private GUI mockGUI;
    private Particle mockParticle;
    private ParticleViewer particleViewer;

    @BeforeEach
    void setUp() {
        mockGUI = mock(GUI.class);
        mockParticle = mock(Particle.class);
        particleViewer = new ParticleViewer();
    }

    @Test
    void testDraw() {
        when(mockParticle.getPosition()).thenReturn(new Vector(10, 20));
        when(mockParticle.getSize()).thenReturn(5);
        when(mockParticle.getColor()).thenReturn(TextColor.ANSI.RED);
        particleViewer.draw(mockParticle, mockGUI, 100L);
        verify(mockGUI).drawRectangle(10, 20, 5, 5, TextColor.ANSI.RED);
    }
}