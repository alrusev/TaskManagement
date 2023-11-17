package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import models.contracts.Feedback;
import models.contracts.Person;
import models.contracts.Story;
import models.enums.Priority;
import models.enums.Size;
import models.enums.StoryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class ChangeFeedbackRatingCommandTests {
    private Repository repository;
    private ChangeFeedbackRatingCommand changeFeedbackRatingCommand;
    Person person;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        changeFeedbackRatingCommand = new ChangeFeedbackRatingCommand(repository);
        Feedback feedback = repository.createFeedback("FeedbackTest", "DescriptionTest", StoryStatus.NEW);
    }

    @Test
    public void execute_Should_ChangeFeedbackRating_When_ValidParameters() {
        // Arrange
        int feedbackId = 1;
        int newRating = 11;

        Feedback feedback = (Feedback) repository.findTaskById(repository.getTasks(), feedbackId);

        // Act
        String result = changeFeedbackRatingCommand.execute(Arrays.asList(String.valueOf(feedbackId),
                String.valueOf(newRating)));

        // Assert
        String expected = String.format("Feedback rating for feedback with ID '%d' updated successfully." +
                " New rating: %s", feedbackId, newRating);
        assertEquals(expected, result);
        assertEquals(newRating, feedback.getRating());
    }

    @Test
    public void execute_Should_ShowExceptionMessage_When_SameRatingAlreadySet() {
        // Arrange
        int feedbackId = 1;
        int newRating = 3;

        Feedback feedback = (Feedback) repository.findTaskById(repository.getTasks(), feedbackId);

        // Act
        String defineFeedback = changeFeedbackRatingCommand.execute(Arrays.asList(String.valueOf(feedbackId), String.valueOf(newRating)));
        String result = changeFeedbackRatingCommand.execute(Arrays.asList(String.valueOf(feedbackId), String.valueOf(newRating)));

        // Assert
        String expected = String.format("The rating is already set to %d!", newRating);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ShowCastClassException_When_FeedbackIsNonexistent() {
        // Arrange
        int storyId = 2;
        int newRating = 3;

        Story story = repository.createStory("TitleTests", "DescriptionDesk", Priority.LOW, Size.MEDIUM, StoryStatus. NOTDONE, person);

        // Act
        String result = changeFeedbackRatingCommand.execute(Arrays.asList(String.valueOf(storyId), String.valueOf(newRating)));

        // Assert
        String expected = String.format("No such feedback with ID '%d'!", storyId);
        assertEquals(expected, result);
    }
}
