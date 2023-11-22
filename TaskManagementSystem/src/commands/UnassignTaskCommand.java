package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.*;
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

        int id = ParsingHelpers.tryParseInteger(parameters.get(ID_INDEX), "ID");
        Task task = repository.findTaskById(id, repository.getTasks());
        if (isFeedback(task))
            throw new IllegalArgumentException(FEEDBACKS_ASSIGNEE_ERROR);
        if (isBug(task)){
            Bug bug = (Bug) task;
            if (bug.getAssignee() == null)
                throw new IllegalArgumentException(NO_ASSIGNEE_ERROR);
            bug.getAssignee().removeTask(task);
            bug.unAssignTask();
        }
        if (isStory(task)){
            Story story = (Story) task;
            if (story.getAssignee() == null)
                throw new IllegalArgumentException(NO_ASSIGNEE_ERROR);
            story.getAssignee().removeTask(task);
            story.unAssignTask();
        }
        return String.format(TASK_UNASSIGNED_MESSAGE, id);
    }

    private boolean isFeedback(Task task) {
        for (Feedback feedback : repository.getFeedbacks()) {
            if (feedback.getId() == task.getId())
                return true;
        }
        return false;
    }
    private boolean isBug(Task task) {
        for (Bug bug : repository.getBugs()) {
            if (bug.getId() == task.getId())
                return true;
        }
        return false;
    }
    private boolean isStory(Task task) {
        for (Story story : repository.getStories()) {
            if (story.getId() == task.getId())
                return true;
        }
        return false;
    }
}
