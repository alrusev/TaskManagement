package commands.createcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Team;
import utils.ValidationHelpers;

import java.util.List;

public class CreateBoardCommand implements Command {
    private final static int EXPECTED_PARAMETERS_COUNT = 2;
    public static final String BOARD_CREATED_MESSAGE = "Board with name %s was created in team %s";
    private final Repository repository;

    public CreateBoardCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);
        String boardName = parameters.get(0);
        Team team = repository.findElementByName(parameters.get(1), repository.getTeams(), "Team");
        repository.createBoard(boardName, team);
        team.addToActivityHistory(String.format("Board with name %s added to team %s", boardName, team.getName()));
        return String.format(BOARD_CREATED_MESSAGE, boardName, team.getName());
    }
}
