package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import exceptions.TheNameIsNotUniqueException;
import utils.ValidationHelpers;

import java.util.List;

public class CreatePersonCommand implements Command {
    private final static int EXPECTED_PARAMETERS_COUNT = 1;
    public static final String PERSON_CREATED_MESSAGE = "Person with name '%s' created successfully.";
    public static final String NAME_OF_PERSON_NOT_UNIQUE = "The name of the person is not unique in the system";



    private final Repository repository;

    public CreatePersonCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);
        String personName = parameters.get(0);
        if (!repository.isNameUniqueInSystem(personName)) {
            throw new TheNameIsNotUniqueException(NAME_OF_PERSON_NOT_UNIQUE);
        }
        repository.createPerson(personName);

        return String.format(PERSON_CREATED_MESSAGE, personName);
    }
}
