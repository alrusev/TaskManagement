package core;

import Utils.TestUtilities;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.contracts.*;
import models.enums.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepositoryTests {
    private static final String VALID_TEAM_NAME = TestUtilities.getString(6);
    private static final String VALID_PERSON_NAME = TestUtilities.getString(6);
    private static final String VALID_BOARD_NAME = TestUtilities.getString(6);
    public static final String VALID_TASK_TITLE = TestUtilities.getString(11);
    public static final String VALID_TASK_DESCRIPTION = TestUtilities.getString(11);
    public static final BugStatus INITIAL_BUG_STATUS = BugStatus.ACTIVE;
    public static final StoryStatus INITIAL_STORY_STATUS = StoryStatus.NOTDONE;
    public static final FeedbackStatus INITIAL_FEEDBACK_STATUS = FeedbackStatus.NEW;
    public static final Severity VALID_BUG_SEVERITY = Severity.MAJOR;
    public static final Priority VALID_BUG_PRIORITY = Priority.LOW;
    public static final Size VALID_STORY_SIZE = Size.SMALL;
    public static final Priority VALID_STORY_PRIORITY = Priority.LOW;
    public static final int VALID_RATING = 2;


    Repository repository;

    @BeforeEach
    public void beforeEach() {
        repository = new RepositoryImpl();
    }

    @Test
    public void constructor_Should_CreateCollections() {
        Repository repo = new RepositoryImpl();
        Assertions.assertAll(
                () -> Assertions.assertNotNull(repo.getTasks()),
                () -> Assertions.assertNotNull(repo.getPeople()),
                () -> Assertions.assertNotNull(repo.getTeams()),
                () -> Assertions.assertNotNull(repo.getBoards())
        );
    }

    @Test
    public void getTeams_Should_ReturnCopy() {
        List<Team> list = repository.getTeams();
        Assertions.assertNotSame(list, repository.getTeams());
    }

    @Test
    public void getBoards_Should_ReturnCopy() {
        List<Board> list = repository.getBoards();
        Assertions.assertNotSame(list, repository.getBoards());
    }

    @Test
    public void getPeople_Should_ReturnCopy() {
        List<Person> list = repository.getPeople();
        Assertions.assertNotSame(list, repository.getPeople());
    }

    @Test
    public void getTasks_Should_ReturnCopy() {
        List<Task> list = repository.getTasks();
        Assertions.assertNotSame(list, repository.getTasks());
    }
    @Test
    public void getBugs_Should_ReturnCopy() {
        List<Bug> list = repository.getBugs();
        Assertions.assertNotSame(list, repository.getBugs());
    }
    @Test
    public void getStories_Should_ReturnCopy() {
        List<Story> list = repository.getStories();
        Assertions.assertNotSame(list, repository.getStories());

    }
    @Test
    public void getFeedbacks_Should_ReturnCopy() {
        List<Feedback> list = repository.getFeedbacks();
        Assertions.assertNotSame(list, repository.getFeedbacks());
    }

    @Test
    public void createTeam_Should_ReturnTeam_WhenArgumentsValid() {
        Team team = repository.createTeam(VALID_TEAM_NAME);
        Assertions.assertEquals(team.getName(), VALID_TEAM_NAME);
    }

    @Test
    public void createPerson_Should_ReturnPerson_WhenArgumentsValid() {
        Person person = repository.createPerson(VALID_PERSON_NAME);
        Assertions.assertEquals(person.getName(), VALID_PERSON_NAME);
    }

    @Test
    public void createBoard_Should_ReturnBoard_WhenArgumentsValid() {
        Team team = repository.createTeam(VALID_TEAM_NAME);
        Board board = repository.createBoard(VALID_BOARD_NAME, team);
        Assertions.assertEquals(board.getName(), VALID_BOARD_NAME);
    }

    @Test
    public void createBug_Should_ReturnBug_WhenArgumentsValid() {
        Bug bug = repository.createBug(VALID_TASK_TITLE, VALID_TASK_DESCRIPTION, VALID_BUG_PRIORITY, VALID_BUG_SEVERITY, TestUtilities.getList(2));
        Assertions.assertAll(
                () -> Assertions.assertEquals(bug.getTitle(), VALID_TASK_TITLE),
                () -> Assertions.assertEquals(bug.getDescription(), VALID_TASK_DESCRIPTION),
                () -> Assertions.assertEquals(bug.getPriority(), VALID_BUG_PRIORITY),
                () -> Assertions.assertEquals(bug.getSeverity(), VALID_BUG_SEVERITY),
                () -> Assertions.assertEquals(bug.getBugStatus(), INITIAL_BUG_STATUS),
                () -> Assertions.assertEquals(bug.getStepsToReproduce(), TestUtilities.getList(2))
        );

    }

    @Test
    public void createStory_Should_ReturnStory_WhenArgumentsValid() {
        Story story = repository.createStory(VALID_TASK_TITLE, VALID_TASK_DESCRIPTION, VALID_STORY_PRIORITY, VALID_STORY_SIZE);
        Assertions.assertAll(
                () -> Assertions.assertEquals(story.getTitle(), VALID_TASK_TITLE),
                () -> Assertions.assertEquals(story.getDescription(), VALID_TASK_DESCRIPTION),
                () -> Assertions.assertEquals(story.getPriority(), VALID_STORY_PRIORITY),
                () -> Assertions.assertEquals(story.getSize(), VALID_STORY_SIZE),
                () -> Assertions.assertEquals(story.getStoryStatus(), INITIAL_STORY_STATUS)
        );

    }

    @Test
    public void createFeedback_Should_ReturnFeedback_WhenArgumentsValid() {
        Feedback feedback = repository.createFeedback(VALID_TASK_TITLE, VALID_TASK_DESCRIPTION,VALID_RATING);
        Assertions.assertAll(
                () -> Assertions.assertEquals(feedback.getTitle(), VALID_TASK_TITLE),
                () -> Assertions.assertEquals(feedback.getDescription(), VALID_TASK_DESCRIPTION),
                () -> Assertions.assertEquals(feedback.getFeedbackStatus(), INITIAL_FEEDBACK_STATUS)
        );

    }

    @Test
    public void findElementByName_Should_ReturnTeam_WhenTeamExists() {
        Team team = repository.createTeam(VALID_TEAM_NAME);
        Assertions.assertEquals(repository.findElementByName(VALID_TEAM_NAME, repository.getTeams(), "Team").getName(), team.getName());
    }

    @Test
    public void findElementByName_Should_ThrowException_WhenTeamDoesNotExists() {
        assertThrows(NoSuchElementFoundException.class, () -> repository.findElementByName(VALID_TEAM_NAME, repository.getTeams(), "Team"));
    }

    @Test
    public void findElementByName_Should_ReturnBoard_WhenBoardExists() {
        Team team = repository.createTeam(VALID_TEAM_NAME);
        Board board = repository.createBoard(VALID_BOARD_NAME, team);
        Assertions.assertEquals(repository.findElementByName(VALID_BOARD_NAME, repository.getBoards(), "Board").getName(), board.getName());
    }

    @Test
    public void findElementByName_Should_ThrowException_WhenBoardDoesNotExists() {
        assertThrows(NoSuchElementFoundException.class, () -> repository.findElementByName(VALID_BOARD_NAME, repository.getBoards(), "Board"));
    }


    @Test
    public void findElementByName_Should_ReturnPerson_WhenPersonExists() {
        Person person = repository.createPerson(VALID_PERSON_NAME);
        Assertions.assertEquals(repository.findElementByName(VALID_PERSON_NAME, repository.getPeople(), "Person").getName(), person.getName());
    }

    @Test
    public void findElementByName_Should_ThrowException_WhenPersonDoesNotExists() {
        assertThrows(NoSuchElementFoundException.class, () -> repository.findElementByName(VALID_PERSON_NAME, repository.getPeople(), "Person"));
    }

    @Test
    public void findTaskById_Should_ReturnTask_WhenTaskExists() {
        Bug bug = repository.createBug(VALID_TASK_TITLE, VALID_TASK_DESCRIPTION, VALID_BUG_PRIORITY, VALID_BUG_SEVERITY, TestUtilities.getList(2));
        Bug foundBug = repository.findTaskById(bug.getId(),repository.getBugs());
        Assertions.assertAll(
                () -> Assertions.assertEquals(foundBug.getTitle(), VALID_TASK_TITLE),
                () -> Assertions.assertEquals(foundBug.getDescription(), VALID_TASK_DESCRIPTION)
        );
    }

    @Test
    public void findTaskById_Should_ThrowException_WhenTaskDoesNotExists() {
        assertThrows(NoSuchElementFoundException.class, () -> repository.findTaskById(1,repository.getBugs()));
    }

}
