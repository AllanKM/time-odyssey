package timelessodyssey.states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.control.Controller;
import timelessodyssey.gui.GUI;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.view.SpriteLoader;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.screens.ScreenViewer;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    private static class TestState extends State<Object> {
        public TestState(Object model, SpriteLoader spriteLoader) throws IOException, URISyntaxException {
            super(model, spriteLoader);
        }

        @Override
        protected ScreenViewer<Object> createScreenViewer(ViewerProvider viewerProvider) {
            return mock(ScreenViewer.class);
        }

        @Override
        protected Controller<Object> createController() {
            return mock(Controller.class);
        }

        @Override
        protected boolean allowArrowSpam() {
            return true;
        }
    }

    private Object mockModel;
    private SpriteLoader mockSpriteLoader;
    private Game mockGame;
    private ResizableGUI mockGUI;
    private TestState testState;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        // Mock dependencies
        mockModel = new Object();
        mockSpriteLoader = mock(SpriteLoader.class);
        mockGame = mock(Game.class);
        mockGUI = mock(ResizableGUI.class);

        // Create instance of TestState
        testState = new TestState(mockModel, mockSpriteLoader);
    }

    @Test
    void testGetModel() {
        // Assert
        assertEquals(mockModel, testState.getModel(), "The model should match the initialized value");
    }

    @Test
    void testStep() throws IOException, URISyntaxException, FontFormatException {
        // Arrange
        GUI.Action mockAction = mock(GUI.Action.class);
        when(mockGUI.getNextAction()).thenReturn(mockAction);

        // Act
        testState.step(mockGame, mockGUI, 100L);

        // Assert
        verify(mockGame).setKeySpam(true);
        verify(testState.controller).step(mockGame, mockAction, 100L);
        verify(testState.screenViewer).draw(mockGUI, 100L);
    }
}
