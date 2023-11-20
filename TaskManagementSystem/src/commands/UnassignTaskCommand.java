package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Feedback;
import models.contracts.Task;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.List;

public class UnassignTaskCommand implements Command {
    public static final String NO_ASSIGNEE_ERROR = "Task has no assignee";
    public static final String FEEDBACKS_ASSIGNEE_ERROR = "Feedbacks cannot have assignees";
    private final Repository repository;
    private static final int ID_INDEX = 0;
    private static final int EXPECTED_PARAMETERS_COUNT = 1;
    public static final String TASK_UNASSIGNED_MESSAGE = "Item with ID '%d' unassigned.";

    public UnassignTaskCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int Id = ParsingHelpers.tryParseInteger(parameters.get(ID_INDEX), "ID");
        Task task = repository.findTaskById(Id,repository.getTasks());
        if (isFeedback(task))

            throw new IllegalArgumentException(FEEDBACKS_ASSIGNEE_ERROR);
        if (task.getAssignee() == null)
            throw new IllegalArgumentException(NO_ASSIGNEE_ERROR);

        task.getAssignee().removeTask(task);
        task.unAssignTask();

        return String.format(TASK_UNASSIGNED_MESSAGE, Id);
    }
    private boolean isFeedback(Task task){
        for (Feedback feedback : repository.getFeedbacks()) {
            if (feedback.getId()== task.getId())
                return true;
        }
        return false;
    }
}
