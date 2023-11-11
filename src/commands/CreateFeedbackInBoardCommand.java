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
    private final static int EXPECTED_PARAMETERS_COUNT = 3;
    private final static String NO_SUCH_STATUS = "No such status";
    private static final String FEEDBACK_CREATED_MESSAGE = "Feedback with ID %d was created.";

    private final Repository repository;
    private String title;
    private String description;
    private TaskStatus status;
    private Board board;
    public CreateFeedbackInBoardCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters,EXPECTED_PARAMETERS_COUNT);
        title = parameters.get(0);
        description = parameters.get(1);
        status = ParsingHelpers.tryParseEnum(parameters.get(2),TaskStatus.class,NO_SUCH_STATUS);
        board = repository.findBoardByName(parameters.get(3));
        Feedback feedback = repository.createFeedback(title,description,status);
        return String.format(FEEDBACK_CREATED_MESSAGE, feedback.getId());
    }
}
