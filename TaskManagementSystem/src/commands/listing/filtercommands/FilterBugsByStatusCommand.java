package commands.listing.filtercommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.enums.BugStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FilterBugsByStatusCommand implements Command {
    private static final int STATUS_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    private final List<Bug> bugs;
    private final static String NO_SUCH_STATUS = "No such status";
    public static final String NO_RESULTS_MESSAGE = "No task with matching status!";



    public FilterBugsByStatusCommand(Repository repository){
        bugs = repository.getBugs();
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        BugStatus filterStatus = ParsingHelpers.tryParseEnum(parameters.get(STATUS_INDEX),
                BugStatus.class, NO_SUCH_STATUS);
        Stream<Bug> streamBug = bugs.stream()
                .filter(bug -> bug.getBugStatus().toString().equalsIgnoreCase(filterStatus.toString()));
        if (streamBug.findAny().isEmpty())
            return NO_RESULTS_MESSAGE;
        bugs.stream()
                .filter(bug -> bug.getBugStatus().equals(filterStatus))
                .sorted(Comparator.comparing(bug -> bug.getTitle().toLowerCase()))
                .forEach(System.out::println);
        return "----- END -----";
    }
}
