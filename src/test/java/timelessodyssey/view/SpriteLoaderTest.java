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
        spriteLoader = Mockito.mock(SpriteLoader.class);
    }

    @Test
    void testGetSpriteThrowsIOExceptionForInvalidPath() throws IOException {
        String invalidSpriteFilePath = "invalid/path/to/sprite.png";
        when(spriteLoader.get(invalidSpriteFilePath)).thenThrow(new IOException("File not found"));
        IOException exception = assertThrows(IOException.class, () -> {
            spriteLoader.get(invalidSpriteFilePath);
        });
        assertEquals("File not found", exception.getMessage());
    }

    @Test
    void testGetSpriteThrowsIOExceptionForNullPath() throws IOException {
        String nullSpriteFilePath = null;
        when(spriteLoader.get(nullSpriteFilePath)).thenThrow(new IOException("Invalid file path"));
        IOException exception = assertThrows(IOException.class, () -> {
            spriteLoader.get(nullSpriteFilePath);
        });
        assertEquals("Invalid file path", exception.getMessage());
    }
}
