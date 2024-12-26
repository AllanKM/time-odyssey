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
        // Mock GUI
        mockGUI = mock(GUI.class);

        // Create a mock implementation of TextViewer
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
        // Arrange
        char character = 'A';
        double x = 10;
        double y = 20;
        TextColor color = TextColor.ANSI.RED;

        // Act
        textViewer.draw(character, x, y, color, mockGUI);

        // Assert
        verify(mockGUI).drawPixel(x, y, color);
    }

    @Test
    void testDrawString() {
        // Arrange
        String string = "HELLO";
        double x = 10;
        double y = 20;
        TextColor color = TextColor.ANSI.GREEN;

        // Act
        textViewer.draw(string, x, y, color, mockGUI);

        // Assert
        for (int i = 0; i < string.length(); i++) {
            verify(mockGUI).drawPixel(x + i, y, color);
        }
    }
}
