package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.PersonImpl;
import models.TeamImpl;
import models.contracts.Person;
import models.contracts.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AddPersonToTeamCommandTests {
    private Repository repository;
    private AddPersonToTeamCommand addPersonToTeamCommand;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        addPersonToTeamCommand = new AddPersonToTeamCommand(repository);
    }

    @Test
    public void execute_Should_AddPersonToTeam_When_ValidParametersProvided() {
        // Arrange
        String personName = "TestName";
        String teamName = "Development";

        repository.createPerson(personName);
        repository.createTeam(teamName);

        Person person = new PersonImpl(personName);
        Team team = new TeamImpl(teamName);

        // Act
        team.addPersonToTeam(person);
        String result = addPersonToTeamCommand.execute(Arrays.asList(personName, teamName));
        System.out.println(team.getMembers());

        // Assert
        assertTrue(team.getMembers().contains(person));
        assertEquals(0, person.getActivityHistory().size());
        assertEquals(0, team.getActivityHistory().size());
        assertEquals(String.format("Person %s added to team %s successfully.", personName, teamName), result);
    }

    @Test
    public void execute_Should_ReturnErrorMessage_When_PersonAlreadyInTeam() {
        // Arrange
        String personName = "TestName";
        String teamName = "Development";

        Person person = new PersonImpl(personName);
        Team team = new TeamImpl(teamName);

        repository.createPerson(personName);
        repository.createTeam(teamName);

        team.addPersonToTeam(person);
        team.addPersonToTeam(person);

        // Act
        String firstAdd = addPersonToTeamCommand.execute(Arrays.asList(personName, teamName));
        String result = addPersonToTeamCommand.execute(Arrays.asList(personName, teamName));

        // Assert
        assertTrue(team.getMembers().contains(person));
        assertEquals(0, person.getActivityHistory().size());
        assertEquals(0, team.getActivityHistory().size());
        assertEquals(String.format("Person %s is already a member of team %s.", personName, teamName), result);
    }

    @Test
    public void execute_Should_ReturnErrorMessage_When_PersonNotFound() {
        // Arrange
        String personName = "TestName";
        String teamName = "Development";

        Team team = new TeamImpl(teamName);

        repository.createTeam(teamName);

        // Act & Assert
        assertFalse(team.getMembers().contains(personName));
        assertEquals(0, team.getActivityHistory().size());
        assertThrows(NoSuchElementFoundException.class, ()-> addPersonToTeamCommand.execute(Arrays.asList(personName, teamName)));
    }

    @Test
    public void execute_Should_ReturnErrorMessage_When_TeamNotFound() {
        // Arrange
        String personName = "TestName";
        String teamName = "Development";

        Person person = new PersonImpl(personName);

        repository.createPerson(personName);

        // Act & Assert
        assertFalse(repository.getTeams().contains(teamName));
        assertEquals(0, person.getActivityHistory().size());
        assertThrows(NoSuchElementFoundException.class, ()-> addPersonToTeamCommand.execute(Arrays.asList(personName, teamName)));
    }

    @Test
    public void execute_Should_ReturnThrowExceptionMessage_When_LessArgumentsProvided() {
        // Arrange
        String personName = "TestName";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, ()-> addPersonToTeamCommand.execute(List.of(personName)));
    }
}
