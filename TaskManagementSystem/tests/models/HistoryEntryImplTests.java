package models;
import Utils.TestUtilities;
import models.contracts.HistoryEntry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HistoryEntryImplTests {
    private static final String INVALID_DESCRIPTION= TestUtilities.getString(501);

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
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new HistoryEntryImpl(INVALID_DESCRIPTION));
    }

    @Test
    public void toString_ReturnsFormattedString() {
        // Arrange
        String description = "Test description";
        HistoryEntry historyEntry = new HistoryEntryImpl(description);

        // Act
        String result = historyEntry.toString();

        // Assert
        assertEquals("### " + description, result);
    }
}
