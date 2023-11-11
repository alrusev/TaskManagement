package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Person;

import java.util.List;

public class CreatePersonCommand implements Command {

    private final Repository repository;

    public CreatePersonCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() != 1) {
            return "Invalid number of parameters. Usage: CreatePersonCommand <personName>";
        }

        String personName = parameters.get(0);

        if (isNameUnique(personName)) {
            // If unique, create a new person and add it to the repository
            repository.createPerson(personName);

            return String.format("Person with name '%s' created successfully.", personName);
        } else {
            return "Person name is not unique. Please choose a different name.";
        }
    }

    private boolean isNameUnique(String name) {
        for (Person person : repository.getAllPeople()) {
            if (person.getName().equals(name)) {
                return false; // Name is not unique
            }
        }
        return true; // Name is unique
    }


}
