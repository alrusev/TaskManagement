package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Board;
import models.contracts.HistoryEntry;
import models.contracts.Task;
import utils.ValidationHelpers;


import java.util.List;

public class ShowBoardActivityCommand implements Command {

    private final Repository repository;

    private static final int BOARD_INDEX = 0;
    private static final int EXPECTED_PARAMETERS_COUNT = 1;

    public ShowBoardActivityCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {

        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String boardName = parameters.get(BOARD_INDEX);

        Board board = repository.findElementByName(boardName, repository.getBoards(), "Board");

        StringBuilder result = new StringBuilder();
        result.append(String.format("Activity for board %s:\n", board.getName()));

        if (board.getActivityHistory().isEmpty())
            result.append(String.format("No activity from board %s yet", board.getName()));
        for (Task task : board.getTasks()) {
            result.append(String.format("Task with ID %d",task.getId())).append(System.lineSeparator());
                for (HistoryEntry entry : task.getHistory()) {
                    result.append(entry.toString()).append(System.lineSeparator());
                }
            }

        return result.toString().trim();
    }
}
