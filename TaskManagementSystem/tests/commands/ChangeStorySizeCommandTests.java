package commands;

import commands.changecommands.ChangeStorySizeCommand;
import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.contracts.Feedback;
import models.contracts.Story;
import models.enums.Priority;
import models.enums.Size;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChangeStorySizeCommandTests {

    private Repository repository;
    private ChangeStorySizeCommand changeStorySizeCommand;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        changeStorySizeCommand = new ChangeStorySizeCommand(repository);
        Story story = repository.createStory("TitleATest", "DescriptionTest", Priority.LOW, Size.SMALL);
    }

    @Test
    public void execute_Should_ChangeStorySize_When_ValidParameters() {
        // Arrange
        int storyId = 1;
        Size newSize = Size.MEDIUM;

        Story story = repository.findTaskById(storyId,repository.getStories());

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

        // Act & Assert
        assertThrows(IllegalArgumentException.class,()->changeStorySizeCommand.execute(Arrays.asList(String.valueOf(storyId), newSize.toString())));
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

        Feedback feedback = repository.createFeedback("FeedbackTest", "DescriptionTest",2);


        Assertions.assertThrows(NoSuchElementFoundException.class, ()-> changeStorySizeCommand.execute(Arrays.asList(String.valueOf(storyId), newSize.toString())));

    }

}
