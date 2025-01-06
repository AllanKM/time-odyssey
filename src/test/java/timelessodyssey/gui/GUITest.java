package timelessodyssey.gui;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GUITest {

    @Mock
    private GUI gui;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWidth() {
        when(gui.getWidth()).thenReturn(100);
        assertEquals(100, gui.getWidth());
    }

    @Test
    public void testGetHeight() {
        when(gui.getHeight()).thenReturn(80);
        assertEquals(80, gui.getHeight());
    }

    @Test
    public void testDrawPixel() {
        gui.drawPixel(10.0, 20.0, TextColor.ANSI.RED);
        verify(gui).drawPixel(10.0, 20.0, TextColor.ANSI.RED);
    }

    @Test
    public void testDrawRectangle() {
        gui.drawRectangle(5.0, 5.0, 10, 10, TextColor.ANSI.BLUE);
        verify(gui).drawRectangle(5.0, 5.0, 10, 10, TextColor.ANSI.BLUE);
    }

    @Test
    public void testClear() {
        gui.clear();
        verify(gui).clear();
    }

    @Test
    public void testGetNextAction() {
        when(gui.getNextAction()).thenReturn(GUI.Action.UP);
        assertEquals(GUI.Action.UP, gui.getNextAction());
    }

    @Test
    public void testRefresh() throws IOException {
        gui.refresh();
        verify(gui).refresh();
    }

    @Test
    public void testClose() throws IOException {
        gui.close();
        verify(gui).close();
    }

    @Test
    public void testActionEnum() {
        assertEquals(9, GUI.Action.values().length);
        assertArrayEquals(
                new GUI.Action[]{
                        GUI.Action.UP, GUI.Action.RIGHT, GUI.Action.DOWN, GUI.Action.LEFT,
                        GUI.Action.NONE, GUI.Action.QUIT, GUI.Action.SELECT, GUI.Action.JUMP,
                        GUI.Action.DASH
                },
                GUI.Action.values()
        );
    }
}
