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

public class ShowPersonActivityCommandTests {
    private Repository repository;
    private ShowPersonActivityCommand showPersonActivityCommand;
    private AddPersonToTeamCommand addPersonToTeamCommand;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        showPersonActivityCommand = new ShowPersonActivityCommand(repository);
        addPersonToTeamCommand = new AddPersonToTeamCommand(repository);
    }

    @Test
    public void execute_should_ShowBoardActivity_When_BoardActivityProvided() {
        // Arrange
        String personName = "Ivancho";
        Person person = repository.createPerson(personName);
        String teamName = "TestTeam";
        Team team = repository.createTeam(teamName);
        addPersonToTeamCommand.execute(List.of(personName, teamName));


        team.addPersonToTeam(person);
        //Act
        String result = showPersonActivityCommand.execute(List.of(personName));

        //Assert
        String expectedOutput = "Person activity for Ivancho - [New person with name Ivancho was created, Ivancho added to team TestTeam]";
        assertEquals(expectedOutput, result);
    }

    @Test
    public void execute_Should_ThrowNoSuchElementFoundException_When_NoSuchPersonExists() {
        //Arrange
        String personName = "NonExistentName";

        //Act & Assert
        assertThrows(NoSuchElementFoundException.class, () -> showPersonActivityCommand.execute(List.of(personName)));
    }
}