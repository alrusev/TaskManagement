package commands.listing.filtercommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.contracts.Person;
import models.enums.BugStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FilterBugsByStatusAndAssigneeCommand implements Command {
    private static final int STATUS_INDEX = 0;
    private static final int ASSIGNEE_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;
    private final static String NO_SUCH_STATUS = "No such status";
    public static final String NO_RESULTS_MESSAGE = "No task with matching status and assignee!";
    private final List<Bug> bugs;




    public FilterBugsByStatusAndAssigneeCommand(Repository repository){
        bugs = repository.getBugs();
    }

    private boolean isBugMatchingAssignee(Bug bug, String assigneeName) {
        Person assignee = bug.getAssignee();
        return assignee != null && assignee.getName().equalsIgnoreCase(assigneeName);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        BugStatus filterStatus = ParsingHelpers.tryParseEnum(parameters.get(STATUS_INDEX),
                BugStatus.class, NO_SUCH_STATUS);

        String assigneeName = parameters.get(ASSIGNEE_INDEX);

        Stream<Bug> streamBug = bugs.stream()
                .filter(bug -> bug.getBugStatus().toString().equalsIgnoreCase(filterStatus.toString()))
                .filter(bug -> isBugMatchingAssignee(bug,assigneeName));

        if (streamBug.findAny().isEmpty())
            return NO_RESULTS_MESSAGE;
        bugs.stream()
                .filter(bug -> bug.getBugStatus().equals(filterStatus))
                .filter(bug -> isBugMatchingAssignee(bug, assigneeName))
                .sorted(Comparator.comparing(bug -> bug.getTitle().toLowerCase()))
                .forEach(System.out::println);
        return "----- END -----";
    }
}
