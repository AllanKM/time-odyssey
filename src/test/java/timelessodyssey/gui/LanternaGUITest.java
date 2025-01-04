package timelessodyssey.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import java.awt.FontFormatException;
import java.awt.Rectangle;
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
    void setUp() throws Exception {
        terminalFactoryMock = mock(DefaultTerminalFactory.class);
        screenMock = mock(Screen.class);
        textGraphicsMock = mock(TextGraphics.class);

        when(screenMock.newTextGraphics()).thenReturn(textGraphicsMock);

        screenCreator = mock(LanternaScreenCreator.class);
        when(screenCreator.createScreen(any(), any(), any())).thenReturn(screenMock);

        lanternaGUI = new LanternaGUI(screenCreator, "TestTitle");
    }

    @Test
    void testSetResolution() throws Exception {
        Resolution resolution = ResizableGUI.Resolution.FHD;
        lanternaGUI.setResolution(resolution);
        verify(screenMock, atLeastOnce()).setCursorPosition(null);
        verify(screenMock, atLeastOnce()).startScreen();
    }


    @Test
    void testDrawPixel() {
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
}
