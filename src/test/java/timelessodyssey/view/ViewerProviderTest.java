package timelessodyssey.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.view.elements.PlayerViewer;
import timelessodyssey.view.menu.LogoViewer;

class ViewerProviderTest {
    @Mock
    private SpriteLoader spriteLoader;

    private ViewerProvider viewerProvider;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        MockitoAnnotations.openMocks(this);
        viewerProvider = new ViewerProvider(spriteLoader);
    }

    @Test
    void testViewerInitialization() {
        // Verify that all viewers are initialized correctly
        assertNotNull(viewerProvider.getParticleViewer());
        assertNotNull(viewerProvider.getPlayerViewer());
        assertNotNull(viewerProvider.getSpikeViewer());
        assertNotNull(viewerProvider.getStarViewer());
        assertNotNull(viewerProvider.getTileViewer());
        assertNotNull(viewerProvider.getTextViewer());
        assertNotNull(viewerProvider.getEntryViewer());
        assertNotNull(viewerProvider.getLogoViewer());
    }

    @Test
    void testGetPlayerViewerReturnsCorrectInstance() {
        PlayerViewer playerViewer = viewerProvider.getPlayerViewer();
        assertEquals(playerViewer, viewerProvider.getPlayerViewer());
    }

    @Test
    void testGetLogoViewerReturnsCorrectInstance() {
        LogoViewer logoViewer = viewerProvider.getLogoViewer();
        assertEquals(logoViewer, viewerProvider.getLogoViewer());
    }

    // Add more tests for other viewers as needed...
}
