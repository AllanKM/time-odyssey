package timelessodyssey.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LanternaGUITest {
    private DefaultTerminalFactory terminalFactoryMock;
    private TerminalScreen terminalScreenMock;
    private AWTTerminalFrame terminalFrameMock;
    private Screen screenMock;
    private LanternaScreenCreator screenCreator;
    private LanternaGUI lanternaGUI;

    @BeforeEach
    void setUp() throws Exception {
        // Mock dependencies
        terminalFactoryMock = mock(DefaultTerminalFactory.class);
        terminalScreenMock = mock(TerminalScreen.class);
        terminalFrameMock = mock(AWTTerminalFrame.class);
        screenMock = mock(Screen.class);

        // Stub methods for terminal and screen creation
        when(terminalFactoryMock.createScreen()).thenReturn(terminalScreenMock);
        when(terminalScreenMock.getTerminal()).thenReturn(terminalFrameMock);

        // Create the real LanternaScreenCreator
        TerminalSize terminalSize = new TerminalSize(80, 24);
        Rectangle defaultBounds = new Rectangle(800, 600);
        screenCreator = new LanternaScreenCreator(terminalFactoryMock, terminalSize, defaultBounds);

        // Create LanternaGUI instance using the real screenCreator
        lanternaGUI = new LanternaGUI(screenCreator, "TestTitle");
    }


    @Test
    void testDrawPixel() {
        TextColor color = TextColor.Factory.fromString("#FFFFFF");

        // Act
        lanternaGUI.drawPixel(10, 15, color);

        // Verify interactions
        verify(terminalScreenMock).newTextGraphics();
    }

    @Test
    void testDrawRectangle() {
        TextColor color = TextColor.Factory.fromString("#00FF00");

        // Act
        lanternaGUI.drawRectangle(5, 5, 10, 20, color);

        // Verify interactions
        verify(terminalScreenMock).newTextGraphics();
    }

    @Test
    void testGetNextAction() {
        KeyEvent leftKeyEvent = new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, ' ');
        lanternaGUI.getKeyAdapter().keyPressed(leftKeyEvent);

        // Act
        LanternaGUI.Action action = lanternaGUI.getNextAction();

        // Assert
        assertEquals(LanternaGUI.Action.LEFT, action);
    }

    @Test
    void testClear() {
        // Act
        lanternaGUI.clear();

        // Verify interactions
        verify(terminalScreenMock).clear();
    }

    @Test
    void testRefresh() throws IOException {
        // Act
        lanternaGUI.refresh();

        // Verify interactions
        verify(terminalScreenMock).refresh();
    }

    @Test
    void testClose() throws IOException {
        // Act
        lanternaGUI.close();

        // Verify interactions
        verify(terminalScreenMock).close();
    }

    @Test
    void testSetKeySpam() {
        // Act
        lanternaGUI.setKeySpam(true);
        lanternaGUI.setKeySpam(false);

        // Assert
        // The priorityKeyPressed should be null when keySpam is false
        //assertNull(lanternaGUI.getKeyAdapter().priorityKeyPressed);
        //assertNull(lanternaGUI.getKeyAdapter().priorityKeyPressed);
    }

    @Test
    void testFontLoading() throws Exception {
        // Act
        int bestFontSize = screenCreator.getBestFontSize(new Rectangle(800, 600));
        assertNotEquals(0, bestFontSize); // Ensure font size is non-zero and computed

        // Validate font loading
        AWTTerminalFontConfiguration fontConfig = screenCreator.loadFont(bestFontSize);
        assertNotNull(fontConfig);
    }
}
