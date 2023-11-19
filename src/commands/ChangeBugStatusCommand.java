package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import exceptions.InvalidUserInputException;
import models.contracts.Bug;
import models.contracts.Task;
import models.enums.BugStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.List;

public class ChangeBugStatusCommand implements Command {

    private static final String BUG_STATUS_ERROR = "The bug status can be either ACTIVE or DONE!";
    private static final String REOPEN_BUG = "Bug with ID '%d' reopened and marked as active.";
    private static final String BUG_DONE_MESSAGE = "Bug with ID '%d' marked as Done";
    private final Repository repository;
    private static final int BUG_ID_INDEX = 0;
    private static final int NEW_STATUS_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;
    private final static String NO_SUCH_STATUS = "No such status";


    public ChangeBugStatusCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //bugId
        int bugId = ParsingHelpers.tryParseInteger(parameters.get(BUG_ID_INDEX), "Bug ID");

        //newStatus
        BugStatus status = ParsingHelpers.tryParseEnum(parameters.get(NEW_STATUS_INDEX),
                BugStatus.class, NO_SUCH_STATUS);

        // Retrieve the Bug from the repository
        Task task = repository.findTaskById(bugId);
        String result = "";
        try {
            Bug bug = (Bug) task;
            try {
                if (!status.equals(BugStatus.ACTIVE) && !status.equals(BugStatus.DONE)) {
                    throw new IllegalArgumentException();
                }
                if (status.equals(bug.getBugStatus())){
                    throw new InvalidUserInputException(String.format("Bug status already set to %s", bug.getBugStatus()));
                }
                // Update the status
                if (status.equals(BugStatus.DONE)) {
                    result = String.format(BUG_DONE_MESSAGE, bugId);
                    bug.markAsDone();
                }
                if (status.equals(BugStatus.ACTIVE)){
                    result = String.format(REOPEN_BUG, bugId);
                    bug.reopenBug();
                }
            } catch (IllegalArgumentException e) {
                result = BUG_STATUS_ERROR;
            }
        } catch (ClassCastException cce) {
            result = String.format("No such Bug with ID '%d'!", bugId);
        }

        return result;
    }
}
