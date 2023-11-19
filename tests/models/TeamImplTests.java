package models;

import Utils.TestUtilities;
import core.RepositoryImpl;
import core.contracts.Repository;
import models.contracts.Board;
import models.contracts.Person;
import models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TeamImplTests {
    public static final String INVALID_NAME = TestUtilities.getString(4);
    public static final String VALID_NAME = TestUtilities.getString(6);
    Team team;
    Repository repository;
    @BeforeEach
    public void before(){
         team = new TeamImpl(VALID_NAME);
         repository = new RepositoryImpl();
    }

    @Test
    public void constructor_Should_CreateCollections() {
        Assertions.assertAll(
                () -> Assertions.assertNotNull(team.getBoards()),
                () -> Assertions.assertNotNull(team.getMembers()),
                () -> Assertions.assertNotNull(team.getActivityHistory())
        );
    }
    @Test
    public void getBoards_Should_ReturnCopy() {
        List<Board> list = team.getBoards();
        Assertions.assertNotSame(list, team.getBoards());
    }

    @Test
    public void getMembers_Should_ReturnCopy() {
        List<Person> list = team.getMembers();
        Assertions.assertNotSame(list, team.getMembers());
    }
    @Test
    public void getActivityHistory_Should_ReturnCopy() {
        List<String> list = team.getActivityHistory();
        Assertions.assertNotSame(list, team.getActivityHistory());
    }
    @Test
    public void constructor_Should_ThrowException_When_NameOutOfBounds(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new TeamImpl(INVALID_NAME));
    }

   @Test
    public void constructor_Should_CreateTeam_When_NameInBounds(){
        Team team1 = new TeamImpl(VALID_NAME);
        Assertions.assertEquals(VALID_NAME, team1.getName());
    }

    @Test
    public  void removePersonFromMembers_Should_ThrowException_When_PersonDoesNotExist(){
        Person person = repository.createPerson(VALID_NAME);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> team.removePersonFromMembers(person));
    }
    @Test
    public  void removePersonFromMembers_Should_RemovePerson_When_PersonExist(){
        Person person = repository.createPerson(VALID_NAME);
        team.addPersonToTeam(person);
        team.removePersonFromMembers(person);
        Assertions.assertEquals(0,team.getMembers().size());
    }
    @Test
    public void addPersonToTeam_Should_AddPersonToTeam(){
        Person person = repository.createPerson(VALID_NAME);
        team.addPersonToTeam(person);
        Assertions.assertEquals(1,team.getMembers().size());
    }
    @Test
    public void addBoardToTeam_Should_AddBoardToTeam(){
        Board board = new BoardImpl(VALID_NAME);
        team.addBoardToTeam(board);
        Assertions.assertEquals(1,team.getBoards().size());
    }
    @Test
    public void addToActivityHistory_Should_addToActivityHistory(){
        team.addToActivityHistory("test");
        Assertions.assertEquals(2,team.getActivityHistory().size());
    }
}