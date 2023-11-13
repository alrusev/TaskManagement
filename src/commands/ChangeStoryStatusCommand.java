package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import exceptions.InvalidUserInputException;
import models.contracts.Story;
import models.contracts.Task;
import models.enums.TaskStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;
import java.util.List;

public class ChangeStoryStatusCommand implements Command {
    private static final String STORY_STATUS_ERROR_MESSAGE = "The status of a story can be - NOT DONE, IN PROGRESS or DONE!";
    private static final String STORY_STATUS_ALREADY_SET = "The status of story with ID '%d' is already set to %s";
    private static final String STORY_STATUS_SUCCESSFULLY_CHANGED = "Status for story with ID '%d' updated successfully. New status: %s";
    private static final String MISSING_STORY_ID = "No such story with ID '%d'";
    private final Repository repository;
    private static final int STORY_ID_INDEX = 0;
    private static final int NEW_STATUS_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;
    private final static String NO_SUCH_STATUS = "No such status";


    public ChangeStoryStatusCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //storyId
        int storyId = ParsingHelpers.tryParseInteger(parameters.get(STORY_ID_INDEX), "Story ID");

        //newStatus
        TaskStatus newStatus = ParsingHelpers.tryParseEnum(parameters.get(NEW_STATUS_INDEX), TaskStatus.class, NO_SUCH_STATUS);

        //Retrieve the Story from the repository
        Task task = repository.findTaskById(repository.getTasks(), storyId);
        String result;
        try {
            Story story = (Story) task;
            try {
                if (!newStatus.equals(TaskStatus.NOTDONE) && !newStatus.equals(TaskStatus.INPROGRESS) &&
                        !newStatus.equals(TaskStatus.DONE)) {
                    throw new IllegalArgumentException();
                }
                if (story.getStatus().equals(newStatus)) {
                    throw new InvalidUserInputException();
                }
                //Update the status
                result = String.format(STORY_STATUS_SUCCESSFULLY_CHANGED, storyId, newStatus);
                task.changeStatus(newStatus);
            } catch (IllegalArgumentException e) {
                result = STORY_STATUS_ERROR_MESSAGE;
            } catch (InvalidUserInputException e) {
                result = String.format(STORY_STATUS_ALREADY_SET, storyId, newStatus);
            }
        }
        catch (ClassCastException cce) {
            result = String.format(MISSING_STORY_ID, storyId);
        }
        return result;
    }
}