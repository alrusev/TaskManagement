package commands.listing;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Person;
import models.contracts.Story;
import utils.ValidationHelpers;
import java.util.Comparator;
import java.util.List;

public class FilterStoriesByAssigneeCommand implements Command {
    private static final int ASSIGNEE_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    private final List<Story> stories;

    public FilterStoriesByAssigneeCommand(Repository repository){
        stories = repository.getStories();
    }

    private boolean isStoryMatchingAssignee(Story story, String assigneeName) {
        Person assignee = story.getAssignee();
        return assignee != null && assignee.getName().equalsIgnoreCase(assigneeName);
    }
    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        String assigneeName = parameters.get(ASSIGNEE_INDEX);

        stories.stream()
                .filter(story -> isStoryMatchingAssignee(story, assigneeName))
                .sorted(Comparator.comparing(story -> story.getTitle().toLowerCase()))
                .forEach(story -> {
                    System.out.printf("Story: %s%n", story.getTitle());
                    System.out.printf("   Assignee: %s%n", story.getAssignee().getName());
                    System.out.printf("   Status: %s%n", story.getStoryStatus());
                    System.out.printf("   Description: %s%n", story.getDescription());
                    System.out.printf("   Comments: %s%n", story.getComments());
                });
        return "----- END -----";
    }
}
