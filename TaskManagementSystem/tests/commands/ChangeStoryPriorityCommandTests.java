package commands;

import commands.changecommands.ChangeStoryPriorityCommand;
import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.contracts.Bug;
import models.contracts.Story;
import models.enums.Priority;
import models.enums.Severity;
import models.enums.Size;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChangeStoryPriorityCommandTests {

    private Repository repository;
    private ChangeStoryPriorityCommand changeStoryPriorityCommand;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        changeStoryPriorityCommand = new ChangeStoryPriorityCommand(repository);
        Story story = repository.createStory("TitleATest", "DescriptionTest", Priority.LOW, Size.SMALL);
    }

    @Test
    public void execute_Should_ChangeStoryPriority_When_ValidParameters() {
        // Arrange
        int storyId = 1;
        Priority newPriority = Priority.MEDIUM;
        String result = changeStoryPriorityCommand.execute(Arrays.asList(String.valueOf(storyId), newPriority.toString()));

        // Act & Assert
        String expected = String.format("Priority for story with ID '%d' updated successfully. New priority: %s",
                storyId, newPriority);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ThrowIllegalArgException_When_InvalidParameters() {
        // Arrange
        int storyID = 1;
        String invalidPriority = "InvalidPriority";

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> changeStoryPriorityCommand.execute(Arrays.asList(String.valueOf(storyID), invalidPriority)));
    }

    @Test
    public void execute_Should_ThrowIllegalArgException_When_PriorityAlreadySet() {
        // Arrange
        int storyID = 1;
        Priority setPriority = Priority.LOW;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, ()->changeStoryPriorityCommand.execute(Arrays.asList(String.valueOf(storyID), setPriority.toString())));
    }

    @Test
    public void execute_Should_ShowCastClassException_When_AlreadySet() {
        // Arrange
        int storyId = 2;
        Priority newPriority = Priority.MEDIUM;

        List<String> stepsToReproduce = Arrays.asList("Step 1", "Step 2", "Step 3");
        Bug bug = repository.createBug("BugTitleTest", "BugDescription", Priority.LOW,
                Severity.CRITICAL, stepsToReproduce);
        // Act
        Assertions.assertThrows(NoSuchElementFoundException.class, ()-> changeStoryPriorityCommand.execute(Arrays.asList(String.valueOf(storyId), newPriority.toString())));

    }

    @Test
    public void execute_Should_ThrowIllegalArgException_when_MissingBugId() {
        // Arrange
        int storyId = 2;

        // Act and Assert
        assertThrows(NoSuchElementFoundException.class,
                () -> repository.findTaskById(storyId,repository.getStories()));

    }


}
