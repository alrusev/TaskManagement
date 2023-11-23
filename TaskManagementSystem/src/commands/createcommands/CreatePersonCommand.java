package commands.createcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import utils.ValidationHelpers;

import java.util.List;

public class CreatePersonCommand implements Command {
    private final static int EXPECTED_PARAMETERS_COUNT = 1;
    public static final String PERSON_CREATED_MESSAGE = "Person with name '%s' created successfully.";

    private final Repository repository;

    public CreatePersonCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);
        String personName = parameters.get(0);
        repository.createPerson(personName);
        return String.format(PERSON_CREATED_MESSAGE, personName);
    }
}
