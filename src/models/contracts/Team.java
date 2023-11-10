package models.contracts;

import java.util.List;

public interface Team {
    String getName();
    List<Board> getBoards();
    List<Person> getMembers();
}
