package models.contracts;

import java.util.List;

public interface Person  {
    String getName();
    List<Task> getTasks();
    void addTask(Task task);
    List<String> getActivityHistory();
    void addToActivityHistory(String activity);
}
