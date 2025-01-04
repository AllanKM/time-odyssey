package timelessodyssey.view.text;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;

import static org.mockito.Mockito.*;

class TextViewerTest {

    private GUI mockGUI;
    private TextViewer textViewer;

    @BeforeEach
    void setUp() {
        mockGUI = mock(GUI.class);
        textViewer = new TextViewer() {
            @Override
            public void draw(char character, double x, double y, TextColor foregroundColor, GUI gui) {
                gui.drawPixel(x, y, foregroundColor);
            }

            @Override
            public void draw(String string, double x, double y, TextColor foregroundColor, GUI gui) {
                for (int i = 0; i < string.length(); i++) {
                    gui.drawPixel(x + i, y, foregroundColor);
                }
            }
        };
    }

    @Test
    void testDrawCharacter() {
        char character = 'A';
        double x = 10;
        double y = 20;
        TextColor color = TextColor.ANSI.RED;
        textViewer.draw(character, x, y, color, mockGUI);
        verify(mockGUI).drawPixel(x, y, color);
    }

    @Test
    void testDrawString() {
        String string = "HELLO";
        double x = 10;
        double y = 20;
        TextColor color = TextColor.ANSI.GREEN;
        textViewer.draw(string, x, y, color, mockGUI);
        for (int i = 0; i < string.length(); i++) {
            verify(mockGUI).drawPixel(x + i, y, color);
        }
    }
}
