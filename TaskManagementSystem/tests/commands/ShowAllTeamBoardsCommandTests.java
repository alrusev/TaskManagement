package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.BoardImpl;
import models.TeamImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ListingHelpers;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowAllTeamBoardsCommandTests {
    private Repository repository;
    private ShowAllTeamBoardsCommand showAllTeamBoardsCommand;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        showAllTeamBoardsCommand = new ShowAllTeamBoardsCommand(repository);
    }

    @Test
    public void execute_should_ShowNoBoardsAddedToTeam_When_NoBoardsAddedToTeam() {
        // Arrange
        String teamName = "TestTeam";
        repository.createTeam(teamName);

        //Act
        String result = showAllTeamBoardsCommand.execute(List.of(teamName));

        //Assert
        String expectedOutput = String.format("Team %s does not have any boards", teamName);
        assertEquals(expectedOutput, result);
    }

    @Test
    public void execute_should_ShowBoardsAddedToTeam_When_BoardsAddedToTeam() {
        // Arrange
        String teamName = "TestTeam";
        repository.createTeam(teamName);
        TeamImpl team = (TeamImpl) repository.findElementByName(teamName, repository.getTeams(), "Team");
        team.addBoardToTeam(new BoardImpl("SmallBoard"));
        team.addBoardToTeam(new BoardImpl("BigBoard"));

        // Act
        String result = showAllTeamBoardsCommand.execute(List.of(teamName));

        //Assert
        String expectedOutput = "Boards of TestTeam - BigBoard, SmallBoard";
        assertEquals(expectedOutput, result);
    }

    @Test
    public void execute_Should_ThrowNoSuchElementFoundException_When_NoBoardsAddedToTeam() {
        //Arrange
        String teamName = "NonExistentTeam";

        //Act & Assert
        assertThrows(NoSuchElementFoundException.class, () -> showAllTeamBoardsCommand.execute(List.of(teamName)));
    }
}
