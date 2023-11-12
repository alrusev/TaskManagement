package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Story;
import models.enums.TaskStatus;

import java.util.List;

public class ChangeStoryStatusCommand implements Command {
    private final Repository repository;
    private static final int STORY_ID_INDEX = 1;
    private static final int NEW_STATUS_INDEX = 2;

    public ChangeStoryStatusCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        //storyId
        int storyId = Integer.parseInt(parameters.get(STORY_ID_INDEX));

        //newStatus
        TaskStatus newStatus = TaskStatus.valueOf(parameters.get(NEW_STATUS_INDEX));

        //Retrieve the Story from the repository
        Story story = repository.getStoryById(storyId);

        //Update the status
        story.updateStatus(newStatus);

        return String.format("Story status for bug with ID '%d' updated successfully. New status: %s", storyId, newStatus);
    }
}