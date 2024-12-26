package timelessodyssey.view.menu;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.Vector;
import timelessodyssey.model.menu.Entry;
import timelessodyssey.view.text.TextViewer;

import static org.mockito.Mockito.*;

class EntryViewerTest {

    private TextViewer mockTextViewer;
    private ResizableGUI mockResizableGUI;
    private EntryViewer entryViewer;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockTextViewer = mock(TextViewer.class);
        mockResizableGUI = mock(ResizableGUI.class);

        // Create EntryViewer instance
        entryViewer = new EntryViewer(mockTextViewer);
    }

    @Test
    void testDrawStartGameEntry() {
        // Arrange
        Entry startGameEntry = new Entry(10, 20, Entry.Type.START_GAME);
        TextColor mockColor = TextColor.ANSI.WHITE;

        // Act
        entryViewer.draw(startGameEntry, mockResizableGUI, mockColor);

        // Assert
        verify(mockTextViewer).draw("Start", 10, 20, mockColor, mockResizableGUI);
    }

    @Test
    void testDrawResolutionEntryWithAutomatic() {
        // Arrange
        Entry resolutionEntry = new Entry(15, 25, Entry.Type.RESOLUTION);
        TextColor mockColor = TextColor.ANSI.GREEN;
        when(mockResizableGUI.getResolution()).thenReturn(null);

        // Act
        entryViewer.draw(resolutionEntry, mockResizableGUI, mockColor);

        // Assert
        verify(mockTextViewer).draw("Resolution:   Automatic >", 15, 25, mockColor, mockResizableGUI);
    }

    @Test
    void testDrawResolutionEntryWithSpecificResolution() {
        // Arrange
        Entry resolutionEntry = new Entry(15, 25, Entry.Type.RESOLUTION);
        TextColor mockColor = TextColor.ANSI.GREEN;
        ResizableGUI.Resolution mockResolution = mock(ResizableGUI.Resolution.class);
        when(mockResolution.getWidth()).thenReturn(1920);
        when(mockResolution.getHeight()).thenReturn(1080);
        when(mockResizableGUI.getResolution()).thenReturn(mockResolution);

        // Act
        entryViewer.draw(resolutionEntry, mockResizableGUI, mockColor);

        // Assert
        verify(mockTextViewer).draw("Resolution: < 1920X1080 > ", 15, 25, mockColor, mockResizableGUI);
    }


    @Test
    void testDrawToMainMenuEntry() {
        // Arrange
        Entry toMainMenuEntry = new Entry(5, 10, Entry.Type.TO_MAIN_MENU);
        TextColor mockColor = TextColor.ANSI.YELLOW;

        // Act
        entryViewer.draw(toMainMenuEntry, mockResizableGUI, mockColor);

        // Assert
        verify(mockTextViewer).draw("Go Back", 5, 10, mockColor, mockResizableGUI);
    }
}
