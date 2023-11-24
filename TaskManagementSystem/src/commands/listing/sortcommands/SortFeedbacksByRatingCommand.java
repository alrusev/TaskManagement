package commands.listing.sortcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Feedback;

import java.util.Comparator;
import java.util.List;

public class SortFeedbacksByRatingCommand implements Command {
    private final List<Feedback> feedbacks;
    private final static String NO_TASKS_MESSAGE = "No feedbacks found";


    public SortFeedbacksByRatingCommand(Repository repository){
        feedbacks = repository.getFeedbacks();
    }
    @Override
    public String execute(List<String> parameters) {
        if (feedbacks.isEmpty())
            return NO_TASKS_MESSAGE;
        List<Feedback> sortFeedbacks = feedbacks.stream()
                .sorted(Comparator.comparing(Feedback::getRating))
                .toList();

        sortFeedbacks.forEach(System.out::println);
        return "----- END -----";
    }

}
