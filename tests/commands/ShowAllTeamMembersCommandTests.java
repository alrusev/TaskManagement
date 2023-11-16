package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.PersonImpl;
import models.TeamImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowAllTeamMembersCommandTests {

    private Repository repository;
    private ShowAllTeamMembersCommand showAllTeamMembersCommand;


    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        showAllTeamMembersCommand = new ShowAllTeamMembersCommand(repository);
    }

    @Test
    public void execute_Should_ShowNoMembersAssigned_When_NoMembersAssigned() {
        // Arrange
        String teamName = "EmptyTeam";
        repository.createTeam(teamName);

        // Act
        String result = showAllTeamMembersCommand.execute(List.of(teamName));

        // Assert
        String expectedOutput = String.format("Team %s does not have any members assigned yet.", teamName);
        assertEquals(expectedOutput, result.trim());
    }

    @Test
    public void execute_Should_ShowMembersAssigned_When_MembersAssigned() {
        // Arrange
        String teamName = "TestTeam";
        repository.createTeam(teamName);
        TeamImpl team = (TeamImpl) repository.findElementByName(teamName, repository.getTeams(), "Team");
        team.addPersonToTeam(new PersonImpl("Pesho"));
        team.addPersonToTeam(new PersonImpl("Gosho"));

        // Act
        String result = showAllTeamMembersCommand.execute(List.of(teamName));

        // Assert
        String expectedOutput = "Members of TestTeam - Gosho, Pesho";
        assertEquals(expectedOutput, result.trim());
    }

    @Test
    public void execute_Should_ThrowNoSuchElementFoundException_When_NoMembersAssigned() {
        // Arrange
        String teamName = "NonExistentTeam";

        // Act & Assert
        assertThrows(NoSuchElementFoundException.class, ()-> showAllTeamMembersCommand.execute(List.of(teamName)));
    }
}
