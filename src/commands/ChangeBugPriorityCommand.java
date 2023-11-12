package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.enums.Priority;

import java.util.List;

public class ChangeBugPriorityCommand implements Command {

    private final Repository repository;
    private static final int BUG_ID_INDEX = 1;
    private static final int NEW_PRIORITY_INDEX = 2;

    public ChangeBugPriorityCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        //bugId
        int bugId = Integer.parseInt(parameters.get(BUG_ID_INDEX));

        //newPriority
        Priority newPriority = Priority.valueOf(parameters.get(NEW_PRIORITY_INDEX));

        // Retrieve the Bug from the repository
        Bug bug = repository.getBugById(bugId);

        // Update the priority
        bug.updatePriority(newPriority);

        return String.format("Bug priority for bug with ID '%d' updated successfully. New priority: %s", bugId, newPriority);
    }

}
