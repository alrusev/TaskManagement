package commands.listing;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Feedback;
import models.contracts.Story;
import models.enums.FeedbackStatus;
import models.enums.StoryStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class FilterFeedbacksByStatus implements Command {
    private static final int STATUS_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    private final List<Feedback> feedbacks;
    private final static String NO_SUCH_STATUS = "No such status";


    public FilterFeedbacksByStatus(Repository repository){
        feedbacks = repository.getFeedbacks();
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        FeedbackStatus filterStatus = ParsingHelpers.tryParseEnum(parameters.get(STATUS_INDEX),
                FeedbackStatus.class, NO_SUCH_STATUS);

        feedbacks.stream()
                .filter(feedback -> feedback.getFeedbackStatus().equals(filterStatus))
                .sorted(Comparator.comparing(feedback -> feedback.getTitle().toLowerCase()))
                .forEach(feedback -> {
                    System.out.printf("Feedback: %s%n", feedback.getTitle());
                    System.out.printf("   Status: %s%n", feedback.getFeedbackStatus());
                    System.out.printf("   Description: %s%n", feedback.getDescription());
                    System.out.printf("   Comments: %s%n", feedback.getComments());
                });
        return "----- END -----";
    }
}
