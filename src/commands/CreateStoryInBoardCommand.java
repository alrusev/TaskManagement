package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Board;
import models.contracts.Bug;
import models.contracts.Person;
import models.contracts.Story;
import models.enums.Priority;
import models.enums.Severity;
import models.enums.Size;
import models.enums.TaskStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateStoryInBoardCommand implements Command {
    private final static int EXPECTED_PARAMETERS_COUNT = 7;
    private final static String NO_SUCH_STATUS = "No such status";
    private final static String NO_SUCH_PRIORITY = "No such priority";
    private final static String NO_SUCH_SIZE = "No such size";
    private static final String STORY_CREATED_MESSAGE = "Story with ID %d was created.";

    private final Repository repository;

    public CreateStoryInBoardCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);
        String title = parameters.get(0);
        String description = parameters.get(1);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(2), Priority.class, NO_SUCH_PRIORITY);
        Size size = ParsingHelpers.tryParseEnum(parameters.get(3), Size.class, NO_SUCH_SIZE);
        TaskStatus status = ParsingHelpers.tryParseEnum(parameters.get(4), TaskStatus.class, NO_SUCH_STATUS);
        Person assignee = repository.findElementByName(parameters.get(5), repository.getPeople(), "person");
        Board board = repository.findElementByName(parameters.get(6), repository.getBoards(), "board");
        Story story = repository.createStory(title, description, priority, size, status, assignee);
        board.addTaskToBoard(story);
        return String.format(STORY_CREATED_MESSAGE, story.getId());
    }
}
