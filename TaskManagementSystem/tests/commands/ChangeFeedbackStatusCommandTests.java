package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.InvalidUserInputException;
import exceptions.NoSuchElementFoundException;
import models.contracts.Feedback;
import models.contracts.Story;
import models.enums.FeedbackStatus;
import models.enums.Priority;
import models.enums.Size;
import models.enums.StoryStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChangeFeedbackStatusCommandTests {

    private Repository repository;
    private ChangeFeedbackStatusCommand changeFeedbackStatusCommand;
    private static final String FEEDBACK_STATUS_SUCCESSFULLY_CHANGED = "Status for feedback with ID '%d' " +
            "updated successfully. New status: %s";
    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        changeFeedbackStatusCommand = new ChangeFeedbackStatusCommand(repository);
        Feedback feedback = repository.createFeedback("FeedbackTest", "DescriptionTest",2);
    }

    @Test
    public void execute_Should_ChangeStatus_When_ValidParameters() {
        // Arrange
        int feedbackId = 1;
        FeedbackStatus newStatus = FeedbackStatus.UNSCHEDULED;

        // Act
        String result = changeFeedbackStatusCommand.execute(Arrays.asList(String.valueOf(feedbackId), newStatus.toString()));

        // Assert
        String expected = String.format(FEEDBACK_STATUS_SUCCESSFULLY_CHANGED, feedbackId, newStatus);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ThrowException_When_StatusInvalid() {
        // Arrange
        int feedbackId = 1;
        StoryStatus newStatus = StoryStatus.NOTDONE;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, ()-> changeFeedbackStatusCommand
                .execute(Arrays.asList(String.valueOf(feedbackId), newStatus.toString())));
    }

    @Test
    public void execute_Should_ShowException_When_StatusAlreadySet() {
        // Arrange
        int feedbackId = 1;
        StoryStatus newStatus = StoryStatus.INPROGRESS;


        // Act & Assert
        assertThrows(IllegalArgumentException.class, ()-> changeFeedbackStatusCommand.execute(Arrays.asList(String.valueOf(feedbackId), newStatus.toString())));
    }

    @Test
    public void execute_Should_ThrowsInvalidUserInputException_When_SameStatus() {
        // Arrange
        int feedbackId = 1;
        FeedbackStatus newStatus = FeedbackStatus.NEW;

        // Act and Assert
        assertThrows(InvalidUserInputException.class, () ->
                changeFeedbackStatusCommand.execute(Arrays.asList(String.valueOf(feedbackId), newStatus.toString())));
    }

    @Test
    public void execute_Should_ThrowsIllegalArgumentException_When_InvalidStatus() {
        // Arrange
        int feedbackId = 1;
        String newStatus = "InProgress";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () ->
                changeFeedbackStatusCommand.execute(Arrays.asList(String.valueOf(feedbackId), newStatus)));
    }
    @Test
    public void execute_Should_ShowCastClassException_When_FeedbackIsNonexistent() {
        // Arrange
        int storyId = 2;
        FeedbackStatus newStatus = FeedbackStatus.NEW;


        Story story = repository.createStory("TitleTests", "DescriptionDesk", Priority.LOW, Size.MEDIUM);


        Assertions.assertThrows(NoSuchElementFoundException.class, ()-> changeFeedbackStatusCommand.execute(Arrays.asList(String.valueOf(storyId), newStatus.toString())));

    }
}
