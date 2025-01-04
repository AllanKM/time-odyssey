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
import java.util.Arrays;
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

    @Test
    void testDraw() throws IOException {
        when(gui.getWidth()).thenReturn(200);
        when(gui.getHeight()).thenReturn(100);

        List<Entry> entries = Arrays.asList(entry1, entry2);
        when(menu.getEntries()).thenReturn(entries);
        when(menu.getCurrentEntry()).thenReturn(entry1);

        menuViewer.draw(gui, 100L);

        verify(gui).clear();
        verify(gui).drawRectangle(eq(0.0d), eq(0.0d), eq(200), eq(1), eq(MenuViewer.frameColor));
        verify(gui).drawRectangle(eq(0.0d), eq(99.0d), eq(200), eq(1), eq(MenuViewer.frameColor));
        verify(gui).drawRectangle(eq(0.0d), eq(1.0d), eq(1), eq(98), eq(MenuViewer.frameColor));
        verify(gui).drawRectangle(eq(199.0d), eq(1.0d), eq(1), eq(98), eq(MenuViewer.frameColor));
        verify(gui).drawRectangle(eq(1.0d), eq(1.0d), eq(198), eq(98), eq(MenuViewer.backgroundColor));

        verify(entryViewer).draw(entry1, gui, MenuViewer.selectedColor);
        verify(entryViewer).draw(entry2, gui, MenuViewer.unselectedColor);

        verify(logoViewer).draw(gui, 44, 16);
        verify(gui).refresh();
    }

    @Test
    void testDrawWithNoEntries() throws IOException {
        when(gui.getWidth()).thenReturn(200);
        when(gui.getHeight()).thenReturn(100);

        when(menu.getEntries()).thenReturn(Arrays.asList());

        menuViewer.draw(gui, 100L);

        verify(gui).clear();
        verify(gui).drawRectangle(eq(0.0d), eq(0.0d), eq(200), eq(1), eq(MenuViewer.frameColor));
        verify(gui).drawRectangle(eq(0.0d), eq(99.0d), eq(200), eq(1), eq(MenuViewer.frameColor));
        verify(gui).drawRectangle(eq(0.0d), eq(1.0d), eq(1), eq(98), eq(MenuViewer.frameColor));
        verify(gui).drawRectangle(eq(199.0d), eq(1.0d), eq(1), eq(98), eq(MenuViewer.frameColor));
        verify(gui).drawRectangle(eq(1.0d), eq(1.0d), eq(198), eq(98), eq(MenuViewer.backgroundColor));

        verifyNoInteractions(entryViewer);

        verify(logoViewer).draw(gui, 44, 16);
        verify(gui).refresh();
    }

    @Test
    void testDrawWhenCurrentEntryIsNull() throws IOException {
        when(gui.getWidth()).thenReturn(200);
        when(gui.getHeight()).thenReturn(100);

        List<Entry> entries = Arrays.asList(entry1, entry2);
        when(menu.getEntries()).thenReturn(entries);
        when(menu.getCurrentEntry()).thenReturn(null);

        menuViewer.draw(gui, 100L);

        verify(gui).clear();
        verify(gui).drawRectangle(eq(0.0d), eq(0.0d), eq(200), eq(1), eq(MenuViewer.frameColor));
        verify(gui).drawRectangle(eq(0.0d), eq(99.0d), eq(200), eq(1), eq(MenuViewer.frameColor));
        verify(gui).drawRectangle(eq(0.0d), eq(1.0d), eq(1), eq(98), eq(MenuViewer.frameColor));
        verify(gui).drawRectangle(eq(199.0d), eq(1.0d), eq(1), eq(98), eq(MenuViewer.frameColor));
        verify(gui).drawRectangle(eq(1.0d), eq(1.0d), eq(198), eq(98), eq(MenuViewer.backgroundColor));

        verify(entryViewer).draw(entry1, gui, MenuViewer.unselectedColor);
        verify(entryViewer).draw(entry2, gui, MenuViewer.unselectedColor);

        verify(logoViewer).draw(gui, 44, 16);
        verify(gui).refresh();
    }
}
