package core;

import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import exceptions.TheNameIsNotUniqueException;
import models.*;
import models.contracts.*;
import models.enums.Priority;
import models.enums.Severity;
import models.enums.Size;
import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {
    public static final String NO_SUCH_ELEMENT_FOUND = "No Such %s Found ";
    public static final String NAME_NOT_UNIQUE = "The name of the %s is not unique in the system";
    public static final String NAME_OF_BOARD_NOT_UNIQUE = "The name of the board is not unique in the team";


    private final List<Team> teams = new ArrayList<>();
    private final List<Board> boards = new ArrayList<>();
    private final List<Person> people = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private final List<Bug> bugs = new ArrayList<>();
    private final List<Story> stories = new ArrayList<>();
    private final List<Feedback> feedbacks = new ArrayList<>();
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
    public List<Bug> getBugs() {
        return new ArrayList<>(bugs);
    }
    @Override
    public List<Story> getStories() {
        return new ArrayList<>(stories);
    }
    @Override
    public List<Feedback> getFeedbacks() {
        return new ArrayList<>(feedbacks);
    }

    @Override
    public Bug createBug(String title, String description, Priority priority,
                         Severity severity, List<String> stepsToReproduce) {
        Bug bug = new BugImpl(title,description,priority,severity,stepsToReproduce, nextId);
        nextId++;
        tasks.add(bug);
        bugs.add(bug);
        return bug;
    }

    @Override
    public Story createStory(String title, String description, Priority priority,
                             Size size) {
        Story story = new StoryImpl(nextId,title,description,priority,size);
        nextId++;
        tasks.add(story);
        stories.add(story);
        return story;
    }

    @Override
    public Feedback createFeedback(String title, String description, int rating) {
        Feedback feedback = new FeedbackImpl(nextId,title,description,rating);
        nextId++;
        tasks.add(feedback);
        feedbacks.add(feedback);
        return feedback;
    }

    @Override
    public Comment createComment(String author, String content) {
        return new CommentImpl(author,content);
    }

    @Override
    public Team createTeam(String name) {
        isNameUniqueInSystem(name,"team");
        Team team = new TeamImpl(name);
        teams.add(team);
        return team;
    }

    @Override
    public Person createPerson(String name) {
        isNameUniqueInSystem(name,"person");
        Person person = new PersonImpl(name);
        people.add(person);
        return person;
    }

    @Override
    public Board createBoard(String name, Team team) {
        isNameUniqueInTeam(name,team);
        Board board = new BoardImpl(name);
        boards.add(board);
        team.addBoardToTeam(board);
        return board;
    }
    @Override
    public <T extends Task> T  findTaskById(int id, List<T> list ){
        for (T task : list) {
            if (task.getId() == id)
                return task;
        }
        throw new NoSuchElementFoundException(String.format("No task with ID %d", id));
    }
    @Override
    public <T extends Nameable> T findElementByName(String name,List<T> listToLook,String type){
        for (T element :listToLook) {
            if (element.getName().equalsIgnoreCase(name))
                return element;
        }
        throw new NoSuchElementFoundException(String.format(NO_SUCH_ELEMENT_FOUND,type));
    }

    private void isNameUniqueInSystem(String name, String type) {
        for (Team team:getTeams()) {
            if (team.getName().equalsIgnoreCase(name))
                throw new TheNameIsNotUniqueException(String.format(NAME_NOT_UNIQUE,type));
        }
        for (Board board:getBoards()) {
            if (board.getName().equalsIgnoreCase(name))
                throw new TheNameIsNotUniqueException(String.format(NAME_NOT_UNIQUE,type));
        }
        for (Person person:getPeople()) {
            if (person.getName().equalsIgnoreCase(name))
                throw new TheNameIsNotUniqueException(String.format(NAME_NOT_UNIQUE,type));
        }
    }

    private void isNameUniqueInTeam(String name, Team team) {
        for (Person person:team.getMembers()) {
            if (person.getName().equalsIgnoreCase(name))
                throw new TheNameIsNotUniqueException(NAME_OF_BOARD_NOT_UNIQUE);
        }
        for (Board board:team.getBoards()){
            if (board.getName().equalsIgnoreCase(name))
                throw new TheNameIsNotUniqueException(NAME_OF_BOARD_NOT_UNIQUE);
        }
    }
}
