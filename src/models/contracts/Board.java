package models.contracts;

import java.util.List;

public interface Board extends Nameable{
    List<Task> getTasks();
    List<HistoryEntry> getActivityHistory();
    void addTaskToBoard(Task task);
    void removeTaskFromBoard(Task task);
}
