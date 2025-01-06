package timelessodyssey.view.elements;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;

import static org.mockito.Mockito.*;

class ElementViewerTest {

    private static class TestElementViewer implements ElementViewer<Object> {
        @Override
        public void draw(Object model, GUI gui, long frameCount) {
            gui.drawPixel(0, 0, TextColor.ANSI.WHITE);
        }
    }

    private Object mockModel;
    private GUI mockGUI;
    private ElementViewer<Object> elementViewer;

    @BeforeEach
    public void setUp() {
        mockModel = new Object();
        mockGUI = mock(GUI.class);
        elementViewer = new TestElementViewer();
    }

    @Test
    public void testDraw() {
        elementViewer.draw(mockModel, mockGUI, 100L);
        verify(mockGUI).drawPixel(0, 0, TextColor.ANSI.WHITE);
    }
}
