package commands;

import Utils.TestUtilities;
import commands.contracts.Command;
import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.contracts.Board;
import models.contracts.Feedback;
import models.contracts.Team;
import models.enums.FeedbackStatus;
import models.enums.StoryStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateFeedbackInBoardCommandTests {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    public static final String INVALID_TITLE = TestUtilities.getString(9);
    public static final String VALID_TITLE = TestUtilities.getString(11);
    public static final String INVALID_DESCRIPTION = TestUtilities.getString(9);
    public static final String VALID_DESCRIPTION = TestUtilities.getString(11);
    public static final FeedbackStatus INITIAL_STATUS = FeedbackStatus.NEW;
    public static final String VALID_BOARD_NAME = TestUtilities.getString(6);
    public static final String VALID_TEAM_NAME = TestUtilities.getString(6);

    private Command command;

    private Repository repository;
    private Board board;

    @BeforeEach
    public void before() {
        this.repository = new RepositoryImpl();
        this.command = new CreateFeedbackInBoardCommand(repository);
        Team team = repository.createTeam(VALID_TEAM_NAME);
        this.board = repository.createBoard(VALID_BOARD_NAME, team);
    }

    @Test
    public void should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> parameters = TestUtilities.getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);
        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_TitleIsInvalidLength() {
        List<String> parameters = List.of(INVALID_TITLE, VALID_DESCRIPTION, VALID_BOARD_NAME);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_DescriptionIsInvalidLength() {
        List<String> parameters = List.of(VALID_TITLE, INVALID_DESCRIPTION, VALID_BOARD_NAME);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void Status_should_BeInitialStatus_When_Created() {
        Feedback feedback = repository.createFeedback(VALID_TITLE, VALID_DESCRIPTION);

        Assertions.assertEquals(INITIAL_STATUS,feedback.getFeedbackStatus);
    }

    @Test
    public void should_ThrowException_When_BoardDoesNotExist() {
        List<String> parameters = List.of(VALID_TITLE, VALID_DESCRIPTION, "Invalid board name");

        assertThrows(NoSuchElementFoundException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_AddToBoard_When_ArgumentAreValid() {
        List<String> parameters = List.of(VALID_TITLE, VALID_DESCRIPTION, VALID_BOARD_NAME);
        command.execute(parameters);

        assertEquals(1, board.getTasks().size());
    }
}
