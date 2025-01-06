package timelessodyssey.model.credits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import timelessodyssey.model.game.elements.player.Player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditsTest {

    @Mock
    private Player player;

    private Credits credits;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(player.getStarCounter()).thenReturn(100);
        when(player.getNumberOfDeaths()).thenReturn(5);
        when(player.getBirthTime()).thenReturn(System.currentTimeMillis() - 125000); // 2 minutes and 5 seconds ago
        credits = new Credits(player);
    }

    @Test
    public void testConstructor() {
        assertEquals(100, credits.getScore());
        assertEquals(5, credits.getDeaths());
        assertEquals(5, credits.getSeconds());
        assertEquals(2, credits.getMinutes());

        String[] expectedMessages = {"Game Over!", "Thank you for playing :)"};
        assertArrayEquals(expectedMessages, credits.getMessages());

        String[] expectedNames = {"Bruno Oliveira", "   Joao Mendes", "Rodrigo Coelho"};
        assertArrayEquals(expectedNames, credits.getNames());
    }

    @Test
    public void testSetAndGetScore() {
        credits.setScore(200);
        assertEquals(200, credits.getScore());
    }

    @Test
    public void testSetAndGetMessages() {
        String[] newMessages = {"New Message 1", "New Message 2"};
        credits.setMessages(newMessages);
        assertArrayEquals(newMessages, credits.getMessages());
    }

    @Test
    public void testSetAndGetNames() {
        String[] newNames = {"Name 1", "Name 2", "Name 3"};
        credits.setNames(newNames);
        assertArrayEquals(newNames, credits.getNames());
    }
}
