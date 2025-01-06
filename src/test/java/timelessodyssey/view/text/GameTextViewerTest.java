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
    public void setUp() throws IOException, URISyntaxException {
        mockGUI = mock(GUI.class);
        gameTextViewer = new GameTextViewer();
    }

    @Test
    public void testDrawKnownChar() {
        char character = 'A';
        double x = 10;
        double y = 20;
        TextColor color = TextColor.ANSI.RED;

        gameTextViewer.draw(character, x, y, color, mockGUI);

        verify(mockGUI, atLeastOnce()).drawPixel(anyDouble(), anyDouble(), eq(color));
        verifyNoMoreInteractions(mockGUI);
    }

    @Test
    public void testDrawUnknownChar() {
        char unknownCharacter = '\uFFFF';
        double x = 10.0;
        double y = 20.0;
        TextColor color = TextColor.ANSI.BLUE;

        gameTextViewer.draw(unknownCharacter, x, y, color, mockGUI);

        verify(mockGUI).drawRectangle(
                eq(x),
                eq(y),
                eq(GameTextViewer.getCharWidth()),
                eq(GameTextViewer.getCharHeight()),
                eq(color)
        );

        verifyNoMoreInteractions(mockGUI);
    }

    @Test
    public void testDrawString() {
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
    public void testDrawEmptyString() {
        String text = "";
        double x = 10;
        double y = 20;
        TextColor color = TextColor.ANSI.GREEN;

        gameTextViewer.draw(text, x, y, color, mockGUI);

        // Ensure no interaction happens for an empty string
        verifyNoInteractions(mockGUI);
    }

    @Test
    public void testDrawInvalidChar() {
        char character = '\u0000'; // Non-mapped character
        double x = 10.0;
        double y = 20.0;
        TextColor color = TextColor.ANSI.YELLOW;

        gameTextViewer.draw(character, x, y, color, mockGUI);

        verify(mockGUI).drawRectangle(
                eq(x),
                eq(y),
                eq(GameTextViewer.getCharWidth()),
                eq(GameTextViewer.getCharHeight()),
                eq(color)
        );
        verifyNoMoreInteractions(mockGUI);
    }

    @Test
    public void testSpacingIncrements() {
        String text = "AB";
        double x = 10.0;
        double y = 20.0;
        TextColor color = TextColor.ANSI.YELLOW;

        gameTextViewer.draw(text, x, y, color, mockGUI);

        double expectedXIncrement = GameTextViewer.getCharWidth() + GameTextViewer.getSpacing();
        verify(mockGUI, atLeastOnce()).drawPixel(anyDouble(), anyDouble(), eq(color));
        assertEquals(expectedXIncrement, 4.0, "Spacing and character width must match increments.");
    }

    @Test
    public void testNegativeCoordinates() {
        char character = 'C';
        double x = -5.0;
        double y = -10.0;
        TextColor color = TextColor.ANSI.GREEN;

        assertDoesNotThrow(() -> gameTextViewer.draw(character, x, y, color, mockGUI));
    }

    @Test
    public void testNullGUI() {
        char character = 'Z';
        double x = 0.0;
        double y = 0.0;
        TextColor color = TextColor.ANSI.CYAN;

        assertThrows(NullPointerException.class, () -> gameTextViewer.draw(character, x, y, color, null));
    }

    @Test
    public void testGetCharHeight() {
        int charHeight = GameTextViewer.getCharHeight();
        assertEquals(5, charHeight, "Character height should be 5.");
    }

    @Test
    public void testGetCharWidth() {
        int charWidth = GameTextViewer.getCharWidth();
        assertEquals(3, charWidth, "Character width should be 3.");
    }

    @Test
    public void testGetSpacing() {
        int spacing = GameTextViewer.getSpacing();
        assertEquals(1, spacing, "Spacing should be 1.");
    }

    @Test
    public void testParseCharMap() throws IOException, URISyntaxException {
        BufferedImage fontImage = gameTextViewer.getClass().getClassLoader().getResourceAsStream("gamefont/font.png") != null
                ? mock(BufferedImage.class) : null;
        assertNotNull(fontImage, "Font image should be loaded.");
    }

    @Test
    public void testDrawKnownCharIntegration() throws IOException, URISyntaxException {
        char character = 'A';
        double x = 0;
        double y = 0;
        TextColor color = TextColor.ANSI.RED;

        assertDoesNotThrow(() -> gameTextViewer.draw(character, x, y, color, mockGUI));
    }

    @Test
    public void testDrawUnknownCharIntegration() {
        char character = '?';
        double x = 0;
        double y = 0;
        TextColor color = TextColor.ANSI.BLUE;

        assertDoesNotThrow(() -> gameTextViewer.draw(character, x, y, color, mockGUI));
    }
}
