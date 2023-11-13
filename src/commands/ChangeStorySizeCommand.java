package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Story;
import models.contracts.Task;
import models.enums.Size;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.List;

public class ChangeStorySizeCommand implements Command {
    private final Repository repository;
    private static final int STORY_ID_INDEX = 0;
    private static final int NEW_SIZE_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;

    public ChangeStorySizeCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //storyId
        int storyId = ParsingHelpers.tryParseInteger(parameters.get(STORY_ID_INDEX), "Story ID");

        //newSize
        Size newSize = Size.valueOf(parameters.get(NEW_SIZE_INDEX));

        //Retrieve the Story from the repository
        Task task = repository.findTaskById(repository.getTasks(), storyId);
        Story story = (Story) task;

        //Update the priority
        story.updateSize(newSize);

        return String.format("Story size for bug with ID '%d' updated successfully. New size: %s", storyId, newSize);
    }
}
