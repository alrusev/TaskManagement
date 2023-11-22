package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.InvalidUserInputException;
import models.contracts.Feedback;
import models.contracts.Story;
import models.enums.Priority;
import models.enums.Size;
import models.enums.StoryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChangeStoryStatusCommandTests {

    private Repository repository;
    private ChangeStoryStatusCommand changeStoryStatusCommand;
    private static final String STORY_STATUS_SUCCESSFULLY_CHANGED = "Status for story with ID '%d' updated successfully. New status: %s";


    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        changeStoryStatusCommand = new ChangeStoryStatusCommand(repository);
        Story story = repository.createStory("TitleATest", "DescriptionTest", Priority.LOW, Size.SMALL);
    }

    @Test
    public void execute_Should_ChangeStatus_When_ValidParameters() {
        // Arrange
        int storyId = 1;
        StoryStatus newStatus = StoryStatus.DONE;

        // Act
        String result = changeStoryStatusCommand.execute(Arrays.asList(String.valueOf(storyId), newStatus.toString()));

        // Assert
        String expected = String.format(STORY_STATUS_SUCCESSFULLY_CHANGED, storyId, newStatus);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ThrowsIllegalArgumentException_When_InvalidStatus() {
        // Arrange
        int feedbackId = 1;
        String newStatus = "Active";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () ->
                changeStoryStatusCommand.execute(Arrays.asList(String.valueOf(feedbackId), newStatus)));
    }

    @Test
    public void execute_Should_ShowException_When_StatusAlreadySet() {
        // Arrange
        int feedbackId = 1;
        StoryStatus newStatus = StoryStatus.DONE;

        //Act
        String setNewStatus = changeStoryStatusCommand.execute(Arrays.asList(String.valueOf(feedbackId), newStatus.toString()));

        // Assert
        assertThrows(InvalidUserInputException.class, ()->changeStoryStatusCommand.execute(Arrays.asList(String.valueOf(feedbackId), newStatus.toString())));
    }

    @Test
    public void execute_Should_ShowCastClassException_When_FeedbackIsNonexistent() {
        // Arrange
        int storyId = 2;
        StoryStatus newStatus = StoryStatus.INPROGRESS;


        Feedback feedback = repository.createFeedback("FeedbackTest", "DescriptionTest",2);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, ()->changeStoryStatusCommand.execute(Arrays.asList(String.valueOf(storyId), newStatus.toString())));
    }

}
