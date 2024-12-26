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
    void setUp() throws IOException {
        // Mock dependencies
        mockGUI = mock(GUI.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockSprite = mock(Sprite.class);

        // Mock SpriteLoader behavior
        when(mockSpriteLoader.get("menu/logo.png")).thenReturn(mockSprite);

        // Create LogoViewer instance
        logoViewer = new LogoViewer(mockSpriteLoader);
    }

    @Test
    void testDraw() {
        // Arrange
        int x = 10;
        int y = 20;

        // Act
        logoViewer.draw(mockGUI, x, y);

        // Assert
        verify(mockSprite).draw(mockGUI, x, y);
    }
}