package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.List;

public class UnassignBugCommand implements Command {
    private final Repository repository;
    private static final int BUG_ID_INDEX = 0;
    private static final int EXPECTED_PARAMETERS_COUNT = 1;
    public static final String BUG_UNASSIGNED_MESSAGE = "Bug with ID '%d' unassigned.";

    public UnassignBugCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int bugId = ParsingHelpers.tryParseInteger(parameters.get(BUG_ID_INDEX), "Bug ID");
        Bug bug = (Bug) repository.findTaskById(bugId);

        bug.unassign();
        return String.format(BUG_UNASSIGNED_MESSAGE, bugId);
    }
}
