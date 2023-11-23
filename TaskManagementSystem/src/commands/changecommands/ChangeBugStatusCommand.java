package commands.changecommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.enums.BugStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;
import java.util.List;

public class ChangeBugStatusCommand implements Command {

    private static final String REOPEN_BUG = "Bug with ID '%d' reopened and marked as active.";
    private static final String BUG_DONE_MESSAGE = "Bug with ID '%d' marked as Done";
    private final Repository repository;
    private static final int BUG_ID_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 2;


    public ChangeBugStatusCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //bugId & newStatus
        int bugId = ParsingHelpers.tryParseInteger(parameters.get(BUG_ID_INDEX), "Bug ID");

        // Retrieve the Bug from the repository
        Bug bug = repository.findTaskById(bugId, repository.getBugs());

        // Update the status
        bug.changeBugStatus();
        String result;
        if (bug.getBugStatus().equals(BugStatus.ACTIVE)){
            result = String.format(REOPEN_BUG, bugId);
        }
        else result = String.format(BUG_DONE_MESSAGE, bugId);
        return result;
    }
}