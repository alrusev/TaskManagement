package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Person;
import models.contracts.Team;
import utils.ValidationHelpers;

import java.util.List;

public class AddPersonToTeamCommand implements Command {
    private static final int PERSON_NAME_INDEX = 0;
    private static final int TEAM_NAME_INDEX = 1;
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private final Repository repository;

    public AddPersonToTeamCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String personName = parameters.get(PERSON_NAME_INDEX);
        String teamName = parameters.get(TEAM_NAME_INDEX);

        return addPersonToTeam(personName, teamName);
    }

    private String addPersonToTeam(String personName, String teamName) {
        Person person = repository.findElementByName(personName, repository.getPeople(), "Person");
        Team team = repository.findElementByName(teamName, repository.getTeams(), "Team");

        if (team.getMembers().contains(person)) {
            return String.format("Person %s is already a member of team %s.", personName, teamName);
        }

        team.addPersonToTeam(person);
        person.addToActivityHistory(String.format("Added to team %s", teamName));
        return String.format("Person %s added to team %s successfully.", personName, teamName);
    }
}
