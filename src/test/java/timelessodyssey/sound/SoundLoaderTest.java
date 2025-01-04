package timelessodyssey.sound;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SoundLoaderTest {

    @Test
    void testLoadSoundSuccess() throws Exception {
        AudioInputStream mockAudioInputStream = mock(AudioInputStream.class);
        Clip mockClip = mock(Clip.class);
        SoundLoader soundLoader = new SoundLoader();
        Clip loadedClip = soundLoader.loadSound(mockAudioInputStream, mockClip);
        verify(mockClip).open(mockAudioInputStream);
        assertEquals(mockClip, loadedClip, "Loaded clip should match the provided clip");
    }

    @Test
    void testLoadSoundFailure() throws LineUnavailableException, IOException {
        AudioInputStream mockAudioInputStream = mock(AudioInputStream.class);
        Clip mockClip = mock(Clip.class);
        SoundLoader soundLoader = new SoundLoader();
        doThrow(new RuntimeException("Test exception")).when(mockClip).open(mockAudioInputStream);
        Exception exception = assertThrows(Exception.class, () ->
                soundLoader.loadSound(mockAudioInputStream, mockClip)
        );
        assertEquals("Unable to load sound file!", exception.getMessage(), "Exception message should match");
    }
}