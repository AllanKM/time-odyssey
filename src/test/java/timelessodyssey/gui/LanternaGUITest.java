package timelessodyssey.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI.Action;
import timelessodyssey.gui.ResizableGUI.Resolution;

class LanternaGUITest {

    private DefaultTerminalFactory terminalFactoryMock;
    private Screen screenMock;
    private TextGraphics textGraphicsMock;
    private LanternaScreenCreator screenCreator;
    private LanternaGUI lanternaGUI;

    @BeforeEach
    public void setUp() throws Exception {
        terminalFactoryMock = mock(DefaultTerminalFactory.class);
        screenMock = mock(Screen.class);
        textGraphicsMock = mock(TextGraphics.class);

        when(screenMock.newTextGraphics()).thenReturn(textGraphicsMock);

        screenCreator = mock(LanternaScreenCreator.class);
        when(screenCreator.createScreen(any(), any(), any())).thenReturn(screenMock);

        lanternaGUI = new LanternaGUI(screenCreator, "TestTitle");
    }

    @Test
    public void testSetResolution() throws Exception {
        Resolution resolution = ResizableGUI.Resolution.FHD;
        lanternaGUI.setResolution(resolution);
        verify(screenMock, atLeastOnce()).setCursorPosition(null);
        verify(screenMock, atLeastOnce()).startScreen();
    }


    @Test
    public void testDrawPixel() {
        TextColor color = TextColor.Factory.fromString("#FFFFFF");
        lanternaGUI.drawPixel(10, 15, color);
        verify(textGraphicsMock).setBackgroundColor(color);
        verify(textGraphicsMock).setCharacter(10, 15, ' ');
    }

    @Test
    void testDrawRectangle() {
        TextColor color = TextColor.Factory.fromString("#FF0000");
        lanternaGUI.drawRectangle(5, 5, 10, 15, color);
        verify(textGraphicsMock).setBackgroundColor(color);
        verify(textGraphicsMock).fillRectangle(any(), any(), eq(' '));
    }

    @Test
    void testClearScreen() {
        lanternaGUI.clear();
        verify(screenMock).clear();
    }

    @Test
    void testRefreshScreen() throws Exception {
        lanternaGUI.refresh();
        verify(screenMock).refresh();
    }

    @Test
    void testCloseScreen() throws Exception {
        lanternaGUI.close();
        verify(screenMock).close();
    }

    @Test
    void testGetWidth() {
        when(screenCreator.getWidth()).thenReturn(80);
        assertEquals(80, lanternaGUI.getWidth());
        verify(screenCreator).getWidth();
    }

    @Test
    void testGetHeight() {
        when(screenCreator.getHeight()).thenReturn(24);
        assertEquals(24, lanternaGUI.getHeight());
        verify(screenCreator).getHeight();
    }

    @Test
    void testGetResolution() throws IOException, URISyntaxException, FontFormatException {
        Resolution resolution = Resolution.FHD;
        lanternaGUI.setResolution(resolution);
        assertEquals(resolution, lanternaGUI.getResolution());
    }

    @Test
    void testGetNextAction() {
        KeyEvent keyEvent = mock(KeyEvent.class);
        when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);
        lanternaGUI.setKeySpam(false);
        lanternaGUI.getKeyAdapter().keyPressed(keyEvent);
        assertEquals(Action.LEFT, lanternaGUI.getNextAction());
    }

    @Test
    void testGetNextActionWithSpamKey() {
        KeyEvent keyEvent = mock(KeyEvent.class);
        when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);
        lanternaGUI.setKeySpam(true);
        lanternaGUI.getKeyAdapter().keyPressed(keyEvent);
        assertEquals(Action.LEFT, lanternaGUI.getNextAction());
    }

    @Test
    void testGetNextActionForAllKeys() {
        KeyEvent keyEventMock = mock(KeyEvent.class);

        when(keyEventMock.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
        lanternaGUI.getKeyAdapter().keyPressed(keyEventMock);
        assertEquals(Action.RIGHT, lanternaGUI.getNextAction());

        when(keyEventMock.getKeyCode()).thenReturn(KeyEvent.VK_UP);
        lanternaGUI.getKeyAdapter().keyPressed(keyEventMock);
        assertEquals(Action.UP, lanternaGUI.getNextAction());

        when(keyEventMock.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);
        lanternaGUI.getKeyAdapter().keyPressed(keyEventMock);
        assertEquals(Action.DOWN, lanternaGUI.getNextAction());

        when(keyEventMock.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
        lanternaGUI.getKeyAdapter().keyPressed(keyEventMock);
        assertEquals(Action.QUIT, lanternaGUI.getNextAction());

        when(keyEventMock.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
        lanternaGUI.getKeyAdapter().keyPressed(keyEventMock);
        assertEquals(Action.SELECT, lanternaGUI.getNextAction());

        when(keyEventMock.getKeyCode()).thenReturn(KeyEvent.VK_SPACE);
        lanternaGUI.getKeyAdapter().keyPressed(keyEventMock);
        assertEquals(Action.JUMP, lanternaGUI.getNextAction());

        when(keyEventMock.getKeyCode()).thenReturn(KeyEvent.VK_X);
        lanternaGUI.getKeyAdapter().keyPressed(keyEventMock);
        assertEquals(Action.DASH, lanternaGUI.getNextAction());

        when(keyEventMock.getKeyCode()).thenReturn(KeyEvent.VK_A);
        lanternaGUI.getKeyAdapter().keyPressed(keyEventMock);
        assertEquals(Action.NONE, lanternaGUI.getNextAction());
    }
}
