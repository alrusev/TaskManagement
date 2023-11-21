package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.contracts.Bug;
import models.contracts.Story;
import models.enums.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeBugStatusCommandTests {

    private Repository repository;
    private ChangeBugStatusCommand changeBugStatusCommand;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        changeBugStatusCommand = new ChangeBugStatusCommand(repository);
        List<String> stepsToReproduce = Arrays.asList("Step 1", "Step 2", "Step 3");
        Bug bug = repository.createBug("BugTitleTest", "BugDescription", Priority.LOW,
                Severity.CRITICAL,  stepsToReproduce);
    }

    @Test
    public void execute_Should_ChangeStatusToActive_When_ValidParameters() {
        // Arrange
        int bugId = 1;
        BugStatus newStatus = BugStatus.ACTIVE;

        // Act
        changeBugStatusCommand.execute(Arrays.asList(String.valueOf(bugId), "done"));
        String result = changeBugStatusCommand.execute(Arrays.asList(String.valueOf(bugId), newStatus.toString()));


        // Assert
        String expected = String.format("Bug with ID '%d' reopened and marked as active.", bugId);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ChangeStatusToDone_When_ValidParameters() {
        // Arrange
        int bugId = 1;
        StoryStatus newStatus = StoryStatus.DONE;

        // Act
        String result = changeBugStatusCommand.execute(Arrays.asList(String.valueOf(bugId), newStatus.toString()));

        // Assert
        String expected = String.format("Bug with ID '%d' marked as Done", bugId);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ThrowCastClassException_When_BugIsNonexistent() {
        // Arrange
        int bugId = 2;
        StoryStatus newStatus = StoryStatus.DONE;

        Story story = repository.createStory("TitleTests", "DescriptionDesk", Priority.LOW, Size.MEDIUM);

        Assertions.assertThrows(NoSuchElementFoundException.class, ()-> changeBugStatusCommand.execute(Arrays.asList(String.valueOf(bugId), newStatus.toString())));

    }

}
