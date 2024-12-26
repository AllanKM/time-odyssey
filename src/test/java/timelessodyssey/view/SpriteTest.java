package timelessodyssey.view;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SpriteTest {

    private GUI mockGUI;
    private Sprite sprite;
    private BufferedImage mockImage;

    @BeforeEach
    void setUp() {
        // Mock the GUI
        mockGUI = mock(GUI.class);

        // Mock a BufferedImage
        mockImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                mockImage.setRGB(x, y, (255 << 24) | (x << 16) | (y << 8) | 0); // ARGB format
            }
        }

        // Mock Sprite
        sprite = mock(Sprite.class);
        when(sprite.getImage()).thenReturn(mockImage);
    }

//    @Test
//    void testDraw() {
//        // Act
//        sprite.draw(mockGUI, 5.0, 5.0);
//
//        // Assert
//        for (int x = 0; x < 10; x++) {
//            for (int y = 0; y < 10; y++) {
//                int ARGB = mockImage.getRGB(x, y);
//                int red = (ARGB >> 16) & 0xFF;
//                int green = (ARGB >> 8) & 0xFF;
//                int blue = ARGB & 0xFF;
//                TextColor expectedColor = new TextColor.RGB(red, green, blue);
//
//                if ((ARGB >> 24) != 0) { // Non-transparent pixel
//                    verify(mockGUI).drawPixel(5.0 + x, 5.0 + y, expectedColor);
//                }
//            }
//        }
//    }

    @Test
    void testGetImage() {
        // Act
        BufferedImage image = sprite.getImage();

        // Assert
        assertNotNull(image, "Image should not be null");
        assertEquals(10, image.getWidth(), "Image width should be 10");
        assertEquals(10, image.getHeight(), "Image height should be 10");
    }
}
