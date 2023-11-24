package commands.listing.sortcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import java.util.Comparator;
import java.util.List;

public class SortBugsByTitleCommand implements Command {
    private final List<Bug> bugs;
    private final static String NO_TASKS_MESSAGE = "No bugs found";

    public SortBugsByTitleCommand(Repository repository){
        bugs = repository.getBugs();
    }
    @Override
    public String execute(List<String> parameters) {
        if (bugs.isEmpty())
            return NO_TASKS_MESSAGE;
        List<Bug> sortBugs = bugs.stream()
                .sorted(Comparator.comparing(task -> task.getTitle().toLowerCase()))
                .toList();

        sortBugs.forEach(System.out::println);
        return "----- END -----";
    }
}
