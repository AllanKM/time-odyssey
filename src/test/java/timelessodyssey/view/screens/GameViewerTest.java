package timelessodyssey.view.screens;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.game.elements.Spike;
import timelessodyssey.model.game.elements.Star;
import timelessodyssey.model.game.elements.Tile;
import timelessodyssey.model.game.elements.particles.Particle;
import timelessodyssey.model.game.elements.player.Player;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.elements.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class GameViewerTest {

    @Mock
    private ResizableGUI gui;

    @Mock
    private ViewerProvider viewerProvider;

    @Mock
    private Scene scene;

    @Mock
    private Player player;

    @Mock
    private PlayerViewer playerViewer;

    @Mock
    private TileViewer tileViewer;

    @Mock
    private SpikeViewer spikeViewer;

    @Mock
    private StarViewer starViewer;

    @Mock
    private ParticleViewer particleViewer;

    private GameViewer gameViewer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(viewerProvider.getPlayerViewer()).thenReturn(playerViewer);
        when(viewerProvider.getTileViewer()).thenReturn(tileViewer);
        when(viewerProvider.getSpikeViewer()).thenReturn(spikeViewer);
        when(viewerProvider.getStarViewer()).thenReturn(starViewer);
        when(viewerProvider.getParticleViewer()).thenReturn(particleViewer);

        when(scene.getPlayer()).thenReturn(player);
        when(scene.getTiles()).thenReturn(new Tile[10][10]);
        when(scene.getGoals()).thenReturn(new Tile[10][10]);
        when(scene.getSpikes()).thenReturn(new Spike[10][10]);
        when(scene.getStars()).thenReturn(new Star[10][10]);
        when(scene.getSnow()).thenReturn(Collections.emptyList());
        when(scene.getDeathParticles()).thenReturn(Collections.emptyList());

        gameViewer = new GameViewer(scene, viewerProvider);
    }

    @Test
    public void testDraw() throws IOException {
        when(gui.getWidth()).thenReturn(200);
        when(gui.getHeight()).thenReturn(100);

        gameViewer.draw(gui, 100L);

        verify(gui).clear();
        verify(gui).drawRectangle(0, 0, 200, 100, GameViewer.backgroundColor);
        verify(playerViewer).draw(player, gui, 100L);
        verify(gui).refresh();
    }

    @Test
    public void testDrawWithTilesAndSpikes() throws IOException {
        Tile tile = mock(Tile.class);
        Spike spike = mock(Spike.class);

        Tile[][] tiles = new Tile[10][10];
        Spike[][] spikes = new Spike[10][10];
        tiles[0][0] = tile;
        spikes[1][1] = spike;

        when(scene.getTiles()).thenReturn(tiles);
        when(scene.getSpikes()).thenReturn(spikes);

        gameViewer.draw(gui, 100L);

        verify(tileViewer).draw(tile, gui, 100L);
        verify(spikeViewer).draw(spike, gui, 100L);
    }

    @Test
    public void testDrawWithStarsAndParticles() throws IOException {
        Star star = mock(Star.class);
        Particle particle = mock(Particle.class);

        Star[][] stars = new Star[10][10];
        stars[2][2] = star;

        List<Particle> snow = List.of(particle);

        when(scene.getStars()).thenReturn(stars);
        when(scene.getSnow()).thenReturn(snow);

        gameViewer.draw(gui, 100L);

        verify(starViewer).draw(star, gui, 100L);
        verify(particleViewer).draw(particle, gui, 100L);
    }

    @Test
    public void testDrawWithNullElements() throws IOException {
        Tile[][] tiles = new Tile[10][10];
        Spike[][] spikes = new Spike[10][10];
        tiles[0][0] = null;
        spikes[1][1] = null;

        when(scene.getTiles()).thenReturn(tiles);
        when(scene.getSpikes()).thenReturn(spikes);

        gameViewer.draw(gui, 100L);

        verifyNoInteractions(tileViewer, spikeViewer);
    }
}
