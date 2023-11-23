package commands.showcommands;

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
    private final Repository repository;

    public ShowAllTeamBoardsCommand(Repository repository){
        this.repository = repository;
    }
    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        String teamName = parameters.get(TEAM_NAME_ID);
        Team team = repository.findElementByName(teamName, repository.getTeams(), "Team");
        List<Board> boards = team.getBoards();

        if (boards.isEmpty()) {
            return String.format("Team %s does not have any boards", teamName);
        } else {
            return String.format("Boards of %s:\n" + ListingHelpers.teamBoardsToString(boards), teamName);
        }
    }
}
