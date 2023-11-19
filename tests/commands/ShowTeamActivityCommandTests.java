package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.contracts.Person;
import models.contracts.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowTeamActivityCommandTests {
    private Repository repository;
    private ShowTeamActivityCommand showTeamActivityCommand;
    private AddPersonToTeamCommand addPersonToTeamCommand;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        showTeamActivityCommand = new ShowTeamActivityCommand(repository);
        addPersonToTeamCommand = new AddPersonToTeamCommand(repository);
    }

    @Test
    public void execute_should_ShowTeamActivity_When_TeamActivityProvided() {
        // Arrange
        String personName = "Ivancho";
        Person person = repository.createPerson(personName);
        String teamName = "TestTeam";
        Team team = repository.createTeam(teamName);
        addPersonToTeamCommand.execute(List.of(personName, teamName));

        //Act
        String result = showTeamActivityCommand.execute(List.of(teamName));

        //Assert
        String expectedOutput = "Team activity for TestTeam - 1. Ivancho added to team TestTeam";
        assertEquals(expectedOutput, result);
    }

    @Test
    public void execute_Should_ThrowNoSuchElementFoundException_When_NoSuchTeamExists() {
        //Arrange
        String teamName = "NonExistentName";

        //Act & Assert
        assertThrows(NoSuchElementFoundException.class, () -> showTeamActivityCommand.execute(List.of(teamName)));
    }

}
