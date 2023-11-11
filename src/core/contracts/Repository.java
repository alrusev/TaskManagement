package core.contracts;

import models.contracts.Comment;
import models.contracts.Task;
import models.contracts.Team;
import models.contracts.Board;
import models.contracts.Person;

import java.util.List;

public interface Repository {
    Task getTaskById(String taskId);

    void addTask(Task task);

    void removeTask(Task task);

    List<Comment> getAllComments();

    void addComment(Comment comment);

    List<Team> getAllTeams();

    Team getTeamByName(String teamName);

    void addTeam(Team team);

    void removeTeam(Team team);

    List<Board> getAllBoards();

    Board getBoardByName(String boardName);

    void addBoard(Board board);

    void removeBoard(Board board);

    List<Person> getAllPeople();

    Person getPersonByName(String personName);

    void addPerson(Person person);

    void removePerson(Person person);

    void createTeam(String teamName, List<Person> members, List<Board> boards);
    
    void createPerson(String personName);

}
