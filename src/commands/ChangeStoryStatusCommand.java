package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Story;
import models.contracts.Task;
import models.enums.TaskStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.List;

public class ChangeStoryStatusCommand implements Command {
    private final Repository repository;
    private static final int STORY_ID_INDEX = 0;
    private static final int NEW_STATUS_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;

    public ChangeStoryStatusCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //storyId
        int storyId = ParsingHelpers.tryParseInteger(parameters.get(STORY_ID_INDEX), "Story ID");

        //newStatus
        TaskStatus newStatus = TaskStatus.valueOf(parameters.get(NEW_STATUS_INDEX));

        //Retrieve the Story from the repository
        Task task = repository.findTaskById(repository.getTasks(), storyId);

        //Update the status
        task.changeStatus(newStatus);

        return String.format("Story status for bug with ID '%d' updated successfully. New status: %s", storyId, newStatus);
    }
}