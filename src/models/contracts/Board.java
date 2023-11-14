package models.contracts;

import java.util.List;

public interface Board extends Nameable{
    List<Task> getTasks();
    List<String> getActivityHistory();
    void addTaskToBoard(Task task);
    void removeTaskFromBoard(Task task);
    void addToActivityHistory(String activity);

}
