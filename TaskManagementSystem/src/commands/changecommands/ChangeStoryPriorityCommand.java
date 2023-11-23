package commands.changecommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Story;
import models.enums.Priority;
import utils.ParsingHelpers;
import utils.ValidationHelpers;
import java.util.List;

public class ChangeStoryPriorityCommand implements Command {

    private static final String STORY_PRIORITY_ALREADY_SET = "The priority of story with ID '%d' is already set to %s!";
    private static final String STORY_PRIORITY_SUCCESSFULLY_CHANGED = "Priority for story with ID '%d' " +
            "updated successfully. New priority: %s";
    private final Repository repository;
    private static final int STORY_ID_INDEX = 0;
    private static final int NEW_PRIORITY_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;
    private final static String NO_SUCH_PRIORITY = "No such priority";

    public ChangeStoryPriorityCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //storyId
        int storyId = ParsingHelpers.tryParseInteger(parameters.get(STORY_ID_INDEX), "Story ID");

        //newPriority
        Priority newPriority = ParsingHelpers.tryParseEnum(parameters.get(NEW_PRIORITY_INDEX), Priority.class,
                NO_SUCH_PRIORITY);

        //Retrieve the Story from the repository
        Story story = repository.findTaskById(storyId, repository.getStories());

        if (story.getPriority().equals(newPriority)) {
            throw new IllegalArgumentException(String.format(STORY_PRIORITY_ALREADY_SET, storyId, newPriority));
        }
        //Update the priority
        story.updatePriority(newPriority);

        return String.format(STORY_PRIORITY_SUCCESSFULLY_CHANGED, storyId, newPriority);
    }
}
