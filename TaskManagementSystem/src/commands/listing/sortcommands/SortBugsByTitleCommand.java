package commands.listing.sortcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import java.util.Comparator;
import java.util.List;

public class SortBugsByTitleCommand implements Command {
    private final List<Bug> bugs;
    private int nextID = 1;

    public SortBugsByTitleCommand(Repository repository){
        bugs = repository.getBugs();
    }
    @Override
    public String execute(List<String> parameters) {
        List<Bug> sortBugs = bugs.stream()
                .sorted(Comparator.comparing(task -> task.getTitle().toLowerCase()))
                .toList();

        sortBugs.forEach(bug -> {
            System.out.printf("%d. Bug: %s%n", nextID++, bug.getTitle());
            System.out.printf("   Description: %s%n", bug.getDescription());
            System.out.printf("   Comments: %s%n", bug.getComments());
            System.out.printf("   Priority: %s%n", bug.getPriority());
            System.out.printf("   Severity: %s%n", bug.getSeverity());
        });
        return "----- END -----";
    }
}