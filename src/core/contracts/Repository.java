package core.contracts;

import models.contracts.*;
import models.enums.Priority;
import models.enums.Severity;
import models.enums.Size;
import models.enums.TaskStatus;

import java.util.List;

public interface Repository {
    List<Team> getTeams();

    List<Board> getBoards();

    List<Person> getPeople();

    List<Task> getTasks();

    List<Comment> getComments();

    Bug createBug(int id, String title, String description, Priority priority, Severity severity, TaskStatus status, Person assignee, List<String> stepsToReproduce);

    Story createStory(int id, String title, String description, Priority priority, Size size, TaskStatus status, Person assignee);

    Feedback createFeedback(int id, String title, String description, TaskStatus status);

    Comment createComment(String author, String content);

    Team createTeam(String name);

    Person createPerson(String name);

    Board createBoard(String name);

    Task findTaskById (List<Task> tasks, int id);

//    Bug getBugById(int bugId);
//
//    Story getStoryById(int storyId);
//    Feedback getFeedbackById(int feedbackId);

}

