package models;

import Utils.TestUtilities;
import core.RepositoryImpl;
import core.contracts.Repository;
import models.contracts.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BoardImplTests {
    public static final String INVALID_NAME = TestUtilities.getString(4);
    public static final String VALID_NAME = TestUtilities.getString(6);
    public static final String VALID_TITLE = TestUtilities.getString(11);
    public static final String VALID_DESCRIPTION = TestUtilities.getString(11);
    Board board;
    Repository repository;
    @BeforeEach
    public void before(){
        board = new BoardImpl(VALID_NAME);
        repository = new RepositoryImpl();
    }

    @Test
    public void constructor_Should_CreateCollections() {
        Assertions.assertAll(
                () -> Assertions.assertNotNull(board.getTasks()),
                () -> Assertions.assertNotNull(board.getActivityHistory())
        );
    }
    @Test
    public void getTasks_Should_ReturnCopy() {
        List<Task> list = board.getTasks();
        Assertions.assertNotSame(list, board.getTasks());
    }

    @org.junit.jupiter.api.Test
    public void getActivityHistory_Should_ReturnCopy() {
        List<String> list = board.getActivityHistory();
        Assertions.assertNotSame(list, board.getActivityHistory());
    }
    @org.junit.jupiter.api.Test
    public void constructor_Should_ThrowException_When_NameOutOfBounds(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BoardImpl(INVALID_NAME));
    }

    @org.junit.jupiter.api.Test
    public void constructor_Should_CreateTeam_When_NameInBounds(){
        Assertions.assertEquals(VALID_NAME, board.getName());
    }

    @org.junit.jupiter.api.Test
    public  void removeTaskFromBoard_Should_ThrowException_When_TaskDoesNotExist(){
        Feedback feedback = repository.createFeedback(VALID_TITLE,VALID_DESCRIPTION);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> board.removeTaskFromBoard(feedback));
    }
    @org.junit.jupiter.api.Test
    public  void removeTaskFromTasks_Should_RemoveTasks_When_TaskExist(){
        Feedback feedback = repository.createFeedback(VALID_TITLE,VALID_DESCRIPTION);
        board.addTaskToBoard(feedback);
        board.removeTaskFromBoard(feedback);
        Assertions.assertEquals(0, board.getTasks().size());
    }
    @org.junit.jupiter.api.Test
    public void addTaskToBoard_Should_AddTaskToBoard(){
        Feedback feedback = repository.createFeedback(VALID_TITLE,VALID_DESCRIPTION);
        board.addTaskToBoard(feedback);
        Assertions.assertEquals(1, board.getTasks().size());
    }
    @Test
    public void addToActivityHistory_Should_addToActivityHistory(){
        board.addToActivityHistory("test");
        Assertions.assertEquals(1, board.getActivityHistory().size());
    }


}
