package commands.listing.filtercommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Story;
import models.enums.StoryStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FilterStoriesByStatusCommand implements Command {
    private static final int STATUS_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    private final List<Story> stories;
    private final static String NO_SUCH_STATUS = "No such status";
    public static final String NO_RESULTS_MESSAGE = "No task with matching status!";


    public FilterStoriesByStatusCommand(Repository repository) {
        stories = repository.getStories();
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        StoryStatus filterStatus = ParsingHelpers.tryParseEnum(parameters.get(STATUS_INDEX),
                StoryStatus.class, NO_SUCH_STATUS);

        Stream<Story> streamStory = stories.stream()
                .filter(story -> story.getStoryStatus().toString().toUpperCase().equals(filterStatus.toString()));
        if (streamStory.findAny().isEmpty())
            return NO_RESULTS_MESSAGE;
        stories.stream()
                    .filter(story -> story.getStoryStatus().equals(filterStatus))
                    .sorted(Comparator.comparing(story -> story.getTitle().toLowerCase()))
                    .forEach(System.out::println);
            return "----- END -----";

    }
}
