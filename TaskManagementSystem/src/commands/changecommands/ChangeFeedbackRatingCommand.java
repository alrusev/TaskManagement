package commands.changecommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Feedback;
import utils.ParsingHelpers;
import utils.ValidationHelpers;
import java.util.List;

public class ChangeFeedbackRatingCommand implements Command {

    private static final String RATING_ALREADY_SET = "The rating for feedback with ID '%d' is already set to %d!";
    private static final String FEEDBACK_RATING_UPDATED_SUCCESSFULLY = "Feedback rating for feedback with ID '%d' updated successfully. New rating: %s";
    private final Repository repository;
    private static final int FEEDBACK_ID_INDEX = 0;
    private static final int NEW_RATING_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;

    public ChangeFeedbackRatingCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //feedbackID
        int feedbackId = ParsingHelpers.tryParseInteger(parameters.get(FEEDBACK_ID_INDEX), "Feedback ID");

        //newRating
        int newRating = ParsingHelpers.tryParseInteger(parameters.get(NEW_RATING_INDEX), "New rating");

        // Retrieve the Feedback from the repository
        Feedback feedback = repository.findTaskById(feedbackId, repository.getFeedbacks());

        if (feedback.getRating() == newRating) {
            throw new IllegalArgumentException(String.format(RATING_ALREADY_SET, feedbackId, newRating));
        }
        //Update the rating
        feedback.updateRating(newRating);

        return String.format(FEEDBACK_RATING_UPDATED_SUCCESSFULLY, feedbackId, newRating);
    }
}
