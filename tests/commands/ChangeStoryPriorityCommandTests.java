package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import models.PersonImpl;
import models.contracts.Bug;
import models.contracts.Person;
import models.contracts.Story;
import models.enums.Priority;
import models.enums.Severity;
import models.enums.Size;
import models.enums.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChangeStoryPriorityCommandTests {

    private Repository repository;
    private ChangeStoryPriorityCommand changeStoryPriorityCommand;
    private Person person;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        changeStoryPriorityCommand = new ChangeStoryPriorityCommand(repository);
        Story story = repository.createStory("TitleATest", "DescriptionTest", Priority.LOW, Size.SMALL,
                TaskStatus.NOTDONE, person);
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
        // Act
        String result = changeStoryPriorityCommand.execute(Arrays.asList(String.valueOf(storyID), setPriority.toString()));

        // Assert
        String expected = String.format("The priority of story with ID '%d' is already set to %s!", storyID, setPriority);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ShowCastClassException_When_AlreadySet() {
        // Arrange
        int storyId = 2;
        Priority newPriority = Priority.MEDIUM;

        List<String> stepsToReproduce = Arrays.asList("Step 1", "Step 2", "Step 3");
        Person person = new PersonImpl("TestName");
        Bug bug = repository.createBug("BugTitleTest", "BugDescription", Priority.LOW,
                Severity.CRITICAL, TaskStatus.ACTIVE, person, stepsToReproduce);
        // Act
        String result = changeStoryPriorityCommand.execute(Arrays.asList(String.valueOf(storyId), newPriority.toString()));

        // Assert
        String expected = String.format("No such story with ID '%d'!", storyId);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ThrowIllegalArgException_when_MissingBugId() {
        // Arrange
        int storyId = 2;

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> repository.findTaskById(repository.getTasks(), storyId));

    }


}
