package commands;

import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.contracts.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowBoardActivityCommandTests {

    private Repository repository;
    private ShowBoardActivityCommand showBoardActivityCommand;
    private CreateFeedbackInBoardCommand createFeedbackInBoardCommand;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryImpl();
        showBoardActivityCommand = new ShowBoardActivityCommand(repository);
        createFeedbackInBoardCommand = new CreateFeedbackInBoardCommand(repository);
    }

    @Test
    public void execute_should_ShowBoardActivity_When_BoardActivityProvided() {
        // Arrange
        String teamName = "TestTeam";
        Team team = repository.createTeam(teamName);
        String boardName = "BoardTest";
        repository.createBoard(boardName, team);
        createFeedbackInBoardCommand.execute(List.of("TitleBoardTest", "DescriptionTest", "New", boardName));

        //Act
        String result = showBoardActivityCommand.execute(List.of(boardName));

        //Assert
        String expectedOutput = "Board activity for BoardTest - [New board with name BoardTest was created, Feedback with title TitleBoardTest added to board BoardTest]";
        assertEquals(expectedOutput, result);
    }

    @Test
    public void execute_Should_ShowNoBoardFound_When_NoSuchBoardExists() {
        //Arrange
        String boardName = "NonExistentTeam";

        //Act & Assert
        assertThrows(NoSuchElementFoundException.class, () -> showBoardActivityCommand.execute(List.of(boardName)));
    }
}
