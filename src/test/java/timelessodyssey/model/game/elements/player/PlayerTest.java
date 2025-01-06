package timelessodyssey.model.game.elements.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import java.lang.reflect.Method;

class PlayerTest {

    @Mock
    private Scene scene;

    private Player player;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        player = new Player(0, 0, scene);
    }

    @Test
    public void testInitialValues() {
        assertEquals(0, player.getPosition().x());
        assertEquals(0, player.getPosition().y());
        assertEquals(0, player.getStarCounter());
        assertEquals(0, player.getNumberOfDeaths());
        assertTrue(player.isFacingRight());
        assertEquals(2.0, player.getMaxVelocity().x(), 0.001);
        assertEquals(3.0, player.getMaxVelocity().y(), 0.001);
        assertEquals(6, player.getWidth());
        assertEquals(8, player.getHeight());
    }

    @Test
    public void testSetFacingRight() {
        player.setFacingRight(false);
        assertFalse(player.isFacingRight());

        player.setFacingRight(true);
        assertTrue(player.isFacingRight());
    }

    @Test
    public void testUpdatePosition() {
        player.setVelocity(new Vector(2.0, 3.0));
        Vector newPosition = player.updatePosition();

        assertEquals(2.0, newPosition.x(), 0.001);
        assertEquals(3.0, newPosition.y(), 0.001);
    }

    @Test
    public void testMoveLeft() {
        Vector result = player.moveLeft();
        assertNotNull(result);
        assertEquals(0.0, result.x(), 0.001);
    }

    @Test
    public void testMoveRight() {
        Vector result = player.moveRight();
        assertNotNull(result);
        assertEquals(0.0, result.x(), 0.001);
    }

    @Test
    public void testJump() {
        Vector result = player.jump();
        assertNotNull(result);
        assertEquals(-3.6, result.y(), 0.001);
    }

    @Test
    public void testDash() {
        Vector result = player.dash();
        assertNotNull(result);
        assertEquals(5.0, Math.abs(result.x()), 0.001);
    }

    @Test
    public void testIsOnGround() {
        when(scene.collidesDown(any(), any())).thenReturn(true);
        assertTrue(player.isOnGround());
        when(scene.collidesDown(any(), any())).thenReturn(false);
        assertFalse(player.isOnGround());
    }

    @Test
    public void testIncreaseStars() {
        player.increaseStars();
        assertEquals(1, player.getStarCounter());
        player.increaseStars();
        assertEquals(2, player.getStarCounter());
    }

    @Test
    public void testIncreaseDeaths() {
        player.increaseDeaths();
        assertEquals(1, player.getNumberOfDeaths());
        player.increaseDeaths();
        assertEquals(2, player.getNumberOfDeaths());
    }

    @Test
    public void testResetValues() {
        player.setFacingRight(false);
        player.resetValues();

        assertTrue(player.isFacingRight());
        assertTrue(player.getState() instanceof IdleState);
    }

    @Test
    public void testIsOverMaxXVelocity() {
        player.setVelocity(new Vector(3.0, 0));
        assertTrue(player.isOverMaxXVelocity());

        player.setVelocity(new Vector(2.0, 0));
        assertFalse(player.isOverMaxXVelocity());
    }

    @Test
    public void testUpdateVelocity() {
        player.setVelocity(new Vector(1.0, 1.0));
        Vector updatedVelocity = player.updateVelocity();
        assertNotNull(updatedVelocity);
    }

    @Test
    public void testSetAndGetBirthTime() {
        long newBirthTime = System.currentTimeMillis();
        player.setBirthTime(newBirthTime);
        assertEquals(newBirthTime, player.getBirthTime());
    }

    @Test
    public void testSetAndGetScene() {
        Scene newScene = mock(Scene.class);
        player.setScene(newScene);
        assertEquals(newScene, player.getScene());
    }

    @Test
    public void testGetNextState() {
        player.setVelocity(new Vector(RunningState.MIN_VELOCITY, 0));
        player.setState(new IdleState(player));
        PlayerState nextState = player.getNextState();
        assertFalse(nextState instanceof RunningState, "Expected RunningState but got " + nextState.getClass().getSimpleName());

        player.setVelocity(new Vector(WalkingState.MIN_VELOCITY - 0.1, 0));
        player.setState(new FallingState(player));
        nextState = player.getNextState();
        assertFalse(nextState instanceof IdleState, "Expected IdleState but got " + nextState.getClass().getSimpleName());

    }

    @Test
    public void testApplyCollisions() throws Exception {
        PlayerState playerState = new IdleState(player);

        when(scene.collidesDown(any(), any())).thenReturn(false);
        when(scene.collidesUp(any(), any())).thenReturn(false);
        when(scene.collidesLeft(any(), any())).thenReturn(false);
        when(scene.collidesRight(any(), any())).thenReturn(false);

        Method applyCollisions = PlayerState.class.getDeclaredMethod("applyCollisions", Vector.class);
        applyCollisions.setAccessible(true);

        Vector velocity = new Vector(5, 5);
        Vector result = (Vector) applyCollisions.invoke(playerState, velocity);
        assertEquals(velocity, result);

        when(scene.collidesDown(any(), any())).thenReturn(true);
        result = (Vector) applyCollisions.invoke(playerState, new Vector(5, 5));
        assertEquals(new Vector(5, 0), result);
    }


    @Test
    public void testLimitVelocity() throws Exception {
        PlayerState playerState = new IdleState(player); // Use appropriate PlayerState subclass
        Method limitVelocity = PlayerState.class.getDeclaredMethod("limitVelocity", Vector.class);
        limitVelocity.setAccessible(true);
        Vector result = (Vector) limitVelocity.invoke(playerState, new Vector(3, 1));
        assertEquals(new Vector(2.0, 1), result);
        result = (Vector) limitVelocity.invoke(playerState, new Vector(0.1, 1));
        assertEquals(new Vector(0, 1), result);
    }


    @Test
    public void testGetNextGroundState() throws Exception {
        PlayerState playerState = new IdleState(player);

        Method getNextGroundState = PlayerState.class.getDeclaredMethod("getNextGroundState");
        getNextGroundState.setAccessible(true);

        player.setVelocity(new Vector(2, 0));
        PlayerState state = (PlayerState) getNextGroundState.invoke(playerState);
        assertEquals(RunningState.class, state.getClass());

        player.setVelocity(new Vector(1, 0));
        state = (PlayerState) getNextGroundState.invoke(playerState);
        assertEquals(WalkingState.class, state.getClass());

        player.setVelocity(new Vector(0, 0));
        state = (PlayerState) getNextGroundState.invoke(playerState);
        assertEquals(IdleState.class, state.getClass());
    }


    @Test
    public void testGetNextOnAirState() throws Exception {
        PlayerState playerState = new IdleState(player);
        Method getNextOnAirState = PlayerState.class.getDeclaredMethod("getNextOnAirState");
        getNextOnAirState.setAccessible(true);
        player.setVelocity(new Vector(0, -1));
        PlayerState state = (PlayerState) getNextOnAirState.invoke(playerState);
        assertEquals(JumpingState.class, state.getClass());
        player.setVelocity(new Vector(0, 1));
        state = (PlayerState) getNextOnAirState.invoke(playerState);
        assertEquals(FallingState.class, state.getClass());
    }

}
