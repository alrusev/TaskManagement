package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Team;
import utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class ShowTeamActivityCommand implements Command {

    private final Repository repository;
    private static final int TEAM_INDEX = 0;
    private static final int EXPECTED_PARAMETERS_COUNT = 1;

    public ShowTeamActivityCommand(Repository repository) {
        this.repository = repository;
    }
    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String teamName = parameters.get(TEAM_INDEX);

        Team team = repository.findElementByName(teamName, repository.getTeams(), "Team");

        List<String> result = new ArrayList<>();

        int nextId = 1;
        for (String history:team.getActivityHistory()) {
            result.add(nextId + ". " + history);
            nextId++;
        }

        return String.format("Team activity for %s - %s", teamName, String.join(", ", result).trim());
    }
}
