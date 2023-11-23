package commands.createcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Board;
import models.contracts.Bug;
import models.enums.Priority;
import models.enums.Severity;
import utils.ParsingHelpers;
import utils.ValidationHelpers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateBugInBoardCommand implements Command {
    private final static int EXPECTED_PARAMETERS_COUNT = 6;
    private final static String NO_SUCH_PRIORITY = "No such priority";
    private final static String NO_SUCH_SEVERITY = "No such severity";
    private static final String BUG_CREATED_MESSAGE = "Bug with ID %d was created.";

    private final Repository repository;

    public CreateBugInBoardCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);
        String title = parameters.get(0);
        String description = parameters.get(1);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(2), Priority.class, NO_SUCH_PRIORITY);
        Severity severity = ParsingHelpers.tryParseEnum(parameters.get(3), Severity.class, NO_SUCH_SEVERITY);
        ArrayList<String> stepsToReproduce = new ArrayList<>(Arrays.asList(parameters.get(4).split(",")));
        Board board = repository.findElementByName(parameters.get(5), repository.getBoards(), "Board");
        Bug bug = repository.createBug(title, description, priority, severity, stepsToReproduce);
        board.addTaskToBoard(bug);
        board.addToActivityHistory(String.format("Bug with title %s added to board %s", title, board.getName()));
        return String.format(BUG_CREATED_MESSAGE, bug.getId());
    }
}
