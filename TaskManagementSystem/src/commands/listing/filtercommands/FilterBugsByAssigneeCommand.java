package commands.listing.filtercommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.contracts.Person;
import utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FilterBugsByAssigneeCommand implements Command {
    private static final int ASSIGNEE_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    public static final String NO_RESULTS_MESSAGE = "No task with matching assignee!";


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
        Stream<Bug> streamBug = bugs.stream()
                .filter(bug -> bug.getBugStatus().toString().toLowerCase().contains(assigneeName))
                .filter(bug -> isBugMatchingAssignee(bug,assigneeName));

        if (streamBug.findAny().isEmpty())
            return NO_RESULTS_MESSAGE;

        bugs.stream()
                .filter(bug -> isBugMatchingAssignee(bug, assigneeName))
                .sorted(Comparator.comparing(bug -> bug.getTitle().toLowerCase()))
                .forEach(System.out::println);
        return "----- END -----";
    }

}
