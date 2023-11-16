package models;

import models.contracts.Board;
import models.contracts.Person;
import models.contracts.Team;
import utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class TeamImpl implements Team {
    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 15;
    private static final String NAME_ERROR_MESSAGE =
            String.format("Name must be between %d and %d symbols", NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    private static final String NO_SUCH_MEMBER = "There is no such member in this team";
    private String name;
    private List<Person> members;
    private List<Board> boards;

    public TeamImpl(String name) {
        setName(name);
        this.members = new ArrayList<>();
        this.boards = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Person> getMembers() {
        return new ArrayList<>(members);
    }

    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    private void setName(String name) {
        ValidationHelpers.validateIntRange(name.length(), NAME_MIN_LENGTH, NAME_MAX_LENGTH, NAME_ERROR_MESSAGE);
        this.name = name;
    }

    @Override
    public void removePersonFromMembers(Person person) {
        if (!members.contains(person))
            throw new IllegalArgumentException(NO_SUCH_MEMBER);
        members.remove(person);
    }

    @Override
    public void addPersonToTeam(Person person) {
        this.members.add(person);
    }

    @Override
    public void addBoardToTeam(Board board) {
        this.boards.add(board);
    }
}
