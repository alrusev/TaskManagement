package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Board;
import models.contracts.Person;

import java.util.ArrayList;
import java.util.List;

public class CreateBoardCommand implements Command {
    private static final int TEAM_NAME_INDEX = 0;
    private static final int BOARD_NAME_INDEX = 1;
    private final Repository repository;

    public CreateBoardCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        String teamName = parameters.get(TEAM_NAME_INDEX);
        String boardName = parameters.get(BOARD_NAME_INDEX);
        List<Person> members = new ArrayList<>();
        return null;

//        if (isNameUnique(teamName, boardName)) {
//            repository.createBoard(teamName, boardName, members, new ArrayList<>());
//            return String.format("Board with name '%s' created successfully for team '%s'.", boardName, teamName);
//        } else {
//            return "Board name is not unique within the team. Please choose a different board name.";
//        }
    }

//    private boolean isNameUnique(String teamName, String boardName) {
//        for (Board board : repository.getBoardsByTeam(teamName)) {
//            if (board.getName().equals(boardName)) {
//                return false; // Name is not unique
//            }
//        }
//        return true; // Name is unique
//    }
}
