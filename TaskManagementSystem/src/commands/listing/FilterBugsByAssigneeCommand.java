package commands.listing;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.contracts.Person;
import utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class FilterBugsByAssigneeCommand implements Command {
    private static final int ASSIGNEE_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    private final List<Bug> bugs;

    public FilterBugsByAssigneeCommand(Repository repository) {
        bugs = repository.getBugs();
    }

    private boolean isBugMatchingAssignee(Bug bug, String assigneeName) {
        Person assignee = bug.getAssignee();
        return assignee != null && assignee.getName().equalsIgnoreCase(assigneeName);
    }
    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        String assigneeName = parameters.get(ASSIGNEE_INDEX);

        bugs.stream()
                .filter(bug -> isBugMatchingAssignee(bug, assigneeName))
                .sorted(Comparator.comparing(bug -> bug.getTitle().toLowerCase()))
                .forEach(bug -> {
                    System.out.printf("Bug: %s%n", bug.getTitle());
                    System.out.printf("   Assignee: %s%n", bug.getAssignee().getName());
                    System.out.printf("   Status: %s%n", bug.getBugStatus());
                    System.out.printf("   Description: %s%n", bug.getDescription());
                    System.out.printf("   Comments: %s%n", bug.getComments());
                });
        return "----- END -----";
    }
}
