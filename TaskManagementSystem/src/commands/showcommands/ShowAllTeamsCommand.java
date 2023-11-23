package commands.showcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Team;
import utils.ListingHelpers;

import java.util.List;

public class ShowAllTeamsCommand implements Command {

    private Repository repository;

    public ShowAllTeamsCommand(Repository repository){
        this.repository = repository;
    }
    @Override
    public String execute(List<String> parameters) {
        if (repository.getTeams().isEmpty()){
            return "There are no registered teams.";
        }
        return ListingHelpers.teamsToString(repository.getTeams());
    }
}
