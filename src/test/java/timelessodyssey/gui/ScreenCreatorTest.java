package timelessodyssey.gui;

import com.googlecode.lanterna.screen.Screen;
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

class ScreenCreatorTest {

    @Mock
    private ScreenCreator screenCreator;

    @Mock
    private Screen screen;

    @Mock
    private KeyListener keyListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateScreen() throws IOException, URISyntaxException, FontFormatException {
        when(screenCreator.createScreen(any(ResizableGUI.Resolution.class), anyString(), any(KeyListener.class)))
                .thenReturn(screen);

        Screen result = screenCreator.createScreen(ResizableGUI.Resolution.FHD, "Test Title", keyListener);

        assertNotNull(result);
        assertEquals(screen, result);
        verify(screenCreator).createScreen(ResizableGUI.Resolution.FHD, "Test Title", keyListener);
    }

    @Test
    public void testCreateScreenThrowsExceptions() {
        assertThrows(IOException.class, () -> {
            doThrow(new IOException()).when(screenCreator).createScreen(any(), anyString(), any());
            screenCreator.createScreen(ResizableGUI.Resolution.FHD, "Test Title", keyListener);
        });

        assertThrows(URISyntaxException.class, () -> {
            doThrow(new URISyntaxException("", "")).when(screenCreator).createScreen(any(), anyString(), any());
            screenCreator.createScreen(ResizableGUI.Resolution.FHD, "Test Title", keyListener);
        });

        assertThrows(FontFormatException.class, () -> {
            doThrow(new FontFormatException("")).when(screenCreator).createScreen(any(), anyString(), any());
            screenCreator.createScreen(ResizableGUI.Resolution.FHD, "Test Title", keyListener);
        });
    }

    @Test
    public void testGetWidth() {
        when(screenCreator.getWidth()).thenReturn(1920);
        assertEquals(1920, screenCreator.getWidth());
    }

    @Test
    public void testGetHeight() {
        when(screenCreator.getHeight()).thenReturn(1080);
        assertEquals(1080, screenCreator.getHeight());
    }
}
