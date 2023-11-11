package core;

import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.*;
import models.contracts.*;
import models.enums.Priority;
import models.enums.Severity;
import models.enums.Size;
import models.enums.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {
    public static final String NO_SUCH_BOARD_FOUND = "No Such Board Found ";
    private List<Team> teams = new ArrayList<>();
    private List<Board> boards = new ArrayList<>();
    private List<Person> people = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private static int nextId;

    public RepositoryImpl() {
        nextId = 0;
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public List<Person> getPeople() {
        return new ArrayList<>(people);
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public Bug createBug(String title, String description, Priority priority,
                         Severity severity, TaskStatus status, Person assignee, List<String> stepsToReproduce) {
        Bug bug = new BugImpl(++nextId,title,description,priority,severity,status,assignee,stepsToReproduce);
        tasks.add(bug);
        return bug;
    }

    @Override
    public Story createStory(String title, String description, Priority priority,
                             Size size, TaskStatus status, Person assignee) {
        Story story = new StoryImpl(++nextId,title,description,priority,size,status,assignee);
        tasks.add(story);
        return story;
    }

    @Override
    public Feedback createFeedback(String title, String description, TaskStatus status) {
        Feedback feedback = new FeedbackImpl(++nextId,title,description,status);
        tasks.add(feedback);
        return feedback;
    }

    @Override
    public Comment createComment(String author, String content) {
        Comment comment = new CommentImpl(author,content);
        comments.add(comment);
        return comment;
    }

    @Override
    public Team createTeam(String name) {
        Team team = new TeamImpl(name);
        teams.add(team);
        return team;
    }

    @Override
    public Person createPerson(String name) {
        Person person = new PersonImpl(name);
        people.add(person);
        return person;
    }

    @Override
    public Board createBoard(String name) {
        Board board = new BoardImpl(name);
        boards.add(board);
        return board;
    }
    private Task findTaskById(List<Task> tasks, int id){
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getId() == id){
                return tasks.get(i);
            }
        }
        throw new IllegalArgumentException(String.format("No task with ID %d", id));
    }
    @Override
    public Board findBoardByName(String name){
        for (Board board:getBoards()) {
            if (board.getName().equals(name))
                return board;
        }
        throw new NoSuchElementFoundException(NO_SUCH_BOARD_FOUND);
    }


}
