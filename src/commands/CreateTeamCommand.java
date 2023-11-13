package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import exceptions.TheNameIsNotUniqueException;
import utils.ValidationHelpers;

import java.util.List;

public class CreateTeamCommand implements Command {
    public static final String NAME_OF_TEAM_NOT_UNIQUE = "The name of the team is not unique in the system";

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
        if (!repository.isNameUniqueInSystem(teamName)) {
            throw new TheNameIsNotUniqueException(NAME_OF_TEAM_NOT_UNIQUE);
        }
        repository.createTeam(teamName);
        return String.format(TEAM_CREATED_MESSAGE, teamName);

    }
}

