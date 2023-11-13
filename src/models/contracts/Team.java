package models.contracts;

import java.util.List;

public interface Team extends Nameable{
    List<Board> getBoards();
    List<Person> getMembers();
    void addPersonToTeam(Person person);
    void addBoardToTeam(Board board);
    void removePersonFromMembers(Person person);
}
