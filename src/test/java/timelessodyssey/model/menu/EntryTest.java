package timelessodyssey.model.menu;

import org.junit.jupiter.api.Test;
import timelessodyssey.model.Vector;

import static org.junit.jupiter.api.Assertions.*;

class EntryTest {

    @Test
    void testEntryInitialization() {
        // Arrange
        int x = 10;
        int y = 20;
        Entry.Type type = Entry.Type.START_GAME;

        // Act
        Entry entry = new Entry(x, y, type);

        // Assert
        assertNotNull(entry.getPosition(), "Position should not be null");
        assertEquals(new Vector(x, y), entry.getPosition(), "Position should match the initialized values");
        assertEquals(type, entry.getType(), "Type should match the initialized value");
    }

    @Test
    void testGetPosition() {
        // Arrange
        int x = 5;
        int y = 15;
        Entry entry = new Entry(x, y, Entry.Type.SETTINGS);

        // Act
        Vector position = entry.getPosition();

        // Assert
        assertNotNull(position, "Position should not be null");
        assertEquals(5, position.x(), "X coordinate should match");
        assertEquals(15, position.y(), "Y coordinate should match");
    }


    @Test
    void testGetType() {
        // Arrange
        Entry.Type type = Entry.Type.EXIT;
        Entry entry = new Entry(0, 0, type);

        // Act
        Entry.Type resultType = entry.getType();

        // Assert
        assertNotNull(resultType, "Type should not be null");
        assertEquals(type, resultType, "Type should match the initialized value");
    }
}
