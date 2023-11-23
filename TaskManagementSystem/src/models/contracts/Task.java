package models.contracts;

import models.enums.StoryStatus;

import java.util.List;

public interface Task {
    int getId();

    String getTitle();

    String getDescription();
    List<Comment> getComments();
    void addComment(Comment comment);

    List<HistoryEntry> getHistory();
    void addToHistory(HistoryEntry historyEntry);


}
