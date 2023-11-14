package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.contracts.Task;
import models.enums.Priority;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.List;

public class ChangeBugPriorityCommand implements Command {

    private static final String PRIORITY_SUCCESSFULLY_CHANGED = "Priority for bug with " +
            "ID '%d' updated successfully. New priority: %s";
    private static final String SAME_PRIORITY_ALREADY_SET = "The priority of bug with ID '%d' is already set to %s!";
    private static final String MISSING_BUG_ID = "No such Bug with ID '%d'!";
    private final Repository repository;
    private static final int BUG_ID_INDEX = 0;
    private static final int NEW_PRIORITY_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;
    private final static String NO_SUCH_PRIORITY = "No such priority";


    public ChangeBugPriorityCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //bugId
        int bugId = ParsingHelpers.tryParseInteger(parameters.get(BUG_ID_INDEX), "Bug ID");

        //newPriority
        Priority newPriority = ParsingHelpers.tryParseEnum(parameters.get(NEW_PRIORITY_INDEX), Priority.class,
                NO_SUCH_PRIORITY);

        // Retrieve the Bug from the repository
        Task task = repository.findTaskById(repository.getTasks(), bugId);
        String result;
        try {
            Bug bug = (Bug) task;
            try {
                if (bug.getPriority().equals(newPriority)) {
                    throw new IllegalArgumentException();
                }

                // Update the priority
                bug.updatePriority(newPriority);
                result = String.format(PRIORITY_SUCCESSFULLY_CHANGED, bugId, newPriority);
            } catch (IllegalArgumentException e) {
                result = String.format(SAME_PRIORITY_ALREADY_SET, bugId, newPriority);
            }
        } catch (ClassCastException cce) {
            result = String.format(MISSING_BUG_ID, bugId);
        }
        return result;
    }

}
