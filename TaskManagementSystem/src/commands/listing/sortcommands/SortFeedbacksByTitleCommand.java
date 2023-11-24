package commands.listing.sortcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Feedback;

import java.util.Comparator;
import java.util.List;

public class SortFeedbacksByTitleCommand implements Command {

    private final List<Feedback> feedbacks;
    private final static String NO_TASKS_MESSAGE = "No feedbacks found";

    public SortFeedbacksByTitleCommand(Repository repository){
        feedbacks = repository.getFeedbacks();
    }
    @Override
    public String execute(List<String> parameters) {
        if (feedbacks.isEmpty())
            return NO_TASKS_MESSAGE;
        List<Feedback> sortFeedbacks = feedbacks.stream()
                .sorted(Comparator.comparing(task -> task.getTitle().toLowerCase()))
                .toList();

        sortFeedbacks.forEach(System.out::println);
        return "----- END -----";
    }
}
