package timelessodyssey.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

    @Mock
    private Game game;

    @Mock
    private GUI.Action action;

    private TestController<String> testController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testController = new TestController<>("Test Model");
    }

    @Test
    void constructor_shouldInitializeModel() {
        assertEquals("Test Model", testController.getModel());
    }

    @Test
    void step_shouldBeCalledWithCorrectParameters() throws IOException, URISyntaxException, FontFormatException {
        testController.step(game, action, 100L);
        assertTrue(testController.stepCalled);
        assertEquals(game, testController.lastGame);
        assertEquals(action, testController.lastAction);
        assertEquals(100L, testController.lastFrameCount);
    }

    // Concrete implementation of Controller for testing
    private static class TestController<T> extends Controller<T> {
        boolean stepCalled = false;
        Game lastGame;
        GUI.Action lastAction;
        long lastFrameCount;

        public TestController(T model) {
            super(model);
        }

        @Override
        public void step(Game game, GUI.Action action, long frameCount) throws IOException, URISyntaxException, FontFormatException {
            stepCalled = true;
            lastGame = game;
            lastAction = action;
            lastFrameCount = frameCount;
        }
    }
}
