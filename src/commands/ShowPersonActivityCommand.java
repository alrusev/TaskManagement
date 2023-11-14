package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Board;
import models.contracts.Person;
import utils.ValidationHelpers;

import java.util.List;

public class ShowPersonActivityCommand implements Command {

    private final Repository repository;
    private static final int NAME_INDEX = 0;
    private static final int EXPECTED_PARAMETERS_COUNT = 1;

    public ShowPersonActivityCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String personName = parameters.get(NAME_INDEX);

        Person person = repository.findElementByName(personName, repository.getPeople(), "Person");

        return String.format("Person activity for %s - %s", personName, person.getActivityHistory().toString());
    }
}
