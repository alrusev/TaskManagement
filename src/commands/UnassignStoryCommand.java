package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Story;
import utils.ParsingHelpers;
import utils.ValidationHelpers;
import java.util.List;

public class UnassignStoryCommand implements Command {
    private final Repository repository;
    private static final int STORY_ID_INDEX = 0;
    private static final int EXPECTED_PARAMETERS_COUNT = 1;
    public static final String STORY_UNASSIGNED_MESSAGE = "Story with ID '%d' unassigned.";

    public UnassignStoryCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int storyId = ParsingHelpers.tryParseInteger(parameters.get(STORY_ID_INDEX), "Story ID");
        Story story = (Story) repository.findTaskById(repository.getTasks(), storyId);

        story.unassign();
        return String.format(STORY_UNASSIGNED_MESSAGE, storyId);
    }
}
