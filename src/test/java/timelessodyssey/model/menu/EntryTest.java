package timelessodyssey.model.menu;

import org.junit.jupiter.api.Test;
import timelessodyssey.model.Vector;

import static org.junit.jupiter.api.Assertions.*;

class EntryTest {

    @Test
    void testEntryInitialization() {
        int x = 10;
        int y = 20;
        Entry.Type type = Entry.Type.START_GAME;
        Entry entry = new Entry(x, y, type);

        assertNotNull(entry.getPosition(), "Position in the initialization not null");
        assertEquals(new Vector(x, y), entry.getPosition(), "Position should exist and match the initialized value");
        assertEquals(type, entry.getType(), "Type should match the initialized value");
    }

    @Test
    void testGetPosition() {
        int x = 5;
        int y = 15;
        Entry entry = new Entry(x, y, Entry.Type.SETTINGS);
        Vector position = entry.getPosition();
        assertNotNull(position, "Position in the initialization not null");
        assertEquals(5, position.x(), "X coordinate initialized should match");
        assertEquals(15, position.y(), "Y coordinate initialized should match");
    }


    @Test
    void testGetType() {
        Entry.Type type = Entry.Type.EXIT;
        Entry entry = new Entry(0, 0, type);
        Entry.Type resultType = entry.getType();
        assertNotNull(resultType, "Type initialized should not be null");
        assertEquals(type, resultType, "Type should match the initialized value");
    }
}
