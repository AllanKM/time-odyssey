package timelessodyssey.view.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.Star;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

import java.io.IOException;

import static org.mockito.Mockito.*;

class StarViewerTest {

    private GUI mockGUI;
    private SpriteLoader mockSpriteLoader;
    private Star mockStar;
    private Sprite mockSprite;
    private StarViewer starViewer;

    @BeforeEach
    void setUp() throws IOException {
        mockGUI = mock(GUI.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockStar = mock(Star.class);
        mockSprite = mock(Sprite.class);
        when(mockSpriteLoader.get("sprites/star.png")).thenReturn(mockSprite);
        starViewer = new StarViewer(mockSpriteLoader);
    }

    @Test
    void testDraw() {
        when(mockStar.getPosition()).thenReturn(new Vector(10, 20));
        starViewer.draw(mockStar, mockGUI, 100L);
        verify(mockSprite).draw(mockGUI, 10, 20);
    }
}
