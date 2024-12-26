package timelessodyssey.view.elements;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.player.DeadState;
import timelessodyssey.model.game.elements.player.IdleState;
import timelessodyssey.model.game.elements.player.Player;
import timelessodyssey.model.game.elements.player.WalkingState;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

class PlayerViewerTest {

    private GUI mockGUI;
    private SpriteLoader mockSpriteLoader;
    private Player mockPlayer;
    private PlayerViewer playerViewer;

    @BeforeEach
    void setUp() throws IOException {
        // Mock dependencies
        mockGUI = mock(GUI.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockPlayer = mock(Player.class);

        // Prepare mock sprites
        Sprite mockSprite = mock(Sprite.class);
        when(mockSpriteLoader.get(anyString())).thenReturn(mockSprite);

        // Create instance of PlayerViewer
        playerViewer = new PlayerViewer(mockSpriteLoader);
    }

    @Test
    void testDraw_IdleState() throws IOException {
        // Arrange
        when(mockPlayer.getState()).thenReturn(new IdleState(mockPlayer));
        when(mockPlayer.getPosition()).thenReturn(new Vector(10, 20));
        when(mockPlayer.isFacingRight()).thenReturn(true);

        // Act
        playerViewer.draw(mockPlayer, mockGUI, 100L);

        // Assert
        verify(mockSpriteLoader).get("sprites/player/player-right-1.png");
    }

    @Test
    void testDraw_WalkingState() throws IOException {
        // Arrange
        when(mockPlayer.getState()).thenReturn(new WalkingState(mockPlayer));
        when(mockPlayer.getPosition()).thenReturn(new Vector(15, 25));
        when(mockPlayer.isFacingRight()).thenReturn(false);
        when(mockPlayer.getVelocity()).thenReturn(new Vector(WalkingState.MIN_VELOCITY + 0.1, 0));

        // Act
        playerViewer.draw(mockPlayer, mockGUI, 101L);

        // Assert
        verify(mockSpriteLoader).get("sprites/player/player-left-3.png");
    }

//    @Test
//    void testDraw_DeadState() {
//        // Arrange
//        Scene mockScene = mock(Scene.class); // Mock the scene required by DeadState or Player
//        when(mockPlayer.getScene()).thenReturn(mockScene); // Complete stubbing
//        when(mockPlayer.getState()).thenReturn(new DeadState(mockPlayer, 50));
//        when(mockPlayer.getPosition()).thenReturn(new Vector(30, 40));
//        when(mockPlayer.isFacingRight()).thenReturn(true);
//
//        // Act
//        playerViewer.draw(mockPlayer, mockGUI, 200L);
//
//        // Assert
//        verifyNoInteractions(mockGUI);
//    }


}
