package models;

import core.RepositoryImpl;
import core.contracts.Repository;
import models.contracts.Person;
import models.enums.BugStatus;
import models.enums.Priority;
import models.enums.Severity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BugImplTests {
    private BugImpl bug;
    private Repository repository;

    @BeforeEach
    public void setUp() {
        int bugId = 1;
        repository = new RepositoryImpl();
        String title = "BugTitleTest";
        String description = "BugDescription";
        Priority priority = Priority.HIGH;
        Severity severity = Severity.MAJOR;
        List<String> stepsToReproduce = Arrays.asList("Step 1", "Step 2", "Step 3");
        bug = new BugImpl(title, description, priority, severity, stepsToReproduce, bugId);
    }

    @Test
    public void constructor_ShouldCreateBug_When_ValidDescriptionProvided() {
        // Assert
        assertEquals("BugTitleTest", bug.getTitle());
        assertEquals("BugDescription", bug.getDescription());
        assertEquals(Priority.HIGH, bug.getPriority());
        assertEquals(Severity.MAJOR, bug.getSeverity());
        assertEquals(BugStatus.ACTIVE, bug.getBugStatus());
        assertNotNull(bug.getStepsToReproduce());
        assertEquals(3, bug.getStepsToReproduce().size());
        assertEquals("Step 1", bug.getStepsToReproduce().get(0));
    }

    @Test
    public void updatePriority_Should_updatePriority_When_ValidPriorityProvided() {
        // Act
        bug.updatePriority(Priority.MEDIUM);

        // Assert
        assertEquals(Priority.MEDIUM, bug.getPriority());
        assertEquals(1, bug.getHistory().size());
    }

    @Test
    public void updatePriority_Should_NotUpdatePriority_When_SamePriorityProvided() {
        // Act
        bug.updatePriority(Priority.HIGH);

        // Assert
        assertEquals(Priority.HIGH, bug.getPriority());
        assertEquals(0, bug.getHistory().size());
    }

    @Test
    public void assignTo_Should_AssignTo_When_ValidPersonProvided() {
        // Arrange
        Person newAssignee = new PersonImpl("NewAssignee");

        // Act
        bug.assignTo(newAssignee);

        // Assert
        assertEquals(newAssignee, bug.getAssignee());
        assertEquals(1, bug.getHistory().size());
        assertEquals("Bug with ID 1 assigned to NewAssignee", newAssignee.getActivityHistory().get(0));
    }

    @Test
    public void unassign_Should_NonUnassign_When_UnassignedProvided() {
        // Act
        bug.unAssign();

        // Assert
        assertNull(bug.getAssignee());
        assertEquals(1, bug.getHistory().size());
    }

    @Test
    public void markAsDone_Should_MarkBugAsDone_When_MarkedDone() {
        // Act
        bug.changeBugStatus();

        // Assert
        assertEquals(BugStatus.DONE, bug.getBugStatus());
        assertEquals(1, bug.getHistory().size());
    }

    @Test
    public void markAsDone_Should_NotUpdate_WhenAlreadyMarked() {
        // Arrange
        bug.setBugStatus(BugStatus.DONE);

        // Act
        bug.changeBugStatus();

        // Assert
        assertEquals(BugStatus.ACTIVE, bug.getBugStatus());
        assertEquals(1, bug.getHistory().size());
    }

    @Test
    public void reopenBug_Should_ReopenAndMarkAsActive_whenMarkedAsActive() {
        // Arrange
        bug.setBugStatus(BugStatus.DONE);

        // Act
        bug.changeBugStatus();

        // Assert
        assertEquals(BugStatus.ACTIVE, bug.getBugStatus());
        assertEquals(1, bug.getHistory().size());
    }

    @Test
    public void reopenBug_Should_NotReopenAndMarkAsActive_whenReopenedAlready() {
        // Act
        bug.changeBugStatus();

        // Assert
        assertEquals(BugStatus.DONE, bug.getBugStatus());
        assertEquals(1, bug.getHistory().size());
    }

    @Test
    public void updateSeverity_Should_UpdateSeverity_When_ValidSeverityProvided() {
        // Act
        bug.updateSeverity(Severity.MINOR);

        // Assert
        assertEquals(Severity.MINOR, bug.getSeverity());
        assertEquals(1, bug.getHistory().size());
    }

    @Test
    public void updateSeverity_Should_NotUpdateSeverity_When_SameSeverityProvided() {
        // Act
        bug.updateSeverity(Severity.MAJOR);

        // Assert
        assertEquals(Severity.MAJOR, bug.getSeverity());
        assertEquals(0, bug.getHistory().size());
    }
}
