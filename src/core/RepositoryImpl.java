package core;

import core.contracts.Repository;
import models.PersonImpl;
import models.TeamImpl;
import models.contracts.*;

import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {

    private final List<Team> teams;
    private final List<Person> people;
    private final List<Board> boards;

    public RepositoryImpl() {
        this.teams = new ArrayList<>();
        this.people = new ArrayList<>();
        this.boards = new ArrayList<>();
    }

    @Override
    public Task getTaskById(String taskId) {
        return null;
    }

    @Override
    public void addTask(Task task) {

    }

    @Override
    public void removeTask(Task task) {

    }

    @Override
    public List<Comment> getAllComments() {
        return null;
    }

    @Override
    public void addComment(Comment comment) {

    }

    @Override
    public List<Team> getAllTeams() {
        return new ArrayList<>(teams);
    }

    @Override
    public Team getTeamByName(String teamName) {
        return null;
    }

    @Override
    public void addTeam(Team team) {

    }

    @Override
    public void removeTeam(Team team) {

    }


    @Override
    public List<Board> getAllBoards() {
        return new ArrayList<>(boards);
    }



    @Override
    public Board getBoardByName(String boardName) {
        return null;
    }

    @Override
    public void addBoard(Board board) {
        boards.add(board);
    }
    private Team findTeamByName(String teamName) {
        for (Team team : teams) {
            if (team.getName().equals(teamName)) {
                return team;
            }
        }
        return null;
    }

    @Override
    public void removeBoard(Board board) {

    }

    @Override
    public List<Person> getAllPeople() {
        return new ArrayList<>(people);
    }

    @Override
    public Person getPersonByName(String personName) {
        return null;
    }

    @Override
    public void addPerson(Person person) {
        people.add(person);
    }

    @Override
    public void removePerson(Person person) {

    }

    @Override
    public void createTeam(String teamName, List<Person> members, List<Board> boards) {
        Team newTeam = new TeamImpl(teamName, new ArrayList<>(members), new ArrayList<>(boards));
        teams.add(newTeam);
    }




    @Override
    public void createPerson(String personName) {
        Person newPerson = new PersonImpl(personName);
        people.add(newPerson);
    }

}
