package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Feedback;

import java.util.List;

public class ChangeFeedbackRatingCommand implements Command {

    private final Repository repository;
    private static final int FEEDBACK_ID_INDEX = 1;
    private static final int NEW_RATING_INDEX = 2;

    public ChangeFeedbackRatingCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        //feedbackID
        int feedbackID = Integer.parseInt(parameters.get(FEEDBACK_ID_INDEX));

        //newRating
        int newRating = Integer.parseInt(parameters.get(NEW_RATING_INDEX));

        // Retrieve the Feedback from the repository
        Feedback feedback = repository.getFeedbackById(feedbackID);

        //Update the rating
        feedback.updateRating(newRating);

        return String.format("Feedback rating for feedback with ID '%d' updated successfully. New rating: %s", feedbackID, newRating);
    }
}
