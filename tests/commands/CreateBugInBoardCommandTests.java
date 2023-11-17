package commands;

import Utils.TestUtilities;
import commands.contracts.Command;
import core.RepositoryImpl;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.contracts.Board;
import models.contracts.Team;
import models.enums.Priority;
import models.enums.Severity;
import models.enums.StoryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateBugInBoardCommandTests {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 8;
    public static final String INVALID_TITLE = TestUtilities.getString(9);
    public static final String VALID_TITLE = TestUtilities.getString(11);
    public static final String INVALID_DESCRIPTION = TestUtilities.getString(9);
    public static final String VALID_DESCRIPTION = TestUtilities.getString(11);
    public static final StoryStatus VALID_STATUS = StoryStatus.NEW;
    public static final Severity VALID_SEVERITY = Severity.MAJOR;
    public static final Priority VALID_PRIORITY = Priority.LOW;
    public static final String VALID_BOARD_NAME = TestUtilities.getString(6);
    public static final String VALID_TEAM_NAME = TestUtilities.getString(6);
    public static final String VALID_PERSON_NAME = TestUtilities.getString(6);


    private Command command;

    private Repository repository;
    private Board board;

    @BeforeEach
    public void before() {
        this.repository = new RepositoryImpl();
        this.command = new CreateBugInBoardCommand(repository);
        Team team = repository.createTeam(VALID_TEAM_NAME);
        this.board = repository.createBoard(VALID_BOARD_NAME,team);
        repository.createPerson(VALID_PERSON_NAME);
    }
    @Test
    public void should_ThrowException_When_ArgumentCountDifferentThanExpected(){
        List<String> parameters = TestUtilities.getList(EXPECTED_NUMBER_OF_ARGUMENTS + 1);
        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    public void should_ThrowException_When_TitleIsInvalidLength(){
        List<String> parameters = List.of(INVALID_TITLE,VALID_DESCRIPTION,VALID_PRIORITY.toString(),
                VALID_SEVERITY.toString(),VALID_STATUS.toString(),VALID_PERSON_NAME,
                "steps to reproduce1,step to reproduce 2",VALID_BOARD_NAME);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    public void should_ThrowException_When_DescriptionIsInvalidLength(){
        List<String> parameters = List.of(VALID_TITLE,INVALID_DESCRIPTION,VALID_PRIORITY.toString(),
                VALID_SEVERITY.toString(),VALID_STATUS.toString(),VALID_PERSON_NAME,
                "steps to reproduce1,step to reproduce 2",VALID_BOARD_NAME);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    public void should_ThrowException_When_PriorityIsInvalid(){
        List<String> parameters = List.of(VALID_TITLE,VALID_DESCRIPTION,"Invalid priority",
                VALID_SEVERITY.toString(),VALID_STATUS.toString(),VALID_PERSON_NAME,
                "steps to reproduce1,step to reproduce 2",VALID_BOARD_NAME);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
        @Test
    public void should_ThrowException_When_SeverityIsInvalid(){
        List<String> parameters = List.of(VALID_TITLE,VALID_DESCRIPTION,VALID_PRIORITY.toString(),
                "Invalid severity",VALID_STATUS.toString(),VALID_PERSON_NAME,
                "steps to reproduce1,step to reproduce 2",VALID_BOARD_NAME);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }

    @Test
    public void should_ThrowException_When_StatusIsInvalid(){
        List<String> parameters = List.of(VALID_TITLE,VALID_DESCRIPTION,VALID_PRIORITY.toString(),
                VALID_SEVERITY.toString(),"Invalid status",VALID_PERSON_NAME,
                "steps to reproduce1,step to reproduce 2",VALID_BOARD_NAME);

        assertThrows(IllegalArgumentException.class, () -> command.execute(parameters));
    }
    @Test
    public void should_ThrowException_When_BoardDoesNotExist(){
        List<String> parameters = List.of(VALID_TITLE,VALID_DESCRIPTION,VALID_PRIORITY.toString(),
                VALID_SEVERITY.toString(),VALID_STATUS.toString(),VALID_PERSON_NAME,
                "steps to reproduce1,step to reproduce 2","Invalid board name");

        assertThrows(NoSuchElementFoundException.class, () -> command.execute(parameters));
    }
    @Test
    public void should_ThrowException_When_AssigneeDoesNotExist(){
        List<String> parameters = List.of(VALID_TITLE,VALID_DESCRIPTION,VALID_PRIORITY.toString(),
                VALID_SEVERITY.toString(),VALID_STATUS.toString(),"invalid person name",
                "steps to reproduce1,step to reproduce 2",VALID_BOARD_NAME);

        assertThrows(NoSuchElementFoundException.class, () -> command.execute(parameters));
    }
    @Test
    public void should_AddToBoard_When_ArgumentAreValid(){
        List<String> parameters = List.of(VALID_TITLE,VALID_DESCRIPTION,VALID_PRIORITY.toString(),
                VALID_SEVERITY.toString(),VALID_STATUS.toString(),VALID_PERSON_NAME,
                "steps to reproduce1,step to reproduce 2",VALID_BOARD_NAME);
        command.execute(parameters);

        assertEquals(1,board.getTasks().size());
    }
}
