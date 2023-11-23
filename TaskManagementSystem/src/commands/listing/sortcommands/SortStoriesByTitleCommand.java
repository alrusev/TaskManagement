package commands.listing.sortcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Story;
import java.util.Comparator;
import java.util.List;

public class SortStoriesByTitleCommand implements Command {
    private final List<Story> stories;
    private int nextID = 1;

    public SortStoriesByTitleCommand(Repository repository){
        stories = repository.getStories();
    }
    @Override
    public String execute(List<String> parameters) {
        List<Story> sortStories = stories.stream()
                .sorted(Comparator.comparing(task -> task.getTitle().toLowerCase()))
                .toList();

        sortStories.forEach(story -> {
            System.out.printf("%d. Story: %s%n", nextID++, story.getTitle());
            System.out.printf("   Description: %s%n", story.getDescription());
            System.out.printf("   Comments: %s%n", story.getComments());
        });
        return "----- END -----";
    }
}
