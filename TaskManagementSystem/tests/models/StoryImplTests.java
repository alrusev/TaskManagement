package models;

import models.contracts.Person;
import models.enums.Priority;
import models.enums.Size;
import models.enums.StoryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StoryImplTests {
    private StoryImpl story;

    @BeforeEach
    public void setUp() {
        int storyId = 1;
        String title = "StoryTitle";
        String description = "StoryDescription";
        Priority priority = Priority.MEDIUM;
        Size size = Size.LARGE;

        story = new StoryImpl(storyId, title, description, priority, size);
    }

    @Test
    public void updatePriority_Should_updatePriority_When_ValidPriorityProvided() {
        // Act
        story.updatePriority(Priority.HIGH);

        // Assert
        assertEquals(Priority.HIGH, story.getPriority());
        assertEquals(1, story.getHistory().size());
    }

    @Test
    public void updatePriority_Should_NotUpdatePriority_When_SamePriorityProvided() {
        // Act
        story.updatePriority(Priority.MEDIUM);

        // Assert
        assertEquals(Priority.MEDIUM, story.getPriority());
        assertEquals(0, story.getHistory().size());
    }

    @Test
    public void updateSize_Should_UpdateSize_When_ValidSizeProvided() {
        // Act
        story.updateSize(Size.SMALL);

        // Assert
        assertEquals(Size.SMALL, story.getSize());
        assertEquals(1, story.getHistory().size());
    }

    @Test
    public void updateSize_Should_NotUpdateSize_When_SameSizeProvided() {
        // Act
        story.updateSize(Size.LARGE);

        // Assert
        assertEquals(Size.LARGE, story.getSize());
        assertEquals(0, story.getHistory().size());
    }

    @Test
    public void assignTo_Should_AssignTo_When_ValidPersonProvided() {
        // Arrange
        Person newAssignee = new PersonImpl("NewAssignee");

        // Act
        story.assignTask(newAssignee);

        // Assert
        assertEquals(newAssignee, story.getAssignee());
        assertEquals(1, story.getHistory().size());
    }

    @Test
    public void unassign_Should_NonUnassign_When_NoAssigneProvided() {
        // Arrange
        story.assignTask(new PersonImpl("Assignee"));

        // Act
        story.unAssignTask();

        // Assert
        assertEquals(story.getAssignee().getName(),"No assignee");
        assertEquals(2, story.getHistory().size());
    }

    @Test
    public void unassign_Should_NonUnassign_When_UnassignedProvided() {
        // Act
        story.unAssignTask();

        // Assert
        assertEquals(story.getAssignee().getName(),"No assignee");
        assertEquals(1, story.getHistory().size());
    }

    @Test
    public void setStoryStatus_Should_ChangeStatus_When_ValidStatusProvided() {
        // Act
        story.setStoryStatus(StoryStatus.DONE);

        // Assert
        assertEquals(StoryStatus.DONE, story.getStoryStatus());
        assertEquals(1, story.getHistory().size());
    }

    @Test
    public void getStoryStatus_Should_ProvideDefaultStatus_When_Initialized() {
        // Assert
        assertEquals(StoryStatus.NOTDONE, story.getStoryStatus());
    }
}
