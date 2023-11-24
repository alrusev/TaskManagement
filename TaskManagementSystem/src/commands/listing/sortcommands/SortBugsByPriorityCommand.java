package commands.listing.sortcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;

import java.util.Comparator;
import java.util.List;

public class SortBugsByPriorityCommand implements Command {
    private final List<Bug> bugs;
    private final static String NO_TASKS_MESSAGE = "No bugs found";

    public SortBugsByPriorityCommand(Repository repository){
        bugs = repository.getBugs();
    }
    @Override
    public String execute(List<String> parameters) {
        if (bugs.isEmpty())
            return NO_TASKS_MESSAGE;
        List<Bug> sortBugs = bugs.stream()
                .sorted(Comparator.comparing(Bug::getPriority))
                .toList();

        sortBugs.forEach(System.out::println);
        return "----- END -----";
    }
}
