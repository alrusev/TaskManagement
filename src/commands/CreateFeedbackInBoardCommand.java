package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Board;
import models.contracts.Feedback;
import models.enums.TaskStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;


import java.util.List;

public class CreateFeedbackInBoardCommand implements Command {
    private final static int EXPECTED_PARAMETERS_COUNT = 4;
    private final static String NO_SUCH_STATUS = "No such status";
    private static final String FEEDBACK_CREATED_MESSAGE = "Feedback with ID %d was created.";

    private final Repository repository;

    public CreateFeedbackInBoardCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);
        String title = parameters.get(0);
        String description = parameters.get(1);
        TaskStatus status = ParsingHelpers.tryParseEnum(parameters.get(2), TaskStatus.class, NO_SUCH_STATUS);
        Board board = repository.findElementByName(parameters.get(3), repository.getBoards(),"board");
        Feedback feedback = repository.createFeedback(title, description, status);
        board.addTaskToBoard(feedback);
        return String.format(FEEDBACK_CREATED_MESSAGE, feedback.getId());
    }
}
