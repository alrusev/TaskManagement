package models;

import Utils.TestUtilities;
import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.contracts.Feedback;
import models.contracts.Person;
import models.contracts.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PersonImplTests {
    public static final String INVALID_NAME = TestUtilities.getString(4);
    public static final String VALID_NAME = TestUtilities.getString(6);
    public static final String VALID_TITLE = TestUtilities.getString(11);
    public static final String VALID_DESCRIPTION = TestUtilities.getString(11);
    public static final int VALID_RATING = 2;
    Person person;
    Repository repository;

    @BeforeEach
    public void before() {
        person = new PersonImpl(VALID_NAME);
        repository = new RepositoryImpl();
    }

    @Test
    public void constructor_Should_CreateCollections() {
        Assertions.assertAll(
                () -> Assertions.assertNotNull(person.getTasks()),
                () -> Assertions.assertNotNull(person.getActivityHistory())
        );
    }

    @Test
    public void getTasks_Should_ReturnCopy() {
        List<Task> list = person.getTasks();
        Assertions.assertNotSame(list, person.getTasks());
    }

    @Test
    public void getActivityHistory_Should_ReturnCopy() {
        List<String> list = person.getActivityHistory();
        Assertions.assertNotSame(list, person.getActivityHistory());
    }

    @Test
    public void constructor_Should_ThrowException_When_NameOutOfBounds() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BoardImpl(INVALID_NAME));
    }

    @Test
    public void constructor_Should_CreateTeam_When_NameInBounds() {
        Assertions.assertEquals(VALID_NAME, person.getName());
    }

    @Test
    public void removeTaskFromBoard_Should_ThrowException_When_TaskDoesNotExist() {
        Feedback feedback = repository.createFeedback(VALID_TITLE, VALID_DESCRIPTION,VALID_RATING);
        Assertions.assertThrows(NoSuchElementFoundException.class,
                () -> person.removeTask(feedback));
    }

    @Test
    public void removeTaskFromTasks_Should_RemoveTasks_When_TaskExist() {
        Feedback feedback = repository.createFeedback(VALID_TITLE, VALID_DESCRIPTION,VALID_RATING);
        person.addTask(feedback);
        person.removeTask(feedback);
        Assertions.assertEquals(0, person.getTasks().size());
    }

    @Test
    public void addTask_Should_AddTask() {
        Feedback feedback = repository.createFeedback(VALID_TITLE, VALID_DESCRIPTION,VALID_RATING);
        person.addTask(feedback);
        Assertions.assertEquals(1, person.getTasks().size());
    }

    @Test
    public void addToActivityHistory_Should_addToActivityHistory() {
        person.addToActivityHistory("test");
        Assertions.assertEquals(1, person.getActivityHistory().size());
    }
}
