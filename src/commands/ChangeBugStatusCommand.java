package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import exceptions.InvalidUserInputException;
import models.contracts.Bug;
import models.contracts.Task;
import models.enums.TaskStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.List;

public class ChangeBugStatusCommand implements Command {

    private static final String BUS_STATUS_ERROR = "The bug status can be either ACTIVE or DONE!";
    private static final String REOPEN_BUG = "Bug with ID '%d' reopened and marked as active.";
    private static final String BUG_DONE_MESSAGE = "Bug with ID '%d' marked as Done";
    private static final String ALREADY_SET_STATUS = "The status of a bug with ID %d is already set to %s!";
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
        TaskStatus status = ParsingHelpers.tryParseEnum(parameters.get(NEW_STATUS_INDEX),
                TaskStatus.class, NO_SUCH_STATUS);

        // Retrieve the Bug from the repository
        Task task = repository.findTaskById(repository.getTasks(), bugId);
        String result;
        try {
            Bug bug = (Bug) task;
            try {
                if (!status.equals(TaskStatus.ACTIVE) && !status.equals(TaskStatus.DONE)) {
                    throw new IllegalArgumentException();
                }
                if (bug.getStatus().equals(status)) {
                    throw new InvalidUserInputException();
                }
                // Update the status
                if (status.equals(TaskStatus.ACTIVE)) {
                    result = String.format(REOPEN_BUG, bugId);
                    bug.reopenBug();
                } else {
                    result = String.format(BUG_DONE_MESSAGE, bugId);
                    bug.markAsDone();
                }
            } catch (IllegalArgumentException e) {
                result = BUS_STATUS_ERROR;
            } catch (InvalidUserInputException e) {
                result = String.format(ALREADY_SET_STATUS, bugId, status);
            }
        } catch (ClassCastException cce) {
            result = String.format("No such Bug with ID '%d'!", bugId);
        }

        return result;
    }
}
