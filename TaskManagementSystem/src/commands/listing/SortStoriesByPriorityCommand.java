package commands.listing;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Story;

import java.util.Comparator;
import java.util.List;

public class SortStoriesByPriorityCommand implements Command {
    private final List<Story> stories;
    private int nextID = 1;

    public SortStoriesByPriorityCommand(Repository repository){
        stories = repository.getStories();
    }
    @Override
    public String execute(List<String> parameters) {
        List<Story> sortStories = stories.stream()
                .sorted(Comparator.comparing(Story::getPriority))
                .toList();

        sortStories.forEach(story -> {
            System.out.printf("%d. Story: %s%n", nextID++, story.getTitle());
            System.out.printf("   Priority: %s%n", story.getPriority());
            System.out.printf("   Description: %s%n", story.getDescription());
            System.out.printf("   Comments: %s%n", story.getComments());
            System.out.printf("   Size: %s%n", story.getSize());
        });
        return "----- END -----";
    }
}
