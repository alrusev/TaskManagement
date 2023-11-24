package commands.listing.sortcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Story;
import java.util.Comparator;
import java.util.List;

public class SortStoriesByTitleCommand implements Command {
    private final List<Story> stories;
    private final static String NO_TASKS_MESSAGE = "No stories found";

    public SortStoriesByTitleCommand(Repository repository){
        stories = repository.getStories();
    }
    @Override
    public String execute(List<String> parameters) {
        if (stories.isEmpty())
            return NO_TASKS_MESSAGE;
        List<Story> sortStories = stories.stream()
                .sorted(Comparator.comparing(task -> task.getTitle().toLowerCase()))
                .toList();

        sortStories.forEach(System.out::println);
        return "----- END -----";
    }
}
