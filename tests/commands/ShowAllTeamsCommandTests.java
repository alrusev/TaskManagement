package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowAllTeamsCommandTests {
    private Repository repository;
    private ShowAllTeamsCommand showAllTeamsCommand;


    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        showAllTeamsCommand = new ShowAllTeamsCommand(repository);
    }

    @Test
    public void execute_Should_ShowNoTeamsRegistered_When_NoTeamsCreated() {
        // Act
        String result = showAllTeamsCommand.execute(Collections.emptyList());

        // Assert
        String expectedOutput = "There are no registered teams.";
        assertEquals(expectedOutput, result.trim());
    }

    @Test
    public void execute_Should_ShowTeamsRegistered_When_TeamsCreated() {
        // Arrange
        repository.createTeam("AlphaTeam");
        repository.createTeam("SuperTeam");

        // Act
        String result = showAllTeamsCommand.execute(Collections.emptyList());

        // Assert
        String expectedOutput = "Teams: AlphaTeam, SuperTeam";
        assertEquals(expectedOutput, result.trim());
    }
}
