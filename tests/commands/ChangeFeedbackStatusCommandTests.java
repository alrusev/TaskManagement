package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import models.contracts.Feedback;
import models.contracts.Person;
import models.contracts.Story;
import models.enums.FeedbackStatus;
import models.enums.Priority;
import models.enums.Size;
import models.enums.StoryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChangeFeedbackStatusCommandTests {

    private Repository repository;
    Person person;
    private ChangeFeedbackStatusCommand changeFeedbackStatusCommand;
    private static final String FEEDBACK_STATUS_SUCCESSFULLY_CHANGED = "Status for feedback with ID '%d' " +
            "updated successfully. New status: %s";
    private static final String FEEDBACK_STATUS_ALREADY_SET = "The status of a feedback with id '%d' is " +
            "already set to %s";
    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        changeFeedbackStatusCommand = new ChangeFeedbackStatusCommand(repository);
        Feedback feedback = repository.createFeedback("FeedbackTest", "DescriptionTest");
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
    public void execute_Should_ShowCastClassException_When_FeedbackIsNonexistent() {
        // Arrange
        int storyId = 2;
        FeedbackStatus newStatus = FeedbackStatus.UNSCHEDULED;


        Story story = repository.createStory("TitleTests", "DescriptionDesk", Priority.LOW, Size.MEDIUM, person);

        // Act
        String result = changeFeedbackStatusCommand.execute(Arrays.asList(String.valueOf(storyId), newStatus.toString()));

        // Assert
        String expected = String.format("No such Feedback with ID '%d'", storyId);
        assertEquals(expected, result);
    }
}
