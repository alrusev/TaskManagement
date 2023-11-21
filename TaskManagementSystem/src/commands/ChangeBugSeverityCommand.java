package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.enums.Severity;
import utils.ParsingHelpers;
import utils.ValidationHelpers;
import java.util.List;

public class ChangeBugSeverityCommand implements Command {

    private static final String SEVERITY_SUCCESSFULLY_CHANGED = "Severity for bug with ID '%d' updated successfully. " +
            "New severity: %s";
    private static final String SEVERITY_ALREADY_DEFINED = "The severity of bug with ID '%s' is already set to %s!";
    private final Repository repository;
    private static final int BUG_ID_INDEX = 0;
    private static final int NEW_SEVERITY_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;
    private final static String NO_SUCH_SEVERITY = "No such severity";

    public ChangeBugSeverityCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //bugId
        int bugId = ParsingHelpers.tryParseInteger(parameters.get(BUG_ID_INDEX), "Bug ID");
        //newSeverity
        Severity severity = ParsingHelpers.tryParseEnum(parameters.get(NEW_SEVERITY_INDEX), Severity.class,
                NO_SUCH_SEVERITY);

        // Retrieve the Bug from the repository
        Bug bug = repository.findTaskById(bugId, repository.getBugs());

        if (bug.getSeverity().equals(severity)) {
            throw new IllegalArgumentException(String.format(SEVERITY_ALREADY_DEFINED, bugId, severity));
        }
        // Update the severity
        bug.updateSeverity(severity);

        return String.format(SEVERITY_SUCCESSFULLY_CHANGED, bugId, severity);
    }
}