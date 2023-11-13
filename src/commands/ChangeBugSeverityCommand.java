package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.contracts.Task;
import models.enums.Severity;
import utils.ParsingHelpers;
import utils.ValidationHelpers;

import java.util.List;

public class ChangeBugSeverityCommand implements Command {

    private final Repository repository;
    private static final int BUG_ID_INDEX = 0;
    private static final int NEW_SEVERITY_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS = 2;


    public ChangeBugSeverityCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        //bugId
        int bugId = ParsingHelpers.tryParseInteger(parameters.get(BUG_ID_INDEX), "Bug ID");
        //newSeverity
        Severity newSeverity = Severity.valueOf(parameters.get(NEW_SEVERITY_INDEX));

        // Retrieve the Bug from the repository
        Task task = repository.findTaskById(repository.getTasks(), bugId);
        Bug bug = (Bug) task;

        // Update the severity
        bug.updateSeverity(newSeverity);

        return String.format("Bug severity for bug with ID '%d' updated successfully. New severity: %s", bugId, newSeverity);
    }
}
