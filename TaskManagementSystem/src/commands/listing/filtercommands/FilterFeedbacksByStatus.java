package commands.listing.filtercommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Feedback;
import models.enums.FeedbackStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FilterFeedbacksByStatus implements Command {
    private static final int STATUS_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    private final List<Feedback> feedbacks;
    private final static String NO_SUCH_STATUS = "No such status";
    public static final String NO_RESULTS_MESSAGE = "No task with matching status!";



    public FilterFeedbacksByStatus(Repository repository){
        feedbacks = repository.getFeedbacks();
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        FeedbackStatus filterStatus = ParsingHelpers.tryParseEnum(parameters.get(STATUS_INDEX),
                FeedbackStatus.class, NO_SUCH_STATUS);
        Stream<Feedback> streamFeedback = feedbacks.stream()
                .filter(bug -> bug.getFeedbackStatus().toString().toUpperCase().equals(filterStatus.toString()));
        if (streamFeedback.findAny().isEmpty())
            return NO_RESULTS_MESSAGE;

        feedbacks.stream()
                .filter(feedback -> feedback.getFeedbackStatus().equals(filterStatus))
                .sorted(Comparator.comparing(feedback -> feedback.getTitle().toLowerCase()))
                .forEach(System.out::println);
        return "----- END -----";
    }
}
