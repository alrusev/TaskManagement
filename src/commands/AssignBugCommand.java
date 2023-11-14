package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Bug;
import models.contracts.Person;
import utils.ValidationHelpers;
import utils.ParsingHelpers;
import exceptions.InvalidUserInputException;

import java.util.List;

public class AssignBugCommand implements Command {
    private final Repository repository;
    private static final int BUG_ID_INDEX = 0;
    private static final int PERSON_NAME_INDEX = 1;
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    public static final String BUG_ASSIGNED_MESSAGE = "Bug with ID '%d' assigned to '%s'.";

    public AssignBugCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int bugId = ParsingHelpers.tryParseInteger(parameters.get(BUG_ID_INDEX), "Bug ID");
        String personName = parameters.get(PERSON_NAME_INDEX);

        Bug bug = (Bug) repository.findTaskById(repository.getTasks(), bugId);
        Person person = repository.findElementByName(personName, repository.getPeople(), "Person");

        bug.assignTo(person);

        return String.format(BUG_ASSIGNED_MESSAGE, bugId, personName);
    }
}
