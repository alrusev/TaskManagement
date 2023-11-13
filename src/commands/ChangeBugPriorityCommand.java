package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.contracts.Task;
import models.enums.Priority;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class ChangeBugPriorityCommand implements Command {

    private final Repository repository;
    private static final int BUG_ID_INDEX = 0;
    private static final int NEW_PRIORITY_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;

    public ChangeBugPriorityCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //bugId
        int bugId = ParsingHelpers.tryParseInteger(parameters.get(BUG_ID_INDEX), "Bug ID");

        //newPriority
        Priority newPriority = Priority.valueOf(parameters.get(NEW_PRIORITY_INDEX));

        // Retrieve the Bug from the repository
        Task task = repository.findTaskById(repository.getTasks(), bugId);
        Bug bug = (Bug) task;

        // Update the priority
        bug.updatePriority(newPriority);

        return String.format("Bug priority for bug with ID '%d' updated successfully. New priority: %s", bugId, newPriority);
    }

}
