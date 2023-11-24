package commands.listing.filtercommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.contracts.Story;
import utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FilterAssignedTasksByStatus implements Command {
    private static final int STATUS_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    public static final String NO_RESULTS_MESSAGE = "No assigned task with matching status!";

    List<Bug> bugs;
    List<Story> stories;
    public FilterAssignedTasksByStatus(Repository repository){
        bugs = repository.getBugs();
        stories = repository.getStories();
    }
    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        String searchedStatus = parameters.get(STATUS_INDEX).toLowerCase();

       Stream<Bug> streamBug = bugs.stream()
                .filter(bug -> bug.getBugStatus().toString().toLowerCase().contains(searchedStatus));

      Stream<Story> streamStory = stories.stream()
                .filter(story -> story.getStoryStatus().toString().toLowerCase().contains(searchedStatus));
        if (streamBug.findAny().isEmpty() && streamStory.findAny().isEmpty()) {
            return NO_RESULTS_MESSAGE;
        }
        else {
            bugs.stream()
                    .filter(bug -> bug.getBugStatus().toString().toLowerCase().contains(searchedStatus)).sorted(Comparator.comparing(Bug::getTitle))
                    .forEach(System.out::println);
            stories.stream()
                    .filter(story -> story.getStoryStatus().toString().toLowerCase().contains(searchedStatus)).sorted(Comparator.comparing(Story::getTitle))
                .forEach(System.out::println);
                    return "----- END -----";

        }
        }
    }
