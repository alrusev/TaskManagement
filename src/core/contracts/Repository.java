package core.contracts;

import models.contracts.*;
import models.enums.Priority;
import models.enums.Severity;
import models.enums.Size;
import models.enums.StoryStatus;

import java.util.List;

public interface Repository {
    List<Team> getTeams();

    List<Board> getBoards();

    List<Person> getPeople();

    List<Task> getTasks();

    List<Comment> getComments();

    Bug createBug(String title, String description, Priority priority, Severity severity, StoryStatus status, Person assignee, List<String> stepsToReproduce);

    Story createStory(String title, String description, Priority priority, Size size, StoryStatus status, Person assignee);

    Feedback createFeedback(String title, String description, StoryStatus status);

    Comment createComment(String author, String content);

    Team createTeam(String name);

    Person createPerson(String name);

    Board createBoard(String name,Team team);

    Task findTaskById (List<Task> tasks, int id);
    <T extends  Nameable> T findElementByName(String name, List<T> listToLook,String type);
    boolean isNameUniqueInSystem(String name);
    boolean isNameUniqueInTeam(String name, Team team);


//    Bug getBugById(int bugId);
//
//    Story getStoryById(int storyId);
//    Feedback getFeedbackById(int feedbackId);

}

