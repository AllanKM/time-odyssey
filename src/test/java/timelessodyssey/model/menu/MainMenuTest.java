package timelessodyssey.model.menu;

import org.junit.jupiter.api.Test;
import timelessodyssey.Game;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest {

    @Test
    void testCreateEntries() {
        MainMenu mainMenu = new MainMenu();
        List<Entry> entries = mainMenu.createEntries();
        assertNotNull(entries, "Entries list initialized should not be null");
        assertEquals(3, entries.size(), "Entries list initialized should contain exactly 3 entries");

        Entry start = entries.get(0);
        Entry settings = entries.get(1);
        Entry exit = entries.get(2);

        int expectedStartX = Game.PIXEL_WIDTH / 2 - (5 / 2) * 3 - (5 / 2) - 2;
        int expectedSettingsX = Game.PIXEL_WIDTH / 2 - (8 / 2) * 3 - (8 / 2);
        int expectedExitX = Game.PIXEL_WIDTH / 2 - (4 / 2) * 3 - (4 / 2);

        assertEquals(expectedStartX, start.getPosition().x(), "The Start entry X position should match");
        assertEquals(55, start.getPosition().y(), "The Start entry Y position should match");
        assertEquals(Entry.Type.START_GAME, start.getType(), "The Start entry type should be START_GAME");

        assertEquals(expectedSettingsX, settings.getPosition().x(), "The Settings entry X position should match");
        assertEquals(65, settings.getPosition().y(), "The Settings entry Y position should match");
        assertEquals(Entry.Type.SETTINGS, settings.getType(), "The Settings entry type should be SETTINGS");

        assertEquals(expectedExitX, exit.getPosition().x(), "The Exit entry X position should match");
        assertEquals(75, exit.getPosition().y(), "The Exit entry Y position should match");
        assertEquals(Entry.Type.EXIT, exit.getType(), "The Exit entry type should be EXIT");
    }
}
