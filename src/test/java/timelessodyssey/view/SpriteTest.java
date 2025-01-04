package timelessodyssey.view;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpriteTest {

    private GUI mockGUI;
    private Sprite sprite;
    private BufferedImage testImage;

    @BeforeEach
    void setUp() throws IOException {
        mockGUI = mock(GUI.class);

        String testImagePath = "test_image.png";
        URL resource = getClass().getClassLoader().getResource(testImagePath);
        assertNotNull(resource, "Test image resource should exist");
        File imageFile = new File(resource.getFile());

        testImage = ImageIO.read(imageFile);
        assertNotNull(testImage, "Test image should be loaded");

        sprite = new Sprite(testImagePath);
    }

    @Test
    void testGetImage() {
        BufferedImage image = sprite.getImage();
        assertNotNull(image, "Image should not be null");
        assertEquals(testImage.getWidth(), image.getWidth(), "Image width should match the expected value");
        assertEquals(testImage.getHeight(), image.getHeight(), "Image height should match the expected value");
    }

    @Test
    void testDraw() {
        sprite.draw(mockGUI, 5, 5);

        for (int dx = 0; dx < testImage.getWidth(); dx++) {
            for (int dy = 0; dy < testImage.getHeight(); dy++) {
                int ARGB = testImage.getRGB(dx, dy);
                int transparency = ARGB >> 24;
                if (transparency == 0) {
                    verify(mockGUI, never()).drawPixel(anyDouble(), anyDouble(), any(TextColor.class));
                } else {
                    TextColor expectedColor = new TextColor.RGB(
                            (ARGB >> 16) & 0xFF,
                            (ARGB >> 8) & 0xFF,
                            ARGB & 0xFF
                    );
                    verify(mockGUI).drawPixel(5 + dx, 5 + dy, expectedColor);
                }
            }
        }
    }
}
