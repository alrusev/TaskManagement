package commands.listing;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.contracts.Story;
import models.enums.BugStatus;
import models.enums.StoryStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class FilterStoriesByStatusCommand implements Command {
    private static final int STATUS_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    private final List<Story> stories;
    private final static String NO_SUCH_STATUS = "No such status";


    public FilterStoriesByStatusCommand(Repository repository){
        stories = repository.getStories();
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        StoryStatus filterStatus = ParsingHelpers.tryParseEnum(parameters.get(STATUS_INDEX),
                StoryStatus.class, NO_SUCH_STATUS);

        stories.stream()
                .filter(story -> story.getStoryStatus().equals(filterStatus))
                .sorted(Comparator.comparing(story -> story.getTitle().toLowerCase()))
                .forEach(story -> {
                    System.out.printf("Story: %s%n", story.getTitle());
                    System.out.printf("   Status: %s%n", story.getStoryStatus());
                    System.out.printf("   Description: %s%n", story.getDescription());
                    System.out.printf("   Comments: %s%n", story.getComments());
                });
        return "----- END -----";
    }
}
