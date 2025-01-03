package timelessodyssey.view.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.Tile;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

import java.io.IOException;

import static org.mockito.Mockito.*;

class TileViewerTest {

    private GUI mockGUI;
    private SpriteLoader mockSpriteLoader;
    private Tile mockTile;
    private TileViewer tileViewer;
    private Sprite mockSprite;

    @BeforeEach
    void setUp() throws IOException {
        // Mock dependencies
        mockGUI = mock(GUI.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockTile = mock(Tile.class);
        mockSprite = mock(Sprite.class);

        // Mock behavior for SpriteLoader
        when(mockSpriteLoader.get("sprites/tiles/futuristic/Gray.png")).thenReturn(mockSprite);
        when(mockSpriteLoader.get("sprites/tiles/cave/ground/Top.png")).thenReturn(mockSprite);

        // Create TileViewer instance
        tileViewer = new TileViewer(mockSpriteLoader);
    }

    @Test
    void testDrawFuturisticGrayTile() {
        // Arrange
        when(mockTile.getCharacter()).thenReturn('G');
        when(mockTile.getPosition()).thenReturn(new Vector(10, 20));

        // Act
        tileViewer.draw(mockTile, mockGUI, 100L);

        // Assert
        verify(mockSprite).draw(mockGUI, 10, 20);
    }

    @Test
    void testDrawCaveTopTile() {
        // Arrange
        when(mockTile.getCharacter()).thenReturn('t');
        when(mockTile.getPosition()).thenReturn(new Vector(15, 25));

        // Act
        tileViewer.draw(mockTile, mockGUI, 101L);

        // Assert
        verify(mockSprite).draw(mockGUI, 15, 25);
    }
}
