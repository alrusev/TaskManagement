package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.TaskImpl;
import models.contracts.Feedback;
import models.contracts.Task;
import models.enums.TaskStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.List;

public class ChangeFeedbackStatusCommand implements Command {

    private final Repository repository;
    private static final int FEEDBACK_ID_INDEX = 0;
    private static final int NEW_STATUS_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;

    public ChangeFeedbackStatusCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //feedbackId
        int feedbackId = ParsingHelpers.tryParseInteger(parameters.get(FEEDBACK_ID_INDEX), "Feedback ID");

        //newStatus
        TaskStatus newStatus = TaskStatus.valueOf(parameters.get(NEW_STATUS_INDEX));

        //Retrieve the Feedback from the repository
        Task task = repository.findTaskById(repository.getTasks(), feedbackId);

        //Update the status
        task.changeStatus(newStatus);

        return String.format("Feedback status for feedback with ID '%d' updated successfully. New status: %s",
                feedbackId, newStatus);
    }
}
