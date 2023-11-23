package commands;

import Utils.TestUtilities;
import commands.addassigncommands.UnassignTaskCommand;
import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.PersonImpl;
import models.contracts.Feedback;
import models.contracts.Person;
import models.contracts.Story;
import models.enums.Priority;
import models.enums.Size;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UnassignTaskTests {
    private final static int EXPECTED_PARAMETERS_COUNT = 1;
    public static final String VALID_NAME = TestUtilities.getString(6);
    public static final String VALID_TITLE = TestUtilities.getString(11);
    public static final String VALID_DESCRIPTION = TestUtilities.getString(11);

    private Repository repository;
    private UnassignTaskCommand command;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        command = new UnassignTaskCommand(repository);
    }
    @Test
    public void execute_Should_Throw_When_ParametersCountInvalid(){
        List<String> parameters = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT +1);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> command.execute(parameters));
    }
    @Test
    public void execute_Should_Throw_When_TaskDoesNotExists(){
        new PersonImpl(VALID_NAME);
        List<String> parameters = List.of("1");
        Assertions.assertThrows(NoSuchElementFoundException.class, ()-> command.execute(parameters));
    }
    @Test
    public void execute_Should_Throw_When_TaskIsFeedback(){
        repository.createPerson(VALID_NAME);
        Feedback feedback = repository.createFeedback(VALID_TITLE,VALID_DESCRIPTION,2);
        List<String> parameters = List.of(String.valueOf(feedback.getId()));
        Assertions.assertThrows(IllegalArgumentException.class, ()-> command.execute(parameters));
    }
    @Test
    public void execute_Should_RemoveTaskFromAssignee_When_ArgumentsAreValid(){
        Person person = repository.createPerson(VALID_NAME);
        Story story = repository.createStory(VALID_TITLE, VALID_DESCRIPTION, Priority.LOW, Size.SMALL);
        List<String> parameters = List.of(String.valueOf(story.getId()));
        person.addTask(story);
        story.assignTask(person);
        command.execute(parameters);
        Assertions.assertEquals(0,person.getTasks().size());
    }
    @Test
    public void execute_Should_RemoveAssigneeFromTask_When_ArgumentsAreValid(){
        Person person = repository.createPerson(VALID_NAME);
        Story story = repository.createStory(VALID_TITLE, VALID_DESCRIPTION, Priority.LOW, Size.SMALL);
        List<String> parameters = List.of(String.valueOf(story.getId()));
        person.addTask(story);
        story.assignTask(person);
        command.execute(parameters);
        Assertions.assertNotEquals(person,story.getAssignee());
    }
}
