package timelessodyssey.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpriteLoaderTest {
    private SpriteLoader spriteLoader;

    @BeforeEach
    void setUp() {
        // Create a mock of the SpriteLoader interface
        spriteLoader = Mockito.mock(SpriteLoader.class);
    }

//    @Test
//    void testGetSpriteSuccessfully() throws IOException {
//        // Arrange
//        String spriteFilePath = "path/to/sprite.png";
//        Sprite expectedSprite = new Sprite(); // Assuming you have a default constructor
//
//        // Set up the mock behavior
//        when(spriteLoader.get(spriteFilePath)).thenReturn(expectedSprite);
//
//        // Act
//        Sprite actualSprite = spriteLoader.get(spriteFilePath);
//
//        // Assert
//        assertNotNull(actualSprite);
//        assertEquals(expectedSprite, actualSprite);
//    }

    @Test
    void testGetSpriteThrowsIOExceptionForInvalidPath() throws IOException {
        // Arrange
        String invalidSpriteFilePath = "invalid/path/to/sprite.png";

        // Set up the mock behavior to throw an IOException for invalid path
        when(spriteLoader.get(invalidSpriteFilePath)).thenThrow(new IOException("File not found"));

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> {
            spriteLoader.get(invalidSpriteFilePath);
        });

        assertEquals("File not found", exception.getMessage());
    }

    @Test
    void testGetSpriteThrowsIOExceptionForNullPath() throws IOException {
        // Arrange
        String nullSpriteFilePath = null;

        // Set up the mock behavior to throw an IOException for null path
        when(spriteLoader.get(nullSpriteFilePath)).thenThrow(new IOException("Invalid file path"));

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> {
            spriteLoader.get(nullSpriteFilePath);
        });

        assertEquals("Invalid file path", exception.getMessage());
    }
}
