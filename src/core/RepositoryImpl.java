package core;

import core.contracts.Repository;
import models.*;
import models.contracts.*;
import models.enums.Priority;
import models.enums.Severity;
import models.enums.Size;
import models.enums.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {
    private List<Team> teams;
    private List<Board> boards;
    private List<Person> people;
    private List<Task> tasks;
    private List<Comment> comments;
    private static int nextId = 0;

    public RepositoryImpl(List<Team> teams, List<Board> boards, List<Person> people, List<Task> tasks, List<Comment> comments) {
        this.teams = new ArrayList<>(teams);
        this.boards = new ArrayList<>(boards);
        this.people = new ArrayList<>(people);
        this.tasks = new ArrayList<>(tasks);
        this.comments = new ArrayList<>(comments);
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
    public Bug createBug(int id, String title, String description, Priority priority,
                         Severity severity, TaskStatus status, Person assignee, List<String> stepsToReproduce) {
        Bug bug = new BugImpl(++nextId,title,description,priority,severity,status,assignee,stepsToReproduce);
        tasks.add(bug);
        return bug;
    }

    @Override
    public Story createStory(int id, String title, String description, Priority priority,
                             Size size, TaskStatus status, Person assignee) {
        Story story = new StoryImpl(++nextId,title,description,priority,size,status,assignee);
        tasks.add(story);
        return story;
    }

    @Override
    public Feedback createFeedback(int id, String title, String description, TaskStatus status) {
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

    public Bug getBugById(int bugId) {
        for (Task task : tasks) {
            if (task instanceof Bug && task.getId() == bugId) {
                return (Bug) task;
            }
        }
        throw new IllegalArgumentException(String.format("No Bug with ID '%d' found.", bugId));
    }

    @Override
    public Story getStoryById(int storyId) {
        for (Task task : tasks) {
            if (task instanceof Story && task.getId() == storyId) {
                return (Story) task;
            }
        }
        throw new IllegalArgumentException(String.format("No Story with ID '%d' found.", storyId));
    }

    @Override
    public Feedback getFeedbackById(int feedbackId) {
        for (Task task:tasks) {
            if (task instanceof Feedback && task.getId() == feedbackId){
                return (Feedback) task;
            }
        }
        throw new IllegalArgumentException(String.format("No Feedback with ID '%d' found.", feedbackId));
    }
}
