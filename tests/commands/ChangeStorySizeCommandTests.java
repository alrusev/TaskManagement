package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import models.StoryImpl;
import models.contracts.Feedback;
import models.contracts.Person;
import models.contracts.Story;
import models.enums.Priority;
import models.enums.Size;
import models.enums.StoryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChangeStorySizeCommandTests {

    private Repository repository;
    private ChangeStorySizeCommand changeStorySizeCommand;
    private Person person;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        changeStorySizeCommand = new ChangeStorySizeCommand(repository);
        Story story = repository.createStory("TitleATest", "DescriptionTest", Priority.LOW, Size.SMALL,
                StoryStatus.NOTDONE, person);
    }

    @Test
    public void execute_Should_ChangeStorySize_When_ValidParameters() {
        // Arrange
        int storyId = 1;
        Size newSize = Size.MEDIUM;

        Story story = (Story) repository.findTaskById(repository.getTasks(), storyId);

        // Act
        String result = changeStorySizeCommand.execute(Arrays.asList(String.valueOf(storyId), newSize.toString()));

        // Assert
        String expected = String.format("Size for story with ID '%d' updated successfully. New size: %s", storyId, newSize);
        assertEquals(expected, result);
        assertEquals(newSize, story.getSize());
    }

    @Test
    public void execute_Should_ThrowIllegalArgException_When_SizeAlreadySet() {
        // Arrange
        int storyId = 1;
        Size newSize = Size.SMALL;

        StoryImpl story = (StoryImpl) repository.findTaskById(repository.getTasks(), storyId);

        // Act
        String result = changeStorySizeCommand.execute(Arrays.asList(String.valueOf(storyId), newSize.toString()));

        // Assert
        String expected = String.format("The size for story with ID '%d' is already set to %s!", storyId, newSize);
        assertEquals(expected, result);
    }

    @Test
    public void execute_ShouldThrowIllegalArgumentException_When_InvalidStoryType() {
        // Arrange
        int storyId = 1;
        String invalidSize = "Invalid Size";

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> changeStorySizeCommand.execute(Arrays.asList(String.valueOf(storyId), invalidSize)),
                "Expected IllegalArgumentException");
    }

    @Test
    public void execute_Should_ShowCastClassException_When_StoryIsNonexistent() {
        // Arrange
        int storyId = 2;
        Size newSize = Size.SMALL;

        Feedback feedback = repository.createFeedback("FeedbackTest", "DescriptionTest", StoryStatus.NEW);

        // Act
        String result = changeStorySizeCommand.execute(Arrays.asList(String.valueOf(storyId), newSize.toString()));

        // Assert
        String expected = String.format("No such story with ID '%d'!", storyId);
        assertEquals(expected, result);
    }

}
