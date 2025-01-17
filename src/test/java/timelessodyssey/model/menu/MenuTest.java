package timelessodyssey.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    private static class TestMenu extends Menu {
        @Override
        protected List<Entry> createEntries() {
            return Arrays.asList(
                    new Entry(0, 0, Entry.Type.START_GAME),
                    new Entry(0, 1, Entry.Type.SETTINGS),
                    new Entry(0, 2, Entry.Type.EXIT)
            );
        }
    }

    private Menu menu;

    @BeforeEach
    public void setUp() {
        menu = new TestMenu();
    }

    @Test
    public void testGetEntries() {
        List<Entry> entries = menu.getEntries();
        assertNotNull(entries, "The Entries list should not be null");
        assertEquals(3, entries.size(), "The Entries list should contain exactly 3 entries");
        assertEquals(Entry.Type.START_GAME, entries.get(0).getType(), "The First entry type should be START_GAME");
        assertEquals(Entry.Type.SETTINGS, entries.get(1).getType(), "The Second entry type should be SETTINGS");
        assertEquals(Entry.Type.EXIT, entries.get(2).getType(), "The Third entry type should be EXIT");
    }

    @Test
    public void testGetNumberEntries() {
        // Act
        int numberEntries = menu.getNumberEntries();

        // Assert
        assertEquals(3, numberEntries, "Number of entries should be 3");
    }

    @Test
    public void testMoveDown() {
        // Act & Assert
        assertEquals(Entry.Type.START_GAME, menu.getCurrentEntry().getType(), "Initial entry should be START_GAME");

        menu.moveDown();
        assertEquals(Entry.Type.SETTINGS, menu.getCurrentEntry().getType(), "After moving down once, entry should be SETTINGS");

        menu.moveDown();
        assertEquals(Entry.Type.EXIT, menu.getCurrentEntry().getType(), "After moving down twice, entry should be EXIT");

        menu.moveDown();
        assertEquals(Entry.Type.START_GAME, menu.getCurrentEntry().getType(), "After moving down thrice, entry should wrap to START_GAME");
    }

    @Test
    public void testMoveUp() {
        // Act & Assert
        assertEquals(Entry.Type.START_GAME, menu.getCurrentEntry().getType(), "Initial entry should be START_GAME");

        menu.moveUp();
        assertEquals(Entry.Type.EXIT, menu.getCurrentEntry().getType(), "After moving up once, entry should wrap to EXIT");

        menu.moveUp();
        assertEquals(Entry.Type.SETTINGS, menu.getCurrentEntry().getType(), "After moving up twice, entry should be SETTINGS");

        menu.moveUp();
        assertEquals(Entry.Type.START_GAME, menu.getCurrentEntry().getType(), "After moving up thrice, entry should wrap to START_GAME");
    }

    @Test
    public void testGetCurrentEntry() {
        // Act & Assert
        assertEquals(Entry.Type.START_GAME, menu.getCurrentEntry().getType(), "Initial current entry should be START_GAME");

        menu.moveDown();
        assertEquals(Entry.Type.SETTINGS, menu.getCurrentEntry().getType(), "After moving down, current entry should be SETTINGS");

        menu.moveUp();
        assertEquals(Entry.Type.START_GAME, menu.getCurrentEntry().getType(), "After moving up, current entry should be START_GAME again");
    }
}
