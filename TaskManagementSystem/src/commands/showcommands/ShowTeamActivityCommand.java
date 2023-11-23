package commands.showcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Board;
import models.contracts.Person;
import models.contracts.Team;
import utils.ValidationHelpers;
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

        List<Person> members = team.getMembers();
        StringBuilder result = new StringBuilder();

        result.append("TEAM: ").append(team.getName().toUpperCase()).append(System.lineSeparator());
        result.append("PEOPLE -->\n");
        if (members.isEmpty()) {
            result.append(String.format("There are no people added to team %s\n", team.getName()));
        }
        for (Person member : members) {
            result.append("### ").append(member.getName())
                    .append(" ###").append(System.lineSeparator())
                    .append("〜〜〜〜〜〜〜〜〜〜").append(System.lineSeparator());
            int nextID = 1;
            for (String element:member.getActivityHistory()) {
                result.append(nextID++).append(". ").append(element).append(System.lineSeparator());
            }
            result.append("〜〜〜〜〜〜〜〜〜〜").append(System.lineSeparator());
        }
        result.append("BOARDS --> \n");
        for (Board board : team.getBoards()) {
            result.append("### ").append(board.getName())
                    .append(" ###").append(System.lineSeparator())
                    .append("〜〜〜〜〜〜〜〜〜〜").append(System.lineSeparator());
            int nextID = 1;
            if (board.getActivityHistory().isEmpty()){
                result.append(String.format("There is no Board activity for %s\n", board.getName()));
            }
            for (String element:board.getActivityHistory()) {
                result.append(nextID++).append(". ").append(element).append(System.lineSeparator());
            }
            result.append("〜〜〜〜〜〜〜〜〜〜").append(System.lineSeparator());
        }
        return result.toString().trim();
    }
}