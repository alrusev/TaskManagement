package commands.listing.filtercommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Task;
import utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FilterTasksByNameCommand implements Command {

    private static final int TASK_ID_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    public static final String NO_RESULTS_MESSAGE = "No task with matching name!";

    private final List<Task> tasks;

    public FilterTasksByNameCommand(Repository repository){
        tasks = repository.getTasks();
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        String filterName = parameters.get(TASK_ID_INDEX).toLowerCase();
        Stream<Task> taskStream = tasks.stream()
                .filter(task -> task.getTitle().toLowerCase().contains(filterName));
        if(taskStream.findAny().isEmpty())
            return NO_RESULTS_MESSAGE;

        tasks.stream()
                .filter(task -> task.getTitle().toLowerCase().contains(filterName))
                .sorted(Comparator.comparing(task -> task.getTitle().toLowerCase()))
                .forEach(System.out::println);
        return "----- END -----";
    }
}
