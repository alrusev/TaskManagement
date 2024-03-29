package commands.changecommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Story;
import models.enums.Size;
import utils.ParsingHelpers;
import utils.ValidationHelpers;
import java.util.List;

public class ChangeStorySizeCommand implements Command {
    private static final String STORY_SIZE_ALREADY_DEFINED = "The size for story with ID '%d' is already set to %s!";
    private static final String STORY_SIZE_SUCCESSFULLY_CHANGED = "Size for story with ID" +
            " '%d' updated successfully. New size: %s";
    private final Repository repository;
    private static final int STORY_ID_INDEX = 0;
    private static final int NEW_SIZE_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;
    private final static String NO_SUCH_SIZE = "No such size";

    public ChangeStorySizeCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //storyId
        int storyId = ParsingHelpers.tryParseInteger(parameters.get(STORY_ID_INDEX), "Story ID");

        //newSize
        Size newSize = ParsingHelpers.tryParseEnum(parameters.get(NEW_SIZE_INDEX), Size.class, NO_SUCH_SIZE);

        //Retrieve the Story from the repository
        Story story = repository.findTaskById(storyId, repository.getStories());

        if (story.getSize().equals(newSize)) {
            throw new IllegalArgumentException(String.format(STORY_SIZE_ALREADY_DEFINED, storyId, newSize));
        }
        //Update the priority
        story.updateSize(newSize);

        return String.format(STORY_SIZE_SUCCESSFULLY_CHANGED, storyId, newSize);
    }
}
