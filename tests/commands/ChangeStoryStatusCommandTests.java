package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import models.contracts.Feedback;
import models.contracts.Person;
import models.contracts.Story;
import models.enums.Priority;
import models.enums.Size;
import models.enums.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeStoryStatusCommandTests {

    private Repository repository;
    private ChangeStoryStatusCommand changeStoryStatusCommand;
    private Person person;
    private static final String STORY_STATUS_ERROR_MESSAGE = "The status of a story can be - NOT DONE, IN PROGRESS or DONE!";
    private static final String STORY_STATUS_ALREADY_SET = "The status of story with ID '%d' is already set to %s";
    private static final String STORY_STATUS_SUCCESSFULLY_CHANGED = "Status for story with ID '%d' updated successfully. New status: %s";


    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        changeStoryStatusCommand = new ChangeStoryStatusCommand(repository);
        Story story = repository.createStory("TitleATest", "DescriptionTest", Priority.LOW, Size.SMALL,
                TaskStatus.INPROGRESS, person);
    }

    @Test
    public void execute_Should_ChangeStatus_When_ValidParameters() {
        // Arrange
        int storyId = 1;
        TaskStatus newStatus = TaskStatus.DONE;

        // Act
        String result = changeStoryStatusCommand.execute(Arrays.asList(String.valueOf(storyId), newStatus.toString()));

        // Assert
        String expected = String.format(STORY_STATUS_SUCCESSFULLY_CHANGED, storyId, newStatus);
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ThrowException_When_StatusInvalid() {
        // Arrange
        int storyId = 1;
        TaskStatus newStatus = TaskStatus.NEW;
        //Act
        String result = changeStoryStatusCommand
                .execute(Arrays.asList(String.valueOf(storyId), newStatus.toString()));

        //Assert
        String expected = STORY_STATUS_ERROR_MESSAGE;
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ShowException_When_StatusAlreadySet() {
        // Arrange
        int feedbackId = 1;
        TaskStatus newStatus = TaskStatus.DONE;

        //Act
        String setNewStatus = changeStoryStatusCommand.execute(Arrays.asList(String.valueOf(feedbackId), newStatus.toString()));
        String result = changeStoryStatusCommand.execute(Arrays.asList(String.valueOf(feedbackId), newStatus.toString()));

        String expected = String.format(STORY_STATUS_ALREADY_SET, feedbackId, newStatus);


        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void execute_Should_ShowCastClassException_When_FeedbackIsNonexistent() {
        // Arrange
        int storyId = 2;
        TaskStatus newStatus = TaskStatus.NEW;


        Feedback feedback = repository.createFeedback("FeedbackTest", "DescriptionTest", TaskStatus.NEW);

        // Act
        String result = changeStoryStatusCommand.execute(Arrays.asList(String.valueOf(storyId), newStatus.toString()));

        // Assert
        String expected = String.format("No such story with ID '%d'", storyId);
        assertEquals(expected, result);
    }

}
