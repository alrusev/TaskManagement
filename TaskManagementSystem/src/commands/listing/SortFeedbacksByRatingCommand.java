package commands.listing;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Feedback;

import java.util.Comparator;
import java.util.List;

public class SortFeedbacksByRatingCommand implements Command {
    private final List<Feedback> feedbacks;
    private int nextID = 1;

    public SortFeedbacksByRatingCommand(Repository repository){
        feedbacks = repository.getFeedbacks();
    }
    @Override
    public String execute(List<String> parameters) {
        List<Feedback> sortFeedbacks = feedbacks.stream()
                .sorted(Comparator.comparing(Feedback::getRating))
                .toList();

        sortFeedbacks.forEach(feedback -> {
            System.out.printf("%d. Feedback: %s%n", nextID++, feedback.getTitle());
            System.out.printf("   Rating: %d%n", feedback.getRating());
            System.out.printf("   Description: %s%n", feedback.getDescription());
            System.out.printf("   Comments: %s%n", feedback.getComments());
        });
        return "----- END -----";
    }

}
