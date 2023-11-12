package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.enums.Severity;

import java.util.List;

public class ChangeBugSeverityCommand implements Command {

    private final Repository repository;
    private static final int BUG_ID_INDEX = 1;
    private static final int NEW_SEVERITY_INDEX = 2;

    public ChangeBugSeverityCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        //bugId
        int bugId = Integer.parseInt(parameters.get(BUG_ID_INDEX));
        //newSeverity
        Severity newSeverity = Severity.valueOf(parameters.get(NEW_SEVERITY_INDEX));

        // Retrieve the Bug from the repository
        Bug bug = repository.getBugById(bugId);

        // Update the severity
        bug.updateSeverity(newSeverity);

        return String.format("Bug severity for bug with ID '%d' updated successfully. New severity: %s", bugId, newSeverity);
    }
}
