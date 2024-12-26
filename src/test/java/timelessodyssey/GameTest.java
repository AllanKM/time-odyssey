package timelessodyssey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timelessodyssey.gui.LanternaGUI;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.gui.ScreenCreator;
import timelessodyssey.model.menu.MainMenu;
import timelessodyssey.sound.BackgroundSoundPlayer;
import timelessodyssey.states.MainMenuState;
import timelessodyssey.states.State;
import timelessodyssey.view.GameSpriteLoader;
import timelessodyssey.view.SpriteLoader;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() throws Exception {
        // Mock dependencies
        ScreenCreator screenCreator = mock(ScreenCreator.class);
        LanternaGUI gui = mock(LanternaGUI.class);
        SpriteLoader spriteLoader = new GameSpriteLoader();
        State<?> state = new MainMenuState(new MainMenu(), spriteLoader);
        BackgroundSoundPlayer backgroundSoundPlayer = mock(BackgroundSoundPlayer.class);

        // Create a Game instance with mocked dependencies
        game = Mockito.spy(new Game() {
            {
                // Override constructor to inject mocks
                try {
                    Field guiField = Game.class.getDeclaredField("gui");
                    guiField.setAccessible(true);
                    guiField.set(this, gui);

                    Field stateField = Game.class.getDeclaredField("state");
                    stateField.setAccessible(true);
                    stateField.set(this, state);

                    Field bgSoundPlayerField = Game.class.getDeclaredField("backgroundSoundPlayer");
                    bgSoundPlayerField.setAccessible(true);
                    bgSoundPlayerField.set(this, backgroundSoundPlayer);

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException("Failed to inject mocks", e);
                }
            }
        });
    }

    @Test
    void testGameInitialization() throws Exception {
        assertNotNull(getPrivateField("spriteLoader"));
        assertEquals(11, game.getNumberOfLevels());
        assertNotNull(getPrivateField("gui")); // Accessing gui via reflection
    }

    @Test
    void testSetState() throws Exception {
        State<?> newState = mock(State.class);
        game.setState(newState);

        // Verify that the state is set correctly using reflection
        State<?> currentState = (State<?>) getPrivateField("state");
        assertEquals(newState, currentState);
    }

    @Test
    void testSetResolution() throws Exception {
        ResizableGUI.Resolution resolution = mock(ResizableGUI.Resolution.class);

        // Call the method to set resolution
        game.setResolution(resolution);

        // Verify that the GUI's setResolution method was called
        LanternaGUI gui = (LanternaGUI) getPrivateField("gui");
        verify(gui).setResolution(resolution);
    }

    @Test
    void testSetKeySpam() throws Exception {
        game.setKeySpam(true);

        // Verify that the GUI's setKeySpam method was called with true using reflection
        LanternaGUI gui = (LanternaGUI) getPrivateField("gui");
        verify(gui).setKeySpam(true);
    }

//    @Test
//    void testStartGame() throws Exception {
//        // Call the start method indirectly by invoking it through reflection.
//        // This is necessary since start() is private.
//
//        // Use reflection to call the private start method
//        invokePrivateMethod("start");
//
//        // Verify that background sound player starts playing using reflection
//        BackgroundSoundPlayer bgSoundPlayer = (BackgroundSoundPlayer) getPrivateField("backgroundSoundPlayer");
//        verify(bgSoundPlayer).start();
//    }

    private Object getPrivateField(String fieldName) throws Exception {
        Field field = Game.class.getDeclaredField(fieldName);
        field.setAccessible(true); // Make it accessible
        return field.get(game); // Return the value of the field from the game instance
    }

    private void invokePrivateMethod(String methodName) throws Exception {
        Method method = Game.class.getDeclaredMethod(methodName);
        method.setAccessible(true); // Make it accessible
        method.invoke(game); // Invoke the private method on the game instance
    }
}
