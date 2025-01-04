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
        mockGUI = mock(GUI.class);
        gameTextViewer = new GameTextViewer();
    }

    @Test
    void testDrawKnownChar() {
        char character = 'A';
        double x = 10;
        double y = 20;
        TextColor color = TextColor.ANSI.RED;
        gameTextViewer.draw(character, x, y, color, mockGUI);
        verify(mockGUI, atLeastOnce()).drawPixel(anyDouble(), anyDouble(), eq(color));
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
    }
}
