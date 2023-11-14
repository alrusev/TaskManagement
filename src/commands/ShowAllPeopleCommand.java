package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.PersonImpl;
import models.contracts.Nameable;
import models.contracts.Person;
import utils.ListingHelpers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowAllPeopleCommand implements Command {

    private final List<Person>people;

    public ShowAllPeopleCommand(Repository repository){
        people = repository.getPeople();
    }
    @Override
    public String execute(List<String> parameters) {
        if (people.isEmpty()){
            return "There are no registered people.";
        }
        return ListingHelpers.peopleToString(people);
    }
}
