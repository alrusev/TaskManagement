package commands.listing.filtercommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.enums.BugStatus;
import utils.ParsingHelpers;
import utils.ValidationHelpers;
import java.util.Comparator;
import java.util.List;

public class FilterBugsByStatusCommand implements Command {
    private static final int STATUS_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    private final List<Bug> bugs;
    private final static String NO_SUCH_STATUS = "No such status";


    public FilterBugsByStatusCommand(Repository repository){
        bugs = repository.getBugs();
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        BugStatus filterStatus = ParsingHelpers.tryParseEnum(parameters.get(STATUS_INDEX),
                BugStatus.class, NO_SUCH_STATUS);

        bugs.stream()
                .filter(bug -> bug.getBugStatus().equals(filterStatus))
                .sorted(Comparator.comparing(bug -> bug.getTitle().toLowerCase()))
                .forEach(bug -> {
                    System.out.printf("Bug: %s%n", bug.getTitle());
                    System.out.printf("   Status: %s%n", bug.getBugStatus());
                    System.out.printf("   Description: %s%n", bug.getDescription());
                    System.out.printf("   Comments: %s%n", bug.getComments());
                });
        return "----- END -----";
    }
}
