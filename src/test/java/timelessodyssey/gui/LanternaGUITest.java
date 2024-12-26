package timelessodyssey.gui;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;
import java.awt.Component;
import java.awt.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LanternaGUITest {
    private DefaultTerminalFactory terminalFactoryMock;
    private TerminalScreen terminalScreenMock;
    private AWTTerminalFrame terminalFrameMock;
    private Screen screenMock;
    private TextGraphics textGraphicsMock;
    private LanternaScreenCreator screenCreator;
    private LanternaGUI lanternaGUI;

    @BeforeEach
    void setUp() throws Exception {
        // Mock dependencies
        terminalFactoryMock = mock(DefaultTerminalFactory.class);
        terminalScreenMock = mock(TerminalScreen.class);
        terminalFrameMock = mock(AWTTerminalFrame.class);
        screenMock = mock(Screen.class);
        textGraphicsMock = mock(TextGraphics.class);

        // Mock the component
        Component componentMock = mock(Component.class);
        when(terminalFrameMock.getComponent(0)).thenReturn(componentMock);

        // Stub methods for terminal and screen creation
        when(terminalFactoryMock.createScreen()).thenReturn(terminalScreenMock);
        when(terminalScreenMock.getTerminal()).thenReturn(terminalFrameMock);
        when(terminalScreenMock.newTextGraphics()).thenReturn(textGraphicsMock);

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

        // Verify interactions based on the current implementation
        verify(textGraphicsMock).setBackgroundColor(color);
        verify(textGraphicsMock).setCharacter(10, 15, ' ');
    }

}
