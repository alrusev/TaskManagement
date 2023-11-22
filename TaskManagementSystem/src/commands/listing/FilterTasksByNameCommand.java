package commands.listing;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Task;
import utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class FilterTasksByNameCommand implements Command {

    private static final int TASK_ID_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS = 1;
    private final List<Task> tasks;

    public FilterTasksByNameCommand(Repository repository){
        tasks = repository.getTasks();
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_ARGUMENTS);

        String filterName = parameters.get(TASK_ID_INDEX).toLowerCase();

        tasks.stream()
                .filter(task -> task.getTitle().toLowerCase().contains(filterName))
                .sorted(Comparator.comparing(task -> task.getTitle().toLowerCase()))
                .forEach(task -> {
                    System.out.printf("Task: %s%n", task.getTitle());
                    System.out.printf("   Description: %s%n", task.getDescription());
                    System.out.printf("   Comments: %s%n", task.getComments());
                });
        return "----- END -----";
    }
}
