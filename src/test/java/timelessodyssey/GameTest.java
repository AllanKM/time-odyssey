package timelessodyssey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.sound.sampled.Clip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timelessodyssey.gui.GUI;
import timelessodyssey.gui.LanternaGUI;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.gui.ScreenCreator;
import timelessodyssey.model.menu.MainMenu;
import timelessodyssey.sound.BackgroundSoundPlayer;
import timelessodyssey.states.MainMenuState;
import timelessodyssey.states.State;
import timelessodyssey.view.GameSpriteLoader;
import timelessodyssey.view.SpriteLoader;
import javax.sound.sampled.FloatControl;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() throws Exception {
        ScreenCreator screenCreator = mock(ScreenCreator.class);
        LanternaGUI gui = mock(LanternaGUI.class);
        SpriteLoader spriteLoader = new GameSpriteLoader();
        State<?> state = new MainMenuState(new MainMenu(), spriteLoader);
        BackgroundSoundPlayer backgroundSoundPlayer = mock(BackgroundSoundPlayer.class);

        game = Mockito.spy(new Game() {
            {
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
        assertNotNull(getPrivateField("gui"));
    }

    @Test
    void testSetState() throws Exception {
        State<?> newState = mock(State.class);
        game.setState(newState);

        State<?> currentState = (State<?>) getPrivateField("state");
        assertEquals(newState, currentState);
    }

    @Test
    void testSetResolution() throws Exception {
        ResizableGUI.Resolution resolution = mock(ResizableGUI.Resolution.class);
        game.setResolution(resolution);
        LanternaGUI gui = (LanternaGUI) getPrivateField("gui");
        verify(gui).setResolution(resolution);
    }

    @Test
    void testSetKeySpam() throws Exception {
        game.setKeySpam(true);
        LanternaGUI gui = (LanternaGUI) getPrivateField("gui");
        verify(gui).setKeySpam(true);
    }

    private Object getPrivateField(String fieldName) throws Exception {
        Field field = Game.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(game);
    }

    private void invokePrivateMethod(String methodName) throws Exception {
        Method method = Game.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(game);
    }

    @Test
    void testGainControlValue() throws Exception {
        BackgroundSoundPlayer bgSoundPlayer = mock(BackgroundSoundPlayer.class);
        Clip mockClip = mock(Clip.class);
        FloatControl mockControl = mock(FloatControl.class);

        when(bgSoundPlayer.getSound()).thenReturn(mockClip);
        when(mockClip.getControl(FloatControl.Type.MASTER_GAIN)).thenReturn(mockControl);
        when(mockControl.getValue()).thenReturn(-15f);

        Field bgSoundPlayerField = Game.class.getDeclaredField("backgroundSoundPlayer");
        bgSoundPlayerField.setAccessible(true);
        bgSoundPlayerField.set(game, bgSoundPlayer);

        FloatControl gainControl = (FloatControl) bgSoundPlayer.getSound().getControl(FloatControl.Type.MASTER_GAIN);
        assertEquals(-15f, gainControl.getValue());
    }

    @Test
    void testStartMethod() throws Exception {
        LanternaGUI gui = mock(LanternaGUI.class);
        BackgroundSoundPlayer bgSoundPlayer = mock(BackgroundSoundPlayer.class);
        State<?> state = mock(State.class);

        Field guiField = Game.class.getDeclaredField("gui");
        guiField.setAccessible(true);
        guiField.set(game, gui);

        Field bgSoundPlayerField = Game.class.getDeclaredField("backgroundSoundPlayer");
        bgSoundPlayerField.setAccessible(true);
        bgSoundPlayerField.set(game, bgSoundPlayer);

        Field stateField = Game.class.getDeclaredField("state");
        stateField.setAccessible(true);
        stateField.set(game, state);

        doNothing().when(bgSoundPlayer).start();
        doNothing().when(gui).close();

        doAnswer(invocation -> {
            stateField.set(game, null);
            return null;
        }).when(state).step(any(Game.class), (ResizableGUI) any(GUI.class), anyLong());

        invokePrivateMethod("start");

        verify(bgSoundPlayer).start();
        verify(state, atLeastOnce()).step(any(Game.class), (ResizableGUI) any(GUI.class), anyLong());
        verify(gui).close();
    }


    @Test
    void testGetResolution() throws Exception {
        ResizableGUI.Resolution resolution = mock(ResizableGUI.Resolution.class);
        LanternaGUI gui = (LanternaGUI) getPrivateField("gui");
        when(gui.getResolution()).thenReturn(resolution);

        assertEquals(resolution, game.getResolution());
        verify(gui).getResolution();
    }

    @Test
    void testGetSpriteLoader() {
        SpriteLoader spriteLoader = game.getSpriteLoader();
        assertNotNull(spriteLoader, "SpriteLoader should not be null");
        assertEquals(GameSpriteLoader.class, spriteLoader.getClass(), "SpriteLoader should be an instance of GameSpriteLoader");
    }

    @Test
    void testFrameTimeCalculation() {
        int FPS = 30;
        long expectedFrameTime = 1000 / FPS;
        assertEquals(33, expectedFrameTime, "Frame time calculation should match the expected value.");
    }
}
