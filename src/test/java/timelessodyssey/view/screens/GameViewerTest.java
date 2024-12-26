package timelessodyssey.view.screens;

import org.mockito.Mock;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.elements.ElementViewer;

class GameViewerTest {
    @Mock
    private ResizableGUI gui;

    @Mock
    private ViewerProvider viewerProvider;

    //@Mock
    //private ElementViewer<Player> playerViewer;

    @Mock
    private ElementViewer<Object> tileViewer; // Adjust as per actual type
    @Mock
    private ElementViewer<Object> spikeViewer; // Adjust as per actual type
    @Mock
    private ElementViewer<Object> starViewer; // Adjust as per actual type
    @Mock
    private ElementViewer<Object> particleViewer; // Adjust as per actual type

    @Mock
    private Scene scene;

    private GameViewer gameViewer;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        when(viewerProvider.getPlayerViewer()).thenReturn(playerViewer);
//        when(viewerProvider.getTileViewer()).thenReturn(tileViewer);
//        when(viewerProvider.getSpikeViewer()).thenReturn(spikeViewer);
//        when(viewerProvider.getStarViewer()).thenReturn(starViewer);
//        when(viewerProvider.getParticleViewer()).thenReturn(particleViewer);
//
//        gameViewer = new GameViewer(scene, viewerProvider);
//    }
//
//    @Test
//    void testDraw() throws IOException {
//        // Mocking the player and setting it in the scene
//        Player player = mock(Player.class);
//        when(scene.getPlayer()).thenReturn(player);
//
//        // Mock other elements
//        when(scene.getSpikes()).thenReturn(Collections.emptyList());
//        when(scene.getTiles()).thenReturn(new Object[0][0]); // Assuming tiles are an empty 2D array for simplicity
//        when(scene.getGoals()).thenReturn(Collections.emptyList());
//        when(scene.getStars()).thenReturn(Collections.emptyList());
//        when(scene.getSnow()).thenReturn(Collections.emptyList());
//        when(scene.getDeathParticles()).thenReturn(Collections.emptyList());
//
//        // Call the draw method
//        gameViewer.draw(gui, 1L);
//
//        // Verify interactions with GUI and viewers
//        verify(gui).clear();
//        verify(gui).drawRectangle(0, 0, gui.getWidth(), gui.getHeight(), GameViewer.backgroundColor);
//
//        // Verify that the player viewer draws the player
//        verify(playerViewer).draw(player, gui, 1L);
//
//        // Since there are no spikes, tiles, goals, stars, or particles, we don't expect any further draws.
//        verifyNoMoreInteractions(gui);
//    }
}
