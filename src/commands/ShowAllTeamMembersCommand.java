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
    private final Repository repository;

    public ShowAllTeamMembersCommand(Repository repository){
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        String teamName = parameters.get(TEAM_NAME_ID);
        Team team = repository.findElementByName(teamName, repository.getTeams(), "Team");
        List<Person>people = team.getMembers();

        if (people.isEmpty()){
            return String.format("Team %s does not have any members assigned yet.", teamName);
        }
        else {
            return String.format("Members of %s - " + ListingHelpers.teamMembersToString(people), teamName);

        }
    }
}
