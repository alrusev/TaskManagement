package commands;

import Utils.TestUtilities;
import commands.contracts.Command;
import commands.createcommands.CreateBoardCommand;
import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import exceptions.TheNameIsNotUniqueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateBoardInTeamCommandTests {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String INVALID_NAME = TestUtilities.getString(4);
    public static final String VALID_NAME = TestUtilities.getString(6);
    public static final String VALID_TEAM_NAME = TestUtilities.getString(6);

    private Command command;
    private Repository repository;

    @BeforeEach
    public void before() {
        this.repository = new RepositoryImpl();
        this.command = new CreateBoardCommand(repository);
    }

    @Test
    public void should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> parameters = TestUtilities.getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);
        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_NameIsInvalidLength() {
        repository.createTeam(VALID_TEAM_NAME);
        List<String> parameters = List.of(INVALID_NAME, VALID_TEAM_NAME);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_NameIsNotUnique() {
        repository.createTeam(VALID_TEAM_NAME);
        List<String> parameters = List.of(VALID_NAME, VALID_TEAM_NAME);
        command.execute(parameters);

        assertThrows(TheNameIsNotUniqueException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_AddTeam_When_ArgumentAreValid() {
        repository.createTeam(VALID_TEAM_NAME);
        List<String> parameters = List.of(VALID_NAME, VALID_TEAM_NAME);
        command.execute(parameters);
        assertEquals(1, repository.getBoards().size());
    }

    @Test
    public void should_AddBoardToTeam_When_ArgumentAreValid() {
        repository.createTeam(VALID_TEAM_NAME);
        List<String> parameters = List.of(VALID_NAME, VALID_TEAM_NAME);
        command.execute(parameters);
        assertEquals(1, repository.findElementByName
                (VALID_TEAM_NAME, repository.getTeams(), "Team").getBoards().size());
    }
    @Test
    public void should_ThrowException_When_ThereIsNoSuchTeam() {
        List<String> parameters = List.of(VALID_NAME, VALID_TEAM_NAME);

        assertThrows(NoSuchElementFoundException.class, () -> command.execute(parameters));
    }

}
