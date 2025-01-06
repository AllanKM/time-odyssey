package timelessodyssey.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResizableGUITest {

    @Mock
    private ResizableGUI resizableGUI;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testResolutionEnum() {
        assertEquals(1280, ResizableGUI.Resolution.WXGA.getWidth());
        assertEquals(720, ResizableGUI.Resolution.WXGA.getHeight());

        assertEquals(1920, ResizableGUI.Resolution.FHD.getWidth());
        assertEquals(1080, ResizableGUI.Resolution.FHD.getHeight());

        assertEquals(2560, ResizableGUI.Resolution.WQHD.getWidth());
        assertEquals(1440, ResizableGUI.Resolution.WQHD.getHeight());

        assertEquals(3840, ResizableGUI.Resolution.FOUR_K.getWidth());
        assertEquals(2160, ResizableGUI.Resolution.FOUR_K.getHeight());
    }

    @Test
    public void testGetResolution() {
        when(resizableGUI.getResolution()).thenReturn(ResizableGUI.Resolution.FHD);
        assertEquals(ResizableGUI.Resolution.FHD, resizableGUI.getResolution());
    }

    @Test
    public void testSetResolution() throws IOException, URISyntaxException, FontFormatException {
        doNothing().when(resizableGUI).setResolution(any(ResizableGUI.Resolution.class));
        resizableGUI.setResolution(ResizableGUI.Resolution.WQHD);
        verify(resizableGUI).setResolution(ResizableGUI.Resolution.WQHD);
    }

    @Test
    public void testSetResolutionThrowsExceptions() {
        assertThrows(IOException.class, () -> {
            doThrow(new IOException()).when(resizableGUI).setResolution(any(ResizableGUI.Resolution.class));
            resizableGUI.setResolution(ResizableGUI.Resolution.FHD);
        });

        assertThrows(URISyntaxException.class, () -> {
            doThrow(new URISyntaxException("", "")).when(resizableGUI).setResolution(any(ResizableGUI.Resolution.class));
            resizableGUI.setResolution(ResizableGUI.Resolution.FHD);
        });

        assertThrows(FontFormatException.class, () -> {
            doThrow(new FontFormatException("")).when(resizableGUI).setResolution(any(ResizableGUI.Resolution.class));
            resizableGUI.setResolution(ResizableGUI.Resolution.FHD);
        });
    }
}
