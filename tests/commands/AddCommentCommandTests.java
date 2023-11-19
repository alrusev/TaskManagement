package commands;

import Utils.TestUtilities;
import commands.contracts.Command;
import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.contracts.Feedback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddCommentCommandTests {
    public static final String VALID_TITLE = TestUtilities.getString(11);
    public static final String VALID_DESCRIPTION = TestUtilities.getString(11);
    public static final String VALID_CONTENT = TestUtilities.getString(6);
    public static final String VALID_AUTHOR = TestUtilities.getString(6);
    private Repository repository;
    private Command command;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        command = new AddCommentCommand(repository);
    }

    @Test
    public void execute_Should_AddCommentToTask_When_ValidParametersProvided() {
        Feedback feedback = repository.createFeedback(VALID_TITLE, VALID_DESCRIPTION);
        List<String> parameters = List.of(String.valueOf(feedback.getId()), VALID_AUTHOR, VALID_CONTENT);

        command.execute(parameters);
        Assertions.assertEquals(1, feedback.getComments().size());
    }


    @Test
    public void execute_Should_ThrowException_When_LessArgumentsProvided() {

        assertThrows(IllegalArgumentException.class, () -> command.execute(List.of("x")));
    }

    @Test
    public void execute_Should_ThrowException_When_NoSuchTaskByIdFound() {
        List<String> parameters = List.of("1", VALID_AUTHOR, VALID_CONTENT);


        assertThrows(NoSuchElementFoundException.class, () -> command.execute(parameters));
    }

}
