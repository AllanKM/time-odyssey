package timelessodyssey.view.screens;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.menu.Entry;
import timelessodyssey.model.menu.Menu;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.menu.EntryViewer;
import timelessodyssey.view.menu.LogoViewer;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

class MenuViewerTest {
    @Mock
    private ResizableGUI gui;

    @Mock
    private ViewerProvider viewerProvider;

    @Mock
    private EntryViewer entryViewer;

    @Mock
    private LogoViewer logoViewer;

    @Mock
    private Menu menu;

    @Mock
    private Entry entry1;

    @Mock
    private Entry entry2;

    private MenuViewer<Menu> menuViewer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(viewerProvider.getEntryViewer()).thenReturn(entryViewer);
        when(viewerProvider.getLogoViewer()).thenReturn(logoViewer);

        menuViewer = new MenuViewer<>(menu, viewerProvider);
    }

//    @Test
//    void testDraw() throws IOException {
//        // Set up the menu entries and current entry
//        List<Entry> entries = List.of(entry1, entry2);
//        when(menu.getEntries()).thenReturn(entries);
//        when(menu.getCurrentEntry()).thenReturn(entry1);
//
//        // Mock GUI dimensions
//        when(gui.getWidth()).thenReturn(80); // Example width
//        when(gui.getHeight()).thenReturn(24); // Example height
//
//        // Call the draw method
//        menuViewer.draw(gui, 1L);
//
//        // Verify interactions with GUI and viewers
//        verify(gui).clear();
//
//        // Verify that background is drawn correctly
//        verify(gui).drawRectangle(0.0d, 0.0d, 80, 24, MenuViewer.backgroundColor); // Background
//
//        // Verify that each entry is drawn with the correct color based on selection
//        verify(entryViewer).draw(entry1, gui, MenuViewer.selectedColor);
//        verify(entryViewer).draw(entry2, gui, MenuViewer.unselectedColor);
//
//        // Verify that the logo is drawn at the specified position
//        verify(logoViewer).draw(gui, 44, 16);
//
//        // Ensure that refresh is called on the GUI
//        verify(gui).refresh();
//
//        // Verify specific calls made in drawBackgroundAndFrame (if applicable)
//        verify(gui).drawRectangle(0.0d, 0.0d, 80, 1, new TextColor.RGB(255, 255, 255)); // Top border
//        verify(gui).drawRectangle(0.0d, 23.0d, 80, 1, new TextColor.RGB(255, 255, 255)); // Bottom border
//        verify(gui).drawRectangle(0.0d, 1.0d, 1, 22, new TextColor.RGB(255, 255, 255)); // Left border
//        verify(gui).drawRectangle(79.0d, 1.0d, 1, 22, new TextColor.RGB(255, 255, 255)); // Right border
//
//        // Verify background rectangle drawn in ScreenViewer
//        //verify(gui).drawRectangle(1.0d, 1.0d, (int) 78.0d, (int) 22.0d, new TextColor.RGB(28 ,28 ,46)); // Background rectangle inside borders
//    }
}
