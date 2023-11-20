package core.contracts;

import models.contracts.*;
import models.enums.Priority;
import models.enums.Severity;
import models.enums.Size;

import java.util.List;

public interface Repository {
    List<Team> getTeams();

    List<Board> getBoards();

    List<Person> getPeople();

    List<Task> getTasks();
    List<Bug> getBugs();
    List<Story> getStories();
    List<Feedback> getFeedbacks();

    Bug createBug(String title, String description, Priority priority, Severity severity, List<String> stepsToReproduce);

    Story createStory(String title, String description, Priority priority, Size size);

    Feedback createFeedback(String title, String description, int rating);

    Comment createComment(String author, String content);

    Team createTeam(String name);

    Person createPerson(String name);

    Board createBoard(String name,Team team);

    <T extends Task> T findTaskById(int id, List<T> list);
    <T extends  Nameable> T findElementByName(String name, List<T> listToLook,String type);
     boolean isNameUniqueInSystem(String name);
    boolean isNameUniqueInTeam(String name, Team team);
}

