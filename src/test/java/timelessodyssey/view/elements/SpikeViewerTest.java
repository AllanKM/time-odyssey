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
    public void setUp() throws IOException {
        mockGUI = mock(GUI.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockSpike = mock(Spike.class);
        mockSprite = mock(Sprite.class);

        when(mockSpriteLoader.get("sprites/spikes/futuristic/Bottom_Spike.png")).thenReturn(mockSprite);
        when(mockSpriteLoader.get("sprites/spikes/cave/Bottom_Spike1.png")).thenReturn(mockSprite);
        when(mockSpriteLoader.get("sprites/spikes/cave/Bottom_Spike2.png")).thenReturn(mockSprite);

        spikeViewer = new SpikeViewer(mockSpriteLoader);
    }

    @Test
    public void testDrawSpikeFuturistic() {
        when(mockSpike.getCharacter()).thenReturn('^');
        when(mockSpike.getPosition()).thenReturn(new Vector(10, 20));
        spikeViewer.draw(mockSpike, mockGUI, 100L);
        verify(mockSprite).draw(mockGUI, 10, 20);
    }

    @Test
    public void testDrawSpikeCaveStyle1() {
        when(mockSpike.getCharacter()).thenReturn('+');
        when(mockSpike.getPosition()).thenReturn(new Vector(15, 25));
        spikeViewer.draw(mockSpike, mockGUI, 101L);
        verify(mockSprite).draw(mockGUI, 15, 25);
    }

    @Test
    public void testDrawSpikeCaveStyle2() {
        when(mockSpike.getCharacter()).thenReturn('-');
        when(mockSpike.getPosition()).thenReturn(new Vector(20, 30));
        spikeViewer.draw(mockSpike, mockGUI, 102L);
        verify(mockSprite).draw(mockGUI, 20, 30);
    }
}
