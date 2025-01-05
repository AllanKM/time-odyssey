package timelessodyssey.view.screens;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.credits.Credits;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.menu.LogoViewer;
import timelessodyssey.view.text.TextViewer;

class CreditsViewerTest {

    private ResizableGUI mockGUI;
    private Credits mockCredits;
    private ViewerProvider mockViewerProvider;
    private TextViewer mockTextViewer;
    private LogoViewer mockLogoViewer;
    private CreditsViewer creditsViewer;

    @BeforeEach
    void setUp() {
        mockGUI = mock(ResizableGUI.class);
        mockCredits = mock(Credits.class);
        mockViewerProvider = mock(ViewerProvider.class);
        mockTextViewer = mock(TextViewer.class);
        mockLogoViewer = mock(LogoViewer.class);
        when(mockViewerProvider.getTextViewer()).thenReturn(mockTextViewer);
        when(mockViewerProvider.getLogoViewer()).thenReturn(mockLogoViewer);

        when(mockCredits.getMessages()).thenReturn(new String[]{"Message1", "Message2"});
        when(mockCredits.getNames()).thenReturn(new String[]{"Name1", "Name2"});
        when(mockCredits.getScore()).thenReturn(42);
        when(mockCredits.getDeaths()).thenReturn(3);
        when(mockCredits.getMinutes()).thenReturn(12);
        when(mockCredits.getSeconds()).thenReturn(34);

        creditsViewer = new CreditsViewer(mockCredits, mockViewerProvider);
    }

    @Test
    void testDraw() throws IOException {
        when(mockGUI.getWidth()).thenReturn(200);

        creditsViewer.draw(mockGUI, 100L);

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

    @Test
    void testDrawWithNoMessages() throws IOException {
        when(mockCredits.getMessages()).thenReturn(new String[0]);
        when(mockGUI.getWidth()).thenReturn(200);

        creditsViewer.draw(mockGUI, 100L);

        verify(mockGUI).clear();
        verify(mockTextViewer, never()).draw(anyString(), anyDouble(), anyDouble(), eq(CreditsViewer.messageColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Name1"), anyDouble(), eq(60.0d), eq(CreditsViewer.nameColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Score:  42"), eq(10.0d), eq(60.0d), eq(CreditsViewer.scoreColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Deaths: 03"), eq(10.0d), eq(70.0d), eq(CreditsViewer.deathColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Time:   12:34"), eq(10.0d), eq(80.0d), eq(CreditsViewer.timeColor), eq(mockGUI));
        verify(mockLogoViewer).draw(mockGUI, 44, 16);
        verify(mockGUI).refresh();
    }

    @Test
    void testDrawWithNullCredits() throws IOException {
        when(mockCredits.getMessages()).thenReturn(new String[0]); // Return an empty array instead of null
        when(mockCredits.getNames()).thenReturn(new String[0]); // Return an empty array instead of null
        when(mockGUI.getWidth()).thenReturn(200);

        creditsViewer.draw(mockGUI, 100L);

        verify(mockGUI).clear();
        verify(mockTextViewer, never()).draw(anyString(), anyDouble(), anyDouble(), eq(CreditsViewer.messageColor), eq(mockGUI));
        verify(mockTextViewer, never()).draw(anyString(), anyDouble(), anyDouble(), eq(CreditsViewer.nameColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Score:  42"), eq(10.0d), eq(60.0d), eq(CreditsViewer.scoreColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Deaths: 03"), eq(10.0d), eq(70.0d), eq(CreditsViewer.deathColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Time:   12:34"), eq(10.0d), eq(80.0d), eq(CreditsViewer.timeColor), eq(mockGUI));
        verify(mockLogoViewer).draw(mockGUI, 44, 16);
        verify(mockGUI).refresh();
    }

    @Test
    void testDrawWithEmptyNames() throws IOException {
        when(mockCredits.getNames()).thenReturn(new String[0]);
        when(mockGUI.getWidth()).thenReturn(200);

        creditsViewer.draw(mockGUI, 100L);

        verify(mockGUI).clear();
        verify(mockTextViewer).draw(eq("Message1"), anyDouble(), anyDouble(), eq(CreditsViewer.messageColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Message2"), anyDouble(), anyDouble(), eq(CreditsViewer.messageColor), eq(mockGUI));
        verify(mockTextViewer, never()).draw(anyString(), anyDouble(), anyDouble(), eq(CreditsViewer.nameColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Score:  42"), eq(10.0d), eq(60.0d), eq(CreditsViewer.scoreColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Deaths: 03"), eq(10.0d), eq(70.0d), eq(CreditsViewer.deathColor), eq(mockGUI));
        verify(mockTextViewer).draw(eq("Time:   12:34"), eq(10.0d), eq(80.0d), eq(CreditsViewer.timeColor), eq(mockGUI));
        verify(mockLogoViewer).draw(mockGUI, 44, 16);
        verify(mockGUI).refresh();
    }
}
