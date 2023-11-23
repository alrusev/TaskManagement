package commands.showcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import utils.ListingHelpers;
import java.util.List;

public class ShowAllPeopleCommand implements Command {

    private final Repository repository;

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
