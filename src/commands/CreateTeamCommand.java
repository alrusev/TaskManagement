package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.TeamImpl;
import models.contracts.Board;
import models.contracts.Person;
import models.contracts.Team;

import java.util.ArrayList;
import java.util.List;

public class CreateTeamCommand implements Command {
    private static final int TEAM_NAME_INDEX = 0;

    private final Repository repository;

    public CreateTeamCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        String teamName = parameters.get(TEAM_NAME_INDEX);
        List<Person> members = new ArrayList<>();
        List<Board> boards = new ArrayList<>();

        if (isNameUnique(teamName)) {
            repository.createTeam(teamName, members, boards);
            return String.format("Team with name '%s' created successfully.", teamName);
        } else {
            return "Team name is not unique. Please choose a different Team name.";
        }
    }

    private boolean isNameUnique(String teamName) {
        for (Team team : repository.getAllTeams()) {
            if (team.getName().equals(teamName)) {
                return false; // Name is not unique
            }
        }
        return true; // Name is unique
    }
}
