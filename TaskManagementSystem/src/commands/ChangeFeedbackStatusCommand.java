package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import exceptions.InvalidUserInputException;
import models.contracts.Feedback;
import models.contracts.Task;
import models.enums.FeedbackStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.List;

public class ChangeFeedbackStatusCommand implements Command {

    private static final String FEEDBACK_STATUS_SUCCESSFULLY_CHANGED = "Status for feedback with ID '%d' " +
            "updated successfully. New status: %s";
    private static final String FEEDBACK_STATUS_ERROR_MESSAGE = "The status of a feedback can be - " +
            "NEW, UNSCHEDULED, SCHEDULED or DONE!";
    private static final String FEEDBACK_STATUS_ALREADY_SET = "The status of a feedback with id '%d' is " +
            "already set to %s";
    public static final String MISSING_FEEDBACK_ID = "No such Feedback with ID '%d'";
    private final Repository repository;
    private static final int FEEDBACK_ID_INDEX = 0;
    private static final int NEW_STATUS_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;
    private final static String NO_SUCH_STATUS = "No such status";

    public ChangeFeedbackStatusCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //feedbackId
        int feedbackId = ParsingHelpers.tryParseInteger(parameters.get(FEEDBACK_ID_INDEX), "Feedback ID");

        //newStatus
        FeedbackStatus newStatus = ParsingHelpers.tryParseEnum(parameters.get(NEW_STATUS_INDEX),
                FeedbackStatus.class, NO_SUCH_STATUS);

        //Retrieve the Feedback from the repository
        Feedback task = repository.findTaskById(feedbackId,repository.getFeedbacks());
        String result;

        try {
            Feedback feedback = (Feedback) task;
            try {
                if (!newStatus.equals(FeedbackStatus.NEW) && !newStatus.equals(FeedbackStatus.UNSCHEDULED)
                        && !newStatus.equals(FeedbackStatus.SCHEDULED) && !newStatus.equals(FeedbackStatus.DONE)) {
                    throw new IllegalArgumentException();
                }
                if (feedback.getFeedbackStatus().equals(newStatus)) {
                    throw new InvalidUserInputException();
                }
                //Update the status
                result = String.format(FEEDBACK_STATUS_SUCCESSFULLY_CHANGED, feedbackId, newStatus);
                feedback.setFeedbackStatus(newStatus);
            } catch (IllegalArgumentException e) {
                result = FEEDBACK_STATUS_ERROR_MESSAGE;
            } catch (InvalidUserInputException ie) {
                result = String.format(FEEDBACK_STATUS_ALREADY_SET, feedbackId, newStatus);
            }
        } catch (ClassCastException cce) {
            result = String.format(MISSING_FEEDBACK_ID, feedbackId);
        }

        return result;
    }
}
