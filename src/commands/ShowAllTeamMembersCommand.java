package commands;
import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Person;
import models.contracts.Team;
import utils.ListingHelpers;
import utils.ValidationHelpers;
import java.util.List;

public class ShowAllTeamMembersCommand implements Command {

    private static final int TEAM_NAME_ID = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    private final List<Person>people;
    private final Repository repository;

    public ShowAllTeamMembersCommand(Repository repository){
        people = repository.getPeople();
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        String teamName = parameters.get(TEAM_NAME_ID);
        Team team = repository.findElementByName(teamName, repository.getTeams(), "Team");

        return String.format("Members of %s - " + ListingHelpers.teamMembersToString(people), teamName);
    }
}
