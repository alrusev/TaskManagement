package models.contracts;

import java.util.List;

public interface Board {
    List<Task> getTasks();
    List<HistoryEntry> getActivityHistory();
    String getName();


}
