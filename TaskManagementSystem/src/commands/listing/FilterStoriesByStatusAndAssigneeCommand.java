package commands.listing;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.contracts.Person;
import models.contracts.Story;
import models.enums.BugStatus;
import models.enums.StoryStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class FilterStoriesByStatusAndAssigneeCommand implements Command {
    private static final int STATUS_INDEX = 0;
    private static final int ASSIGNEE_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;
    private final List<Story> stories;
    private final static String NO_SUCH_STATUS = "No such status";


    public FilterStoriesByStatusAndAssigneeCommand(Repository repository){
        stories = repository.getStories();
    }

    private boolean isStoryMatchingAssignee(Story story, String assigneeName) {
        Person assignee = story.getAssignee();
        return assignee != null && assignee.getName().equalsIgnoreCase(assigneeName);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        StoryStatus filterStatus = ParsingHelpers.tryParseEnum(parameters.get(STATUS_INDEX),
                StoryStatus.class, NO_SUCH_STATUS);

        String assigneeName = parameters.get(ASSIGNEE_INDEX);

        stories.stream()
                .filter(story -> story.getStoryStatus().equals(filterStatus))
                .filter(story -> isStoryMatchingAssignee(story, assigneeName))
                .sorted(Comparator.comparing(story -> story.getTitle().toLowerCase()))
                .forEach(story -> {
                    System.out.printf("Story: %s%n", story.getTitle());
                    System.out.printf("   Status: %s%n", story.getStoryStatus());
                    System.out.printf("   Assignee: %s%n", story.getAssignee());
                    System.out.printf("   Description: %s%n", story.getDescription());
                    System.out.printf("   Comments: %s%n", story.getComments());
                });
        return "----- END -----";
    }
}
