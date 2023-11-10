package models.contracts;

import models.CommentImpl;
import models.HistoryEntryImpl;
import models.PersonImpl;
import models.enums.Priority;
import models.enums.TaskStatus;

import java.util.List;

public interface Task {
    int getId();

    String getTitle();

    String getDescription();

    TaskStatus getStatus();

    List<Comment> getComments();
    void addComment(Comment comment);

    List<HistoryEntry> getHistory();
    void addToHistory(HistoryEntry historyEntry);
}
