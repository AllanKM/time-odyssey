package timelessodyssey.sound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BackgroundSoundPlayerTest {

    private Clip mockClip;
    private BackgroundSoundPlayer soundPlayer;

    @BeforeEach
    void setUp() {
        mockClip = mock(Clip.class);
        soundPlayer = new BackgroundSoundPlayer(mockClip);
    }

    @Test
    void testStart() {
        soundPlayer.start();

        verify(mockClip).setMicrosecondPosition(0);
        verify(mockClip).start();
        verify(mockClip).loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Test
    void testStop() {
        soundPlayer.stop();
        verify(mockClip).stop();
    }

    @Test
    void testSetSound() {
        Clip newMockClip = mock(Clip.class);
        soundPlayer.setSound(newMockClip);
        assertEquals(newMockClip, soundPlayer.getSound(), "The set sound should match the retrieved sound");
    }

    @Test
    void testGetSound() {
        Clip retrievedClip = soundPlayer.getSound();
        assertEquals(mockClip, retrievedClip, "The retrieved sound should match the initial sound");
    }
}
