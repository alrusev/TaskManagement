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

    private Repository repository;

    public ShowAllPeopleCommand(Repository repository){
        this.repository = repository;
    }
    @Override
    public String execute(List<String> parameters) {
        if (repository.getPeople().isEmpty()){
            return "There are no registered people.";
        }
        return ListingHelpers.peopleToString(repository.getPeople());
    }
}
