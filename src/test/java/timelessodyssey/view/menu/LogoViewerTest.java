package timelessodyssey.view.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

import java.io.IOException;

import static org.mockito.Mockito.*;

class LogoViewerTest {

    private GUI mockGUI;
    private SpriteLoader mockSpriteLoader;
    private Sprite mockSprite;
    private LogoViewer logoViewer;

    @BeforeEach
    public void setUp() throws IOException {
        mockGUI = mock(GUI.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockSprite = mock(Sprite.class);
        when(mockSpriteLoader.get("menu/logo.png")).thenReturn(mockSprite);
        logoViewer = new LogoViewer(mockSpriteLoader);
    }

    @Test
    public void testDraw() {
        int x = 10;
        int y = 20;
        logoViewer.draw(mockGUI, x, y);
        verify(mockSprite).draw(mockGUI, x, y);
    }
}