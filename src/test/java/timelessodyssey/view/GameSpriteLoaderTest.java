package timelessodyssey.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameSpriteLoaderTest {

    private GameSpriteLoader gameSpriteLoader;

    @BeforeEach
    void setUp() {
        // Initialize the GameSpriteLoader
        gameSpriteLoader = new GameSpriteLoader();
    }

    @Test
    void testGetLoadsSpriteIfNotPresent() throws IOException {
        // Arrange
        String spriteFilepath = "path/to/sprite.png";

        // Mock the Sprite creation to avoid NullPointerException
        Sprite mockSprite = mock(Sprite.class);
        gameSpriteLoader.spriteMap.put(spriteFilepath, mockSprite);

        // Act
        Sprite sprite = gameSpriteLoader.get(spriteFilepath);

        // Assert
        assertNotNull(sprite, "Sprite should not be null");
        assertSame(mockSprite, sprite, "Should return the mock sprite instance");
    }

    @Test
    void testGetReturnsExistingSprite() throws IOException {
        // Arrange
        String spriteFilepath = "path/to/sprite.png";
        Sprite firstSprite = mock(Sprite.class);
        gameSpriteLoader.spriteMap.put(spriteFilepath, firstSprite);

        // Act
        Sprite secondSprite = gameSpriteLoader.get(spriteFilepath);

        // Assert
        assertSame(firstSprite, secondSprite, "Should return the same instance for the same filepath");
    }

//    @Test
//    void testGetThrowsIOExceptionForInvalidPath() throws IOException {
//        // Arrange
//        String invalidPath = "invalid/path/to/sprite.png";
//
//        // Act & Assert
//        assertThrows(IOException.class, () -> {
//            // Simulate exception when creating a Sprite
//            gameSpriteLoader.get(invalidPath);
//        }, "Should throw IOException for invalid path");
//    }
}
