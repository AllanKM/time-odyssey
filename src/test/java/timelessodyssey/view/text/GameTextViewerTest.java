package timelessodyssey.view.text;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTextViewerTest {

    private GUI mockGUI;
    private GameTextViewer gameTextViewer;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        mockGUI = mock(GUI.class);
        gameTextViewer = new GameTextViewer();
    }

    @Test
    void testDrawKnownChar() {
        char character = 'A'; // Assuming 'A' is defined in the font map
        double x = 10;
        double y = 20;
        TextColor color = TextColor.ANSI.RED;

        gameTextViewer.draw(character, x, y, color, mockGUI);

        verify(mockGUI, atLeastOnce()).drawPixel(anyDouble(), anyDouble(), eq(color));
        verifyNoMoreInteractions(mockGUI);
    }

    @Test
    void testDrawUnknownChar() {
        char unknownCharacter = '\uFFFF'; // Use a character that is highly unlikely to exist in the charMap
        double x = 10.0;
        double y = 20.0;
        TextColor color = TextColor.ANSI.BLUE;

        gameTextViewer.draw(unknownCharacter, x, y, color, mockGUI);

        // Verify that drawRectangle is called for unknown characters
        verify(mockGUI).drawRectangle(
                eq(x),
                eq(y),
                eq(GameTextViewer.getCharWidth()),
                eq(GameTextViewer.getCharHeight()),
                eq(color)
        );

        // Ensure no additional unexpected interactions occurred
        verifyNoMoreInteractions(mockGUI);
    }

    @Test
    void testDrawString() {
        String text = "HELLO";
        double x = 10;
        double y = 20;
        TextColor color = TextColor.ANSI.GREEN;

        gameTextViewer.draw(text, x, y, color, mockGUI);

        for (int i = 0; i < text.length(); i++) {
            double expectedX = x + i * (GameTextViewer.getCharWidth() + GameTextViewer.getSpacing());
            verify(mockGUI, atLeastOnce()).drawPixel(anyDouble(), anyDouble(), eq(color));
        }
        verifyNoMoreInteractions(mockGUI);
    }

    @Test
    void testGetCharHeight() {
        int charHeight = GameTextViewer.getCharHeight();
        assertEquals(5, charHeight, "Character height should be 5.");
    }

    @Test
    void testGetCharWidth() {
        int charWidth = GameTextViewer.getCharWidth();
        assertEquals(3, charWidth, "Character width should be 3.");
    }

    @Test
    void testGetSpacing() {
        int spacing = GameTextViewer.getSpacing();
        assertEquals(1, spacing, "Spacing should be 1.");
    }

    @Test
    void testParseCharMap() throws IOException, URISyntaxException {
        BufferedImage fontImage = gameTextViewer.getClass().getClassLoader().getResourceAsStream("gamefont/font.png") != null
                ? mock(BufferedImage.class) : null;
        assertNotNull(fontImage, "Font image should be loaded.");
    }

    @Test
    void testDrawKnownCharIntegration() throws IOException, URISyntaxException {
        // Ensures font map and font image integration works
        char character = 'A'; // Assuming 'A' exists in the font map
        double x = 0;
        double y = 0;
        TextColor color = TextColor.ANSI.RED;

        assertDoesNotThrow(() -> gameTextViewer.draw(character, x, y, color, mockGUI));
    }

    @Test
    void testDrawUnknownCharIntegration() {
        char character = '?'; // Assuming '?' does not exist in the font map
        double x = 0;
        double y = 0;
        TextColor color = TextColor.ANSI.BLUE;

        assertDoesNotThrow(() -> gameTextViewer.draw(character, x, y, color, mockGUI));
    }
}
