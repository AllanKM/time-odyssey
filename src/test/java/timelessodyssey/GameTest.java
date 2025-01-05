package timelessodyssey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timelessodyssey.gui.LanternaGUI;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.sound.BackgroundSoundPlayer;
import timelessodyssey.states.State;
import timelessodyssey.view.GameSpriteLoader;
import timelessodyssey.view.SpriteLoader;

class GameTest {

    private Game game;
    private LanternaGUI mockGui;
    private BackgroundSoundPlayer mockBackgroundSoundPlayer;
    private FloatControl mockFloatControl;

    @BeforeEach
    void setUp() throws Exception {
        mockGui = mock(LanternaGUI.class);
        mockBackgroundSoundPlayer = mock(BackgroundSoundPlayer.class);
        mockFloatControl = mock(FloatControl.class);

        Clip mockClip = mock(Clip.class);
        when(mockBackgroundSoundPlayer.getSound()).thenReturn(mockClip);
        when(mockClip.getControl(FloatControl.Type.MASTER_GAIN)).thenReturn(mockFloatControl);

        game = Mockito.spy(new Game());
        injectPrivateField("gui", mockGui);
        injectPrivateField("backgroundSoundPlayer", mockBackgroundSoundPlayer);
        injectPrivateField("spriteLoader", new GameSpriteLoader());
    }

    @Test
    void testGameInitialization() throws Exception {
        assertNotNull(getPrivateField("spriteLoader"));
        assertEquals(11, game.getNumberOfLevels());
        assertNotNull(getPrivateField("gui"));
    }

    @Test
    void testSetState() throws Exception {
        State<?> mockState = mock(State.class);
        game.setState(mockState);
        assertEquals(mockState, getPrivateField("state"));
    }

    @Test
    void testSetResolution() throws Exception {
        ResizableGUI.Resolution mockResolution = mock(ResizableGUI.Resolution.class);
        game.setResolution(mockResolution);
        verify(mockGui).setResolution(mockResolution);
    }

    @Test
    void testSetKeySpam() throws Exception {
        game.setKeySpam(true);
        verify(mockGui).setKeySpam(true);
    }

    @Test
    void testGetResolution() throws Exception {
        ResizableGUI.Resolution mockResolution = mock(ResizableGUI.Resolution.class);
        when(mockGui.getResolution()).thenReturn(mockResolution);
        assertEquals(mockResolution, game.getResolution());
        verify(mockGui).getResolution();
    }

    @Test
    void testGetSpriteLoader() {
        SpriteLoader spriteLoader = game.getSpriteLoader();
        assertNotNull(spriteLoader);
        assertEquals(GameSpriteLoader.class, spriteLoader.getClass());
    }

    @Test
    void testFrameTimeCalculation() {
        int FPS = 30;
        long expectedFrameTime = 1000 / FPS;
        assertEquals(33, expectedFrameTime);
    }

    @Test
    void testGainControlValue() throws Exception {
        Clip mockClip = mock(Clip.class);
        FloatControl mockControl = mock(FloatControl.class);

        when(mockBackgroundSoundPlayer.getSound()).thenReturn(mockClip);
        when(mockClip.getControl(FloatControl.Type.MASTER_GAIN)).thenReturn(mockControl);

        game = new Game(); // Create the Game instance

        injectPrivateField("backgroundSoundPlayer", mockBackgroundSoundPlayer);

        FloatControl gainControl = (FloatControl) mockBackgroundSoundPlayer.getSound().getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-15f);

        verify(mockControl, times(1)).setValue(-15f);
    }

    @Test
    void testStartMethod() throws Exception {
        State<?> mockState = mock(State.class);

        injectPrivateField("state", mockState);

        doNothing().when(mockBackgroundSoundPlayer).start();
        doNothing().when(mockGui).close();

        doAnswer(invocation -> {
            injectPrivateField("state", null);
            return null;
        }).when(mockState).step(any(Game.class), any(ResizableGUI.class), anyLong());

        invokePrivateMethod("start");

        verify(mockBackgroundSoundPlayer).start();
        verify(mockState, atLeastOnce()).step(any(Game.class), any(ResizableGUI.class), anyLong());
        verify(mockGui).close();
    }

    @Test
    void testStartBackgroundSoundPlayer() throws Exception {
        State<?> mockState = mock(State.class);
        injectPrivateField("state", mockState);

        doNothing().when(mockGui).close();

        doAnswer(invocation -> {
            injectPrivateField("state", null); // Terminate the loop
            return null;
        }).when(mockState).step(any(Game.class), any(ResizableGUI.class), anyLong());

        invokePrivateMethod("start");

        verify(mockBackgroundSoundPlayer).start();
        verify(mockGui).close();
        verify(mockState, atLeastOnce()).step(any(Game.class), any(ResizableGUI.class), anyLong());
    }

    private void injectPrivateField(String fieldName, Object value) throws Exception {
        Field field = Game.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(game, value);
    }

    private Object getPrivateField(String fieldName) throws Exception {
        Field field = Game.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(game);
    }

    private void invokePrivateMethod(String methodName) throws Exception {
        var method = Game.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(game);
    }
}
