package timelessodyssey.view.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.Spike;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

import java.io.IOException;

import static org.mockito.Mockito.*;

class SpikeViewerTest {

    private GUI mockGUI;
    private SpriteLoader mockSpriteLoader;
    private Spike mockSpike;
    private SpikeViewer spikeViewer;
    private Sprite mockSprite;

    @BeforeEach
    void setUp() throws IOException {
        // Mock dependencies
        mockGUI = mock(GUI.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockSpike = mock(Spike.class);
        mockSprite = mock(Sprite.class);

        // Set up mock behavior for SpriteLoader
        when(mockSpriteLoader.get("sprites/spikes/futuristic/Bottom_Spike.png")).thenReturn(mockSprite);
        when(mockSpriteLoader.get("sprites/spikes/cave/Bottom_Spike1.png")).thenReturn(mockSprite);
        when(mockSpriteLoader.get("sprites/spikes/cave/Bottom_Spike2.png")).thenReturn(mockSprite);

        // Create instance of SpikeViewer
        spikeViewer = new SpikeViewer(mockSpriteLoader);
    }

    @Test
    void testDrawSpikeFuturistic() {
        // Arrange
        when(mockSpike.getCharacter()).thenReturn('^');
        when(mockSpike.getPosition()).thenReturn(new Vector(10, 20));

        // Act
        spikeViewer.draw(mockSpike, mockGUI, 100L);

        // Assert
        verify(mockSprite).draw(mockGUI, 10, 20);
    }

    @Test
    void testDrawSpikeCaveStyle1() {
        // Arrange
        when(mockSpike.getCharacter()).thenReturn('+');
        when(mockSpike.getPosition()).thenReturn(new Vector(15, 25));

        // Act
        spikeViewer.draw(mockSpike, mockGUI, 101L);

        // Assert
        verify(mockSprite).draw(mockGUI, 15, 25);
    }

    @Test
    void testDrawSpikeCaveStyle2() {
        // Arrange
        when(mockSpike.getCharacter()).thenReturn('-');
        when(mockSpike.getPosition()).thenReturn(new Vector(20, 30));

        // Act
        spikeViewer.draw(mockSpike, mockGUI, 102L);

        // Assert
        verify(mockSprite).draw(mockGUI, 20, 30);
    }
}
