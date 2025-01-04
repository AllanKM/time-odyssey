package timelessodyssey.view.elements;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
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
    private Scene mockScene;

    @BeforeEach
    void setUp() throws IOException {
        mockGUI = mock(GUI.class);
        mockSpriteLoader = mock(SpriteLoader.class);
        mockPlayer = mock(Player.class);
        mockScene = mock(Scene.class);

        when(mockPlayer.getScene()).thenReturn(mockScene);
        when(mockScene.getDeathParticles()).thenReturn(Collections.emptyList());

        Sprite mockSprite = mock(Sprite.class);
        when(mockSpriteLoader.get(anyString())).thenReturn(mockSprite);
        playerViewer = new PlayerViewer(mockSpriteLoader);
    }

//    @Test
//    void testDraw_IdleState() throws IOException {
//        when(mockPlayer.getState()).thenReturn(new IdleState(mockPlayer));
//        when(mockPlayer.getPosition()).thenReturn(new Vector(8, 20));
//        when(mockPlayer.isFacingRight()).thenReturn(true);
//
//        playerViewer.draw(mockPlayer, mockGUI, 100L);
//
//        verify(mockSpriteLoader).get("sprites/player/player-right-1.png");
//        verify(mockSpriteLoader.get("sprites/player/player-right-1.png")).draw(mockGUI, 8, 20);
//    }

//    @Test
//    void testDraw_WalkingState_RightDirection() throws IOException {
//        when(mockPlayer.getState()).thenReturn(new WalkingState(mockPlayer));
//        when(mockPlayer.getPosition()).thenReturn(new Vector(13, 25));
//        when(mockPlayer.isFacingRight()).thenReturn(true);
//        when(mockPlayer.getVelocity()).thenReturn(new Vector(WalkingState.MIN_VELOCITY + 0.1, 0));
//
//        playerViewer.draw(mockPlayer, mockGUI, 101L);
//
//        verify(mockSpriteLoader).get("sprites/player/player-right-3.png");
//        verify(mockSpriteLoader.get("sprites/player/player-right-3.png")).draw(mockGUI, 13, 25);
//    }

    @Test
    void testDraw_WalkingState_LeftDirection() throws IOException {
        when(mockPlayer.getState()).thenReturn(new WalkingState(mockPlayer));
        when(mockPlayer.getPosition()).thenReturn(new Vector(18, 30));
        when(mockPlayer.isFacingRight()).thenReturn(false);
        when(mockPlayer.getVelocity()).thenReturn(new Vector(-(WalkingState.MIN_VELOCITY + 0.1), 0));

        playerViewer.draw(mockPlayer, mockGUI, 102L);

        verify(mockSpriteLoader).get("sprites/player/player-left-3.png");
        verify(mockSpriteLoader.get("sprites/player/player-left-3.png")).draw(mockGUI, 18, 30);
    }

//    @Test
//    void testDraw_DeadState() throws IOException {
//        Scene mockScene = mock(Scene.class);
//        when(mockPlayer.getScene()).thenReturn(mockScene);
//        when(mockScene.getDeathParticles()).thenReturn(Collections.emptyList());
//        when(mockPlayer.getState()).thenReturn(new DeadState(mockPlayer, 10));
//        when(mockPlayer.getPosition()).thenReturn(new Vector(5, 10));
//
//        playerViewer.draw(mockPlayer, mockGUI, 103L);
//
//        verify(mockSpriteLoader).get("sprites/player/player-dead.png");
//        verify(mockSpriteLoader.get("sprites/player/player-dead.png")).draw(mockGUI, 5, 10);
//    }


//    @Test
//    void testNoInteractionWhenStateIsNull() throws IOException {
//        when(mockPlayer.getState()).thenReturn(null);
//
//        playerViewer.draw(mockPlayer, mockGUI, 104L);
//
//        verifyNoInteractions(mockSpriteLoader);
//    }
}
