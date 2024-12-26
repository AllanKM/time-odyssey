package timelessodyssey.sound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SoundPlayerTest {

    private static class TestSoundPlayer implements SoundPlayer {
        private Clip sound;

        @Override
        public void start() {
            if (sound != null) {
                sound.setMicrosecondPosition(0);
                sound.start();
            }
        }

        @Override
        public void stop() {
            if (sound != null) {
                sound.stop();
            }
        }

        @Override
        public void setSound(Clip sound) {
            this.sound = sound;
        }

        @Override
        public Clip getSound() {
            return sound;
        }
    }

    private Clip mockClip;
    private SoundPlayer soundPlayer;

    @BeforeEach
    void setUp() {
        // Mock the Clip object
        mockClip = mock(Clip.class);

        // Create an instance of TestSoundPlayer
        soundPlayer = new TestSoundPlayer();
        soundPlayer.setSound(mockClip);
    }

    @Test
    void testStart() {
        // Act
        soundPlayer.start();

        // Assert
        verify(mockClip).setMicrosecondPosition(0);
        verify(mockClip).start();
    }

    @Test
    void testStop() {
        // Act
        soundPlayer.stop();

        // Assert
        verify(mockClip).stop();
    }

    @Test
    void testSetSound() {
        // Arrange
        Clip newMockClip = mock(Clip.class);

        // Act
        soundPlayer.setSound(newMockClip);

        // Assert
        assertEquals(newMockClip, soundPlayer.getSound(), "The set sound should match the retrieved sound");
    }

    @Test
    void testGetSound() {
        // Act
        Clip retrievedClip = soundPlayer.getSound();

        // Assert
        assertEquals(mockClip, retrievedClip, "The retrieved sound should match the initially set sound");
    }
}