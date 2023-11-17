package core;

import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.*;
import models.contracts.*;
import models.enums.Priority;
import models.enums.Severity;
import models.enums.Size;
import models.enums.StoryStatus;

import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {
    public static final String NO_SUCH_ELEMENT_FOUND = "No Such %s Found ";
    private List<Team> teams = new ArrayList<>();
    private List<Board> boards = new ArrayList<>();
    private List<Person> people = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private static int nextId;

    public RepositoryImpl() {
        nextId = 1;
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
                         Severity severity, Person assignee, List<String> stepsToReproduce) {
        Bug bug = new BugImpl(title,description,priority,severity,assignee,stepsToReproduce, nextId);
        nextId++;
        tasks.add(bug);
        return bug;
    }

    @Override
    public Story createStory(String title, String description, Priority priority,
                             Size size, Person assignee) {
        Story story = new StoryImpl(nextId,title,description,priority,size,assignee);
        nextId++;
        tasks.add(story);
        return story;
    }

    @Override
    public Feedback createFeedback(String title, String description) {
        Feedback feedback = new FeedbackImpl(nextId,title,description);
        nextId++;
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
        person.addToActivityHistory("New person with name " + person.getName() + " was created");
        return person;
    }

    @Override
    public Board createBoard(String name, Team team) {
        Board board = new BoardImpl(name);
        boards.add(board);
        team.addBoardToTeam(board);
        board.addToActivityHistory("New board with name " + board.getName() + " was created");
        return board;
    }
    @Override
    public Task findTaskById(List<Task> tasks, int id){
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getId() == id){
                return tasks.get(i);
            }
        }
        throw new IllegalArgumentException(String.format("No task with ID %d", id));
    }
    @Override
    public <T extends Nameable> T findElementByName(String name,List<T> listToLook,String type){
        for (T element :listToLook) {
            if (element.getName().equalsIgnoreCase(name))
                return element;
        }
        throw new NoSuchElementFoundException(String.format(NO_SUCH_ELEMENT_FOUND,type));
    }

    @Override
    public boolean isNameUniqueInSystem(String name) {
        for (Team team:getTeams()) {
            if (team.getName().equalsIgnoreCase(name))
                return false;
        }
        for (Board board:getBoards()) {
            if (board.getName().equalsIgnoreCase(name))
                return false;
        }
        for (Person person:getPeople()) {
            if (person.getName().equalsIgnoreCase(name))
                return false;
        }
        return true;
    }

    @Override
    public boolean isNameUniqueInTeam(String name, Team team) {
        for (Person person:team.getMembers()) {
            if (person.getName().equalsIgnoreCase(name))
                return false;
        }
        for (Board board:team.getBoards()){
            if (board.getName().equalsIgnoreCase(name))
                return false;
        }
        return true;
    }
}
