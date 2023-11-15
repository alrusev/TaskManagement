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

public class ChangeBugPriorityCommandTests {

    private Repository repository;
    private ChangeBugPriorityCommand changeBugPriorityCommand;
    private Person person;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        changeBugPriorityCommand = new ChangeBugPriorityCommand(repository);
        List<String> stepsToReproduce = Arrays.asList("Step 1", "Step 2", "Step 3");
        Person person = new PersonImpl("TestName");
        Bug bug = repository.createBug("BugTitleTest", "BugDescription", Priority.LOW,
                Severity.CRITICAL, TaskStatus.ACTIVE, person, stepsToReproduce);
    }

    @Test
    public void execute_Should_ChangeBugPriority_When_ValidParameters() {
        // Act
        int bugId = 1;
        Priority newPriority = Priority.MEDIUM;
        String result = changeBugPriorityCommand.execute(Arrays.asList(String.valueOf(bugId), newPriority.toString()));

        // Assert
        String expected = String.format("Priority for bug with ID '%d' updated successfully. New priority: %s",
                bugId, newPriority);
        assertEquals(expected, result);
    }
    @Test
    public void execute_Should_ThrowIllegalArgException_When_InvalidParameters() {
        // Arrange
        int bugId = 1;
        String invalidPriority = "InvalidPriority";

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> changeBugPriorityCommand.execute(Arrays.asList(String.valueOf(bugId), invalidPriority)));
    }

    @Test
    public void execute_Should_ShowIllegalArgException_When_PriorityAlreadySet() {
        // Arrange
        int bugId = 1;
        Priority setPriority = Priority.LOW;
        // Act
        String result = changeBugPriorityCommand.execute(Arrays.asList(String.valueOf(bugId), setPriority.toString()));

        // Assert
        String expected = String.format("The priority of bug with ID '%d' is already set to %s!", bugId, setPriority);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ShowCastClassException_When_AlreadySet() {
        // Arrange
        int bugId = 2;
        Priority newPriority = Priority.MEDIUM;

        Story story = repository.createStory("TitleTests", "DescriptionDesk", Priority.LOW, Size.MEDIUM, TaskStatus. NOTDONE, person);
        // Act
        String result = changeBugPriorityCommand.execute(Arrays.asList(String.valueOf(bugId), newPriority.toString()));

        // Assert
        String expected = String.format("No such Bug with ID '%d'!", bugId);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ThrowIllegalArgException_when_MissingBugId() {
        // Arrange
        int bugId = 2;

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> repository.findTaskById(repository.getTasks(), bugId));

    }


}
