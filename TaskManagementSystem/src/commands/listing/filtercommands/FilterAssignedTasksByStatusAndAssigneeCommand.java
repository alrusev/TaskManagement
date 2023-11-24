package commands.listing.filtercommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.contracts.Person;
import models.contracts.Story;
import utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FilterAssignedTasksByStatusAndAssigneeCommand implements Command {
    private static final int STATUS_INDEX = 0;
    private static final int ASSIGNEE_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;
    public static final String NO_RESULTS_MESSAGE = "No assigned task with matching status and assignee!";

    List<Bug> bugs;
    List<Story> stories;
    public FilterAssignedTasksByStatusAndAssigneeCommand(Repository repository){
        bugs = repository.getBugs();
        stories = repository.getStories();
    }
    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        String searchedStatus = parameters.get(STATUS_INDEX).toLowerCase();
        String searchedAssignee = parameters.get(ASSIGNEE_INDEX).toLowerCase();

        Stream<Bug> streamBug = bugs.stream()
                .filter(bug -> bug.getBugStatus().toString().toLowerCase().contains(searchedStatus))
                .filter(bug -> isBugMatchingAssignee(bug,searchedAssignee));

        Stream<Story> streamStory = stories.stream()
                .filter(story -> story.getStoryStatus().toString().toLowerCase().contains(searchedStatus))
                .filter(story -> isStoryMatchingAssignee(story,searchedAssignee));

        if (streamBug.findAny().isEmpty() && streamStory.findAny().isEmpty())
            return NO_RESULTS_MESSAGE;

        bugs.stream()
                .filter(bug -> bug.getBugStatus().toString().toLowerCase().contains(searchedStatus))
                .filter(bug -> bug.getAssignee().getName().toLowerCase().contains(searchedAssignee)).sorted(Comparator.comparing(Bug::getTitle))
                    .forEach(System.out::println);
        stories.stream()
                .filter(story -> story.getStoryStatus().toString().toLowerCase().contains(searchedStatus))
                .filter(story -> story.getAssignee().getName().toLowerCase().contains(searchedAssignee)).sorted(Comparator.comparing(Story::getTitle))
                    .forEach(System.out::println);
            return "----- END -----";


    }
    private boolean isBugMatchingAssignee(Bug bug, String assigneeName) {
        Person assignee = bug.getAssignee();
        return assignee != null && assignee.getName().toLowerCase().contains(assigneeName);
    }
        private boolean isStoryMatchingAssignee(Story story, String assigneeName) {
        Person assignee = story.getAssignee();
        return assignee != null && assignee.getName().toLowerCase().contains(assigneeName);
    }

}
