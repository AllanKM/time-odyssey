package timelessodyssey.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LanternaScreenCreatorTest {

    @Mock
    private DefaultTerminalFactory terminalFactory;

    @Mock
    private TerminalScreen screen;

    @Mock
    private AWTTerminalFrame terminal;

    private LanternaScreenCreator screenCreator;
    private TerminalSize terminalSize;
    private Rectangle defaultBounds;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        terminalSize = new TerminalSize(80, 24);
        defaultBounds = new Rectangle(800, 600);
        screenCreator = new LanternaScreenCreator(terminalFactory, terminalSize, defaultBounds);
    }

    @Test
    public void testConstructor() {
        assertNotNull(screenCreator);
        verify(terminalFactory).setInitialTerminalSize(terminalSize);
        verify(terminalFactory).setForceAWTOverSwing(true);
    }

    @Test
    public void testCreateScreen() throws IOException, URISyntaxException, FontFormatException {
        when(terminalFactory.createScreen()).thenReturn(screen);
        when(screen.getTerminal()).thenReturn(terminal);
        when(terminal.getComponent(0)).thenReturn(mock(Component.class));

        ResizableGUI.Resolution resolution = ResizableGUI.Resolution.FHD;
        KeyListener keyListener = mock(KeyListener.class);
        Screen result = screenCreator.createScreen(resolution, "Test Title", keyListener);

        assertNotNull(result);
        verify(terminal).setTitle("Test Title");
        verify(terminal.getComponent(0)).addKeyListener(keyListener);
    }


    @Test
    public void testLoadFont() throws URISyntaxException, IOException, FontFormatException {
        AWTTerminalFontConfiguration fontConfig = screenCreator.loadFont(12);
        assertNotNull(fontConfig);
    }

    @Test
    public void testGetBestFontSize() {
        Rectangle bounds = new Rectangle(800, 600);
        int fontSize = screenCreator.getBestFontSize(bounds);
        assertTrue(fontSize > 0);
        assertTrue(fontSize <= Math.min(bounds.width / terminalSize.getColumns(), bounds.height / terminalSize.getRows()));
    }

    @Test
    public void testGetWidth() {
        assertEquals(terminalSize.getColumns(), screenCreator.getWidth());
    }

    @Test
    public void testGetHeight() {
        assertEquals(terminalSize.getRows(), screenCreator.getHeight());
    }
}
