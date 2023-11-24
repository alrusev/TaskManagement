package commands.listing.filtercommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Person;
import models.contracts.Story;
import utils.ValidationHelpers;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FilterStoriesByAssigneeCommand implements Command {
    private static final int ASSIGNEE_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    public static final String NO_RESULTS_MESSAGE = "No task with matching assignee!";

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
        Stream<Story> streamStory = stories.stream()
                .filter(story -> story.getStoryStatus().toString().toLowerCase().contains(assigneeName))
                .filter(story -> isStoryMatchingAssignee(story,assigneeName));

        if (streamStory.findAny().isEmpty())
            return NO_RESULTS_MESSAGE;

        stories.stream()
                .filter(story -> isStoryMatchingAssignee(story, assigneeName))
                .sorted(Comparator.comparing(story -> story.getTitle().toLowerCase()))
                .forEach(System.out::print);
        return "----- END -----";
    }
}
