package timelessodyssey.model.menu;

import org.junit.jupiter.api.Test;
import timelessodyssey.Game;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest {

    @Test
    void testCreateEntries() {
        // Arrange
        MainMenu mainMenu = new MainMenu();

        // Act
        List<Entry> entries = mainMenu.createEntries();

        // Assert
        assertNotNull(entries, "Entries list should not be null");
        assertEquals(3, entries.size(), "Entries list should contain exactly 3 entries");

        // Verify each entry
        Entry start = entries.get(0);
        Entry settings = entries.get(1);
        Entry exit = entries.get(2);

        int expectedStartX = Game.PIXEL_WIDTH / 2 - (5 / 2) * 3 - (5 / 2) - 2;
        int expectedSettingsX = Game.PIXEL_WIDTH / 2 - (8 / 2) * 3 - (8 / 2);
        int expectedExitX = Game.PIXEL_WIDTH / 2 - (4 / 2) * 3 - (4 / 2);

        assertEquals(expectedStartX, start.getPosition().x(), "Start entry X position should match");
        assertEquals(55, start.getPosition().y(), "Start entry Y position should match");
        assertEquals(Entry.Type.START_GAME, start.getType(), "Start entry type should be START_GAME");

        assertEquals(expectedSettingsX, settings.getPosition().x(), "Settings entry X position should match");
        assertEquals(65, settings.getPosition().y(), "Settings entry Y position should match");
        assertEquals(Entry.Type.SETTINGS, settings.getType(), "Settings entry type should be SETTINGS");

        assertEquals(expectedExitX, exit.getPosition().x(), "Exit entry X position should match");
        assertEquals(75, exit.getPosition().y(), "Exit entry Y position should match");
        assertEquals(Entry.Type.EXIT, exit.getType(), "Exit entry type should be EXIT");
    }
}
