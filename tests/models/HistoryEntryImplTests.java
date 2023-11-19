package models;
import models.contracts.HistoryEntry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HistoryEntryImplTests {

    @Test
    public void constructor_ValidDescription_Success() {
        // Arrange
        String validDescription = "This is a valid description.";

        // Act
        HistoryEntry historyEntry = new HistoryEntryImpl(validDescription);

        // Assert
        assertEquals(validDescription, historyEntry.getDescription());
    }

    @Test
    public void constructor_DescriptionTooShort_ThrowsIllegalArgumentException() {
        // Arrange
        String shortDescription = "Short";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new HistoryEntryImpl(shortDescription));
    }

    @Test
    public void constructor_DescriptionTooLong_ThrowsIllegalArgumentException() {
        // Arrange
        String longDescription = "This is a very long description that exceeds the maximum allowed length of 500 characters. " +
                "This is done intentionally to test the validation logic of the setDescription method in HistoryEntryImpl." +
                "This is a very long description that exceeds the maximum allowed length of 500 characters." +
                "This is done intentionally to test the validation logic of the setDescription method in HistoryEntryImpl." +
                "This is a very long description that exceeds the maximum allowed length of 500 characters. " +
                "This is done intentionally to test the validation logic of the setDescription method in HistoryEntryImpl.";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new HistoryEntryImpl(longDescription));
    }

    @Test
    public void toString_ReturnsFormattedString() {
        // Arrange
        String description = "Test description";
        HistoryEntry historyEntry = new HistoryEntryImpl(description);

        // Act
        String result = historyEntry.toString();

        // Assert
        assertEquals("History Entry: " + description, result);
    }
}
