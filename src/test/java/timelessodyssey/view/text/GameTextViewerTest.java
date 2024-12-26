package timelessodyssey.view.text;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

class GameTextViewerTest {

    private GUI mockGUI;
    private GameTextViewer gameTextViewer;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        // Mock GUI
        mockGUI = mock(GUI.class);

        // Instantiate GameTextViewer
        gameTextViewer = new GameTextViewer();
    }

    @Test
    void testDrawKnownChar() {
        // Arrange
        char character = 'A';
        double x = 10;
        double y = 20;
        TextColor color = TextColor.ANSI.RED;

        // Act
        gameTextViewer.draw(character, x, y, color, mockGUI);

        // Assert
        verify(mockGUI, atLeastOnce()).drawPixel(anyDouble(), anyDouble(), eq(color));
    }

//    @Test
//    void testDrawUnknownChar() {
//        // Arrange
//        char character = '?'; // Assuming '?' is not in the font map
//        double x = 10;
//        double y = 20;
//        TextColor color = TextColor.ANSI.RED;
//
//        // Act
//        gameTextViewer.draw(character, x, y, color, mockGUI);
//
//        // Assert
//        verify(mockGUI).drawRectangle(eq(x), eq(y), eq(GameTextViewer.getCharWidth()), eq(GameTextViewer.getCharHeight()), eq(color));
//    }

    @Test
    void testDrawString() {
        // Arrange
        String text = "HELLO";
        double x = 10;
        double y = 20;
        TextColor color = TextColor.ANSI.GREEN;

        // Act
        gameTextViewer.draw(text, x, y, color, mockGUI);

        // Assert
        for (int i = 0; i < text.length(); i++) {
            double expectedX = x + i * (GameTextViewer.getCharWidth() + GameTextViewer.getSpacing());
            verify(mockGUI, atLeastOnce()).drawPixel(anyDouble(), anyDouble(), eq(color));
        }
    }
}
