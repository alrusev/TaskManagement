package models.contracts;

import java.util.List;

public interface Person extends Nameable{
    List<Task> getTasks();
    void addTask(Task task);
    void removeTask(Task task);
    List<String> getActivityHistory();
    void addToActivityHistory(String activity);
}
