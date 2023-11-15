package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Board;
import models.contracts.Team;
import utils.ListingHelpers;
import utils.ValidationHelpers;

import java.util.List;

public class ShowAllTeamBoardsCommand implements Command {

    private static final int TEAM_NAME_ID = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    private final List<Board>boards;
    private final Repository repository;

    public ShowAllTeamBoardsCommand(Repository repository){
        boards = repository.getBoards();
        this.repository = repository;
    }
    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        String teamName = parameters.get(TEAM_NAME_ID);
        Team team = repository.findElementByName(teamName, repository.getTeams(), "Team");

        return String.format("Boards of %s - " + ListingHelpers.teamBoardsToString(boards), teamName);
    }
}
