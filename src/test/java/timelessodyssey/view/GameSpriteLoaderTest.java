package timelessodyssey.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameSpriteLoaderTest {

    private GameSpriteLoader gameSpriteLoader;

    @BeforeEach
    public void setUp() {
        gameSpriteLoader = new GameSpriteLoader();
    }

    @Test
    public void testGetLoadsSpriteIfNotPresent() throws IOException {
        String spriteFilepath = "path/to/sprite.png";
        Sprite mockSprite = mock(Sprite.class);
        gameSpriteLoader.spriteMap.put(spriteFilepath, mockSprite);
        Sprite sprite = gameSpriteLoader.get(spriteFilepath);
        assertNotNull(sprite, "Sprite should not be null");
        assertSame(mockSprite, sprite, "Should return the mock sprite instance");
    }

    @Test
    public void testGetReturnsExistingSprite() throws IOException {
        String spriteFilepath = "path/to/sprite.png";
        Sprite firstSprite = mock(Sprite.class);
        gameSpriteLoader.spriteMap.put(spriteFilepath, firstSprite);
        Sprite secondSprite = gameSpriteLoader.get(spriteFilepath);
        assertSame(firstSprite, secondSprite, "It Should return the same instance for the same filepath");
    }

}
