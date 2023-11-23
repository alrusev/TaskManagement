package commands.changecommands;

import commands.contracts.Command;
import core.contracts.Repository;
import exceptions.InvalidUserInputException;
import models.contracts.Story;
import models.enums.StoryStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;
import java.util.List;

public class ChangeStoryStatusCommand implements Command {
    private static final String STORY_STATUS_ERROR_MESSAGE = "The status of a story can be - NOT DONE, IN PROGRESS or DONE!";
    private static final String STORY_STATUS_ALREADY_SET = "The status of story with ID '%d' is already set to %s";
    private static final String STORY_STATUS_SUCCESSFULLY_CHANGED = "Status for story with ID '%d' updated successfully. New status: %s";
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
        StoryStatus newStatus = ParsingHelpers.tryParseEnum(parameters.get(NEW_STATUS_INDEX), StoryStatus.class, NO_SUCH_STATUS);

        //Retrieve the Story from the repository
        Story story = repository.findTaskById(storyId, repository.getStories());

        if (!newStatus.equals(StoryStatus.NOTDONE) && !newStatus.equals(StoryStatus.INPROGRESS) &&
                !newStatus.equals(StoryStatus.DONE)) {
            throw new IllegalArgumentException(STORY_STATUS_ERROR_MESSAGE);
        }
        if (story.getStoryStatus().equals(newStatus)) {
            throw new InvalidUserInputException(String.format(STORY_STATUS_ALREADY_SET, storyId, newStatus));
        }
        //Update the status
        story.setStoryStatus(newStatus);

        return String.format(STORY_STATUS_SUCCESSFULLY_CHANGED, storyId, newStatus);
    }
}