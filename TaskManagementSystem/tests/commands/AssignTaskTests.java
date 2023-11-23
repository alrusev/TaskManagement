package commands;

import Utils.TestUtilities;
import commands.addassigncommands.AssignTaskCommand;
import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.FeedbackImpl;
import models.PersonImpl;
import models.contracts.*;
import models.enums.Priority;
import models.enums.Size;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AssignTaskTests {
    private final static int EXPECTED_PARAMETERS_COUNT = 2;
    public static final String VALID_NAME = TestUtilities.getString(6);
    public static final String VALID_TITLE = TestUtilities.getString(11);
    public static final String VALID_DESCRIPTION = TestUtilities.getString(11);

    private Repository repository;
    private AssignTaskCommand command;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        command = new AssignTaskCommand(repository);
    }
    @Test
    public void execute_Should_Throw_When_ParametersCountInvalid(){
        List<String> parameters = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT +1);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> command.execute(parameters));
    }
    @Test
    public void execute_Should_Throw_When_TaskDoesNotExists(){
        new PersonImpl(VALID_NAME);
        List<String> parameters = List.of("1",VALID_NAME);
        Assertions.assertThrows(NoSuchElementFoundException.class, ()-> command.execute(parameters));
    }
    @Test
    public void execute_Should_Throw_When_PersonDoesNotExists(){
        new FeedbackImpl(1,VALID_TITLE,VALID_DESCRIPTION,2);
        List<String> parameters = List.of("1",VALID_NAME);
        Assertions.assertThrows(NoSuchElementFoundException.class, ()-> command.execute(parameters));
    }
    @Test
    public void execute_Should_Throw_When_TaskIsFeedback(){
        repository.createPerson(VALID_NAME);
        Feedback feedback = repository.createFeedback(VALID_TITLE,VALID_DESCRIPTION,2);
        List<String> parameters = List.of(String.valueOf(feedback.getId()),VALID_NAME);
        Assertions.assertThrows(IllegalArgumentException.class, ()-> command.execute(parameters));
    }
    @Test
    public void execute_Should_AddTaskToAssignee_When_ArgumentsAreValid(){
        Person person = repository.createPerson(VALID_NAME);
        Team team = repository.createTeam("Team_Name");
        Board board = repository.createBoard("Valid_Board",team);
        Story story = repository.createStory(VALID_TITLE, VALID_DESCRIPTION, Priority.LOW, Size.SMALL);
        board.addTaskToBoard(story);
        team.addPersonToTeam(person);
        List<String> parameters = List.of(String.valueOf(story.getId()),VALID_NAME);
        command.execute(parameters);
        Assertions.assertEquals(1,person.getTasks().size());
    }
    @Test
    public void execute_Should_AddAssigneeToTask_When_ArgumentsAreValid(){
        Person person = repository.createPerson(VALID_NAME);
        Team team = repository.createTeam("Team_Name");
        Board board = repository.createBoard("Valid_Board",team);
        Story story = repository.createStory(VALID_TITLE, VALID_DESCRIPTION, Priority.LOW, Size.SMALL);
        board.addTaskToBoard(story);
        team.addPersonToTeam(person);
        List<String> parameters = List.of(String.valueOf(story.getId()),VALID_NAME);
        story.unAssignTask();
        command.execute(parameters);
        Assertions.assertEquals(person.getName(),story.getAssignee().getName());
    }

}

