package timelessodyssey.view.screens;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.credits.Credits;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.menu.LogoViewer;
import timelessodyssey.view.text.TextViewer;

import java.io.IOException;

import static org.mockito.Mockito.*;

class CreditsViewerTest {

    private ResizableGUI mockGUI;
    private Credits mockCredits;
    private ViewerProvider mockViewerProvider;
    private TextViewer mockTextViewer;
    private LogoViewer mockLogoViewer;
    private CreditsViewer creditsViewer;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockGUI = mock(ResizableGUI.class);
        mockCredits = mock(Credits.class);
        mockViewerProvider = mock(ViewerProvider.class);
        mockTextViewer = mock(TextViewer.class);
        mockLogoViewer = mock(LogoViewer.class);

        // Mock ViewerProvider behavior
        when(mockViewerProvider.getTextViewer()).thenReturn(mockTextViewer);
        when(mockViewerProvider.getLogoViewer()).thenReturn(mockLogoViewer);

        // Mock Credits behavior
        when(mockCredits.getMessages()).thenReturn(new String[]{"Message1", "Message2"});
        when(mockCredits.getNames()).thenReturn(new String[]{"Name1", "Name2"});
        when(mockCredits.getScore()).thenReturn(42);
        when(mockCredits.getDeaths()).thenReturn(3);
        when(mockCredits.getMinutes()).thenReturn(12);
        when(mockCredits.getSeconds()).thenReturn(34);

        // Create CreditsViewer instance
        creditsViewer = new CreditsViewer(mockCredits, mockViewerProvider);
    }

    @Test
    void testDraw() throws IOException {
        // Arrange
        when(mockGUI.getWidth()).thenReturn(200);

        // Act
        creditsViewer.draw(mockGUI, 100L);

        // Assert
        verify(mockGUI).clear();
        verify(mockTextViewer).draw(eq("Message1"), anyDouble(), anyDouble(), eq(CreditsViewer.messageColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Message2"), anyDouble(), anyDouble(), eq(CreditsViewer.messageColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Name1"), anyDouble(), eq(60.0d), eq(CreditsViewer.nameColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Name2"), anyDouble(), eq(70.0d), eq(CreditsViewer.nameColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Score:  42"), eq(10.0d), eq(60.0d), eq(CreditsViewer.scoreColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Deaths: 03"), eq(10.0d), eq(70.0d), eq(CreditsViewer.deathColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Time:   12:34"), eq(10.0d), eq(80.0d), eq(CreditsViewer.timeColor), eq(mockGUI));
        verify(mockLogoViewer).draw(mockGUI, 44, 16);
        verify(mockGUI).refresh();
    }


}