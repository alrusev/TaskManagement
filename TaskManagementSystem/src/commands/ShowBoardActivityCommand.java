package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Board;
import utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class ShowBoardActivityCommand implements Command {

    private final Repository repository;

    private static final int BOARD_INDEX = 0;
    private static final int EXPECTED_PARAMETERS_COUNT = 1;

    public ShowBoardActivityCommand(Repository repository){
        this.repository = repository;
    }
    @Override
    public String execute(List<String> parameters) {

        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String boardName = parameters.get(BOARD_INDEX);

        Board board = repository.findElementByName(boardName, repository.getBoards(), "Board");
        List<String> result = new ArrayList<>();

        int nextId = 1;
        for (String history:board.getActivityHistory()) {
            result.add(nextId + ". " + history);
            nextId++;
        }

        return String.format("Board activity for %s - %s", boardName, String.join(", ", result).trim());
    }
}
