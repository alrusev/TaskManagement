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
import models.enums.StoryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChangeBugSeverityCommandTests {

    private Repository repository;
    private ChangeBugSeverityCommand changeBugSeverityCommand;
    private Person person;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        changeBugSeverityCommand = new ChangeBugSeverityCommand(repository);
        List<String> stepsToReproduce = Arrays.asList("Step 1", "Step 2", "Step 3");
        Person person = new PersonImpl("TestName");
        Bug bug = repository.createBug("BugTitleTest", "BugDescription", Priority.LOW,
                Severity.CRITICAL, StoryStatus.ACTIVE, person, stepsToReproduce);
    }

    @Test
    public void execute_Should_ChangeBugSeverity_When_ValidParameters() {
        // Act
        int bugId = 1;
        Severity severity = Severity.MAJOR;
        String result = changeBugSeverityCommand.execute(Arrays.asList(String.valueOf(bugId), severity.toString()));

        // Assert
        String expected = String.format("Severity for bug with ID '%d' updated successfully. New severity: %s",
                bugId, severity);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ThrowIllegalArgException_When_InvalidParameters() {
        // Act
        int bugId = 1;
        String invalidSeverity = "InvalidSeverity";

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> changeBugSeverityCommand.execute(Arrays.asList(String.valueOf(bugId), invalidSeverity.toString())));

    }

    @Test
    public void execute_Should_ThrowIllegalArgException_When_AlreadySet() {
        // Arrange
        int bugId = 1;
        Severity setSeverity = Severity.CRITICAL;
        // Act
        String result = changeBugSeverityCommand.execute(Arrays.asList(String.valueOf(bugId), setSeverity.toString()));

        // Assert
        String expected = String.format("The severity of bug with ID '%d' is already set to %s!", bugId, setSeverity);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ThrowCastClassException_When_AlreadySet() {
        // Arrange
        int bugId = 2;
        Severity setSeverity = Severity.CRITICAL;

        Story story = repository.createStory("TitleTests", "DescriptionDesk", Priority.LOW, Size.MEDIUM, StoryStatus. NOTDONE, person);
        // Act
        String result = changeBugSeverityCommand.execute(Arrays.asList(String.valueOf(bugId), setSeverity.toString()));

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
