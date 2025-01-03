package timelessodyssey.states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.control.Controller;
import timelessodyssey.control.credits.CreditsController;
import timelessodyssey.model.credits.Credits;
import timelessodyssey.view.SpriteLoader;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.screens.CreditsViewer;
import timelessodyssey.view.screens.ScreenViewer;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CreditsStateTest {

    private Credits mockCredits;
    private SpriteLoader mockSpriteLoader;
    private ViewerProvider mockViewerProvider;
    private CreditsState creditsState;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        // Mock dependencies
        mockCredits = mock(Credits.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockViewerProvider = mock(ViewerProvider.class);

        // Create instance of CreditsState
        creditsState = new CreditsState(mockCredits, mockSpriteLoader);
    }

    @Test
    void testCreateScreenViewer() {
        // Act
        ScreenViewer<Credits> screenViewer = creditsState.createScreenViewer(mockViewerProvider);

        // Assert
        assertNotNull(screenViewer, "ScreenViewer should not be null");
        assertTrue(screenViewer instanceof CreditsViewer, "ScreenViewer should be an instance of CreditsViewer");
    }

    @Test
    void testCreateController() {
        // Act
        Controller<Credits> controller = creditsState.createController();

        // Assert
        assertNotNull(controller, "Controller should not be null");
        assertTrue(controller instanceof CreditsController, "Controller should be an instance of CreditsController");
    }

    @Test
    void testAllowArrowSpam() {
        // Act
        boolean result = creditsState.allowArrowSpam();

        // Assert
        assertFalse(result, "allowArrowSpam should return false");
    }
}
