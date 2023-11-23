package commands;

import Utils.TestUtilities;
import commands.contracts.Command;
import commands.createcommands.CreatePersonCommand;
import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.TheNameIsNotUniqueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreatePersonCommandTests {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String INVALID_NAME = TestUtilities.getString(4);
    public static final String VALID_NAME = TestUtilities.getString(6);

    private Command command;
    private Repository repository;

    @BeforeEach
    public void before() {
        this.repository = new RepositoryImpl();
        this.command = new CreatePersonCommand(repository);
    }
    @Test
    public void should_ThrowException_When_ArgumentCountDifferentThanExpected(){
        List<String> parameters = TestUtilities.getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);
        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    public void should_ThrowException_When_NameIsInvalidLength(){
        List<String> parameters = List.of(INVALID_NAME);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    public void should_ThrowException_When_NameIsNotUnique(){
        List<String> parameters = List.of(VALID_NAME);
        command.execute(parameters);

        assertThrows(TheNameIsNotUniqueException.class, () -> command.execute(parameters));
    }
    @Test
    public void should_AddTeam_When_ArgumentAreValid(){
        List<String> parameters = List.of(VALID_NAME);
        command.execute(parameters);

        assertEquals(1,repository.getPeople().size());
    }
}
