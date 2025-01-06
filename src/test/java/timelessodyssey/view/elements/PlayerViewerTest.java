package timelessodyssey.view.elements;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import timelessodyssey.gui.GUI;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.player.*;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

class PlayerViewerTest {

    private GUI mockGUI;
    private SpriteLoader mockSpriteLoader;
    private Player mockPlayer;
    private PlayerViewer playerViewer;
    private Scene mockScene;

    @BeforeEach
    public void setUp() throws IOException {
        mockGUI = mock(GUI.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockPlayer = mock(Player.class);
        mockScene = mock(Scene.class);

        when(mockPlayer.getScene()).thenReturn(mockScene);
        when(mockScene.getDeathParticles()).thenReturn(Collections.emptyList());

        when(mockSpriteLoader.get("sprites/player/player-right-4.png"))
                .thenReturn(mock(Sprite.class));
        when(mockSpriteLoader.get("sprites/player/player-left-4.png"))
                .thenReturn(mock(Sprite.class));

        playerViewer = new PlayerViewer(mockSpriteLoader);

        clearInvocations(mockSpriteLoader);
    }

    @Test
    public void testDraw_FallingState() throws IOException {
        // Mock the sprite that will be used
        Sprite mockSprite = mock(Sprite.class);
        when(mockSpriteLoader.get("sprites/player/player-left-7.png")).thenReturn(mockSprite);

        // Reinitialize the PlayerViewer with the mockSpriteLoader
        playerViewer = new PlayerViewer(mockSpriteLoader);
        when(mockPlayer.getState()).thenReturn(new FallingState(mockPlayer));
        when(mockPlayer.getPosition()).thenReturn(new Vector(100, 150));
        when(mockPlayer.isFacingRight()).thenReturn(false);

        playerViewer.draw(mockPlayer, mockGUI, 300L);

        verify(mockSpriteLoader, times(2)).get("sprites/player/player-left-7.png");
        verify(mockSprite).draw(mockGUI, 100, 150);
    }


    @Test
    public void testDraw_DashingState() throws IOException {
        Sprite mockSprite = mock(Sprite.class);
        when(mockSpriteLoader.get("sprites/player/player-right-4.png")).thenReturn(mockSprite);

        playerViewer = new PlayerViewer(mockSpriteLoader);

        when(mockPlayer.getState()).thenReturn(new DashingState(mockPlayer));
        when(mockPlayer.getPosition()).thenReturn(new Vector(200, 250));
        when(mockPlayer.isFacingRight()).thenReturn(true);

        playerViewer.draw(mockPlayer, mockGUI, 400L);

        verify(mockSpriteLoader, times(2)).get("sprites/player/player-right-4.png");
        verify(mockSprite).draw(mockGUI, 198, 250);
    }


    @Test
    public void testDraw_DeadState() throws IOException {
        when(mockPlayer.getScene()).thenReturn(mockScene);
        when(mockScene.getDeathParticles()).thenReturn(Collections.emptyList());
        when(mockPlayer.getPosition()).thenReturn(new Vector(0, 0));

        DeadState deadState = new DeadState(mockPlayer, 50);
        when(mockPlayer.getState()).thenReturn(deadState);

        playerViewer.draw(mockPlayer, mockGUI, 0L);

        verifyNoInteractions(mockGUI);
    }


    @Test
    public void testGetSprite() throws IOException {
        when(mockPlayer.getState()).thenReturn(new RunningState(mockPlayer));
        when(mockPlayer.isFacingRight()).thenReturn(true);

        //Sprite sprite = playerViewer.getSprite(mockPlayer, 300L);
        //assertNotNull(sprite, "Sprite should not be null for valid states");
    }
}
