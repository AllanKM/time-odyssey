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
        // Mock the Clip object
        mockClip = mock(Clip.class);

        // Create an instance of BackgroundSoundPlayer with the mocked Clip
        soundPlayer = new BackgroundSoundPlayer(mockClip);
    }

    @Test
    void testStart() {
        // Act
        soundPlayer.start();

        // Assert
        verify(mockClip).setMicrosecondPosition(0);
        verify(mockClip).start();
        verify(mockClip).loop(Clip.LOOP_CONTINUOUSLY);
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
        assertEquals(mockClip, retrievedClip, "The retrieved sound should match the initial sound");
    }
}
