package commands.listing;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Task;
import java.util.Comparator;
import java.util.List;

public class SortTasksByNameCommand implements Command {

    private final List<Task> tasks;
    private int nextID = 1;


    public SortTasksByNameCommand(Repository repository) {
        tasks = repository.getTasks();
    }

    @Override
    public String execute(List<String> parameters) {
        List<Task> sortedTasks = tasks.stream()
                .sorted(Comparator.comparing(task -> task.getTitle().toLowerCase()))
                .toList();

        sortedTasks.forEach(task -> {
            System.out.printf("%d. Task: %s%n", nextID++, task.getTitle());
            System.out.printf("   Description: %s%n", task.getDescription());
            System.out.printf("   Comments: %s%n", task.getComments());
        });
        return "----- END -----";
    }
}