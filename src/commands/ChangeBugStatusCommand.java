package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.contracts.Task;
import models.enums.TaskStatus;
import java.util.List;

public class ChangeBugStatusCommand implements Command {

    private final Repository repository;
    private static final int BUG_ID_INDEX = 1;
    private static final int NEW_STATUS_INDEX = 2;

    public ChangeBugStatusCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        //bugId
        int bugId = Integer.parseInt(parameters.get(BUG_ID_INDEX));

        //newStatus
        TaskStatus newStatus = TaskStatus.valueOf(parameters.get(NEW_STATUS_INDEX));

        // Retrieve the Bug from the repository
        Task task = repository.findTaskById(repository.getTasks(), bugId);
        Bug bug = (Bug) task;
        String result = "";

        // Update the status
        if (newStatus.equals(TaskStatus.ACTIVE)) {
            result = String.format("Bug with ID '%d' reopened and marked as active.", bugId);
            bug.reopenBug();
        } else {
            result = String.format("Bug with ID '%d' marked as Done", bugId);
            bug.markAsDone();
        }

        return result;
    }
}
