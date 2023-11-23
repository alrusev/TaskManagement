package commands.createcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Board;
import models.contracts.Feedback;
import utils.ParsingHelpers;
import utils.ValidationHelpers;


import java.util.List;

public class CreateFeedbackInBoardCommand implements Command {
    private final static int EXPECTED_PARAMETERS_COUNT = 4;
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
        int rating = ParsingHelpers.tryParseInteger(parameters.get(2),"rating");
        Board board = repository.findElementByName(parameters.get(3), repository.getBoards(), "board");
        Feedback feedback = repository.createFeedback(title, description,rating);
        board.addTaskToBoard(feedback);
        board.addToActivityHistory(String.format("Feedback with title %s added to board %s", title, board.getName()));
        return String.format(FEEDBACK_CREATED_MESSAGE, feedback.getId());
    }
}
