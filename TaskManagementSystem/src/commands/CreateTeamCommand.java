package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import utils.ValidationHelpers;

import java.util.List;

public class CreateTeamCommand implements Command {
    private final static int EXPECTED_PARAMETERS_COUNT = 1;
    public static final String TEAM_CREATED_MESSAGE = "Team with name '%s' created successfully.";

    private final Repository repository;

    public CreateTeamCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);
        String teamName = parameters.get(0);
        repository.createTeam(teamName);
        return String.format(TEAM_CREATED_MESSAGE, teamName);
    }
}

