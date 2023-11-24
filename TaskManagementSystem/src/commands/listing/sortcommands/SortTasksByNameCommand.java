package commands.listing.sortcommands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Task;
import java.util.Comparator;
import java.util.List;

public class SortTasksByNameCommand implements Command {

    private final List<Task> tasks;
    private final static String NO_TASKS_MESSAGE = "No tasks found";


    public SortTasksByNameCommand(Repository repository) {
        tasks = repository.getTasks();
    }

    @Override
    public String execute(List<String> parameters) {
        if (tasks.isEmpty())
            return NO_TASKS_MESSAGE;
        List<Task> sortedTasks = tasks.stream()
                .sorted(Comparator.comparing(task -> task.getTitle().toLowerCase()))
                .toList();

        sortedTasks.forEach(System.out::println);
        return "----- END -----";
    }
}