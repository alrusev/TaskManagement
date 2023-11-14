package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Team;
import utils.ListingHelpers;

import java.util.List;

public class ShowAllTeamsCommand implements Command {

    private final List<Team>teams;

    public ShowAllTeamsCommand(Repository repository){
        teams = repository.getTeams();
    }
    @Override
    public String execute(List<String> parameters) {
        if (teams.isEmpty()){
            return "There are no registered teams.";
        }
        return ListingHelpers.teamsToString(teams);
    }
}
