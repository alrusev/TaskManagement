package models;

import models.contracts.Comment;
import models.contracts.HistoryEntry;
import models.contracts.Person;
import models.contracts.Task;
import models.enums.Priority;
import models.enums.TaskStatus;
import utils.ValidationHelpers;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class TaskImpl implements Task {

    private static final int TITLE_MIN_LENGTH = 10;
    private static final int TITLE_MAX_LENGTH = 100;
    private static final String TITLE_ERROR_MESSAGE = "Title must be between 10 and 100 symbols long.";
    private static final int DESC_MIN_LENGTH = 10;
    private static final int DESC_MAX_LENGTH = 500;
    private static final String DESC_ERROR_MESSAGE = "Description must be between 10 and 500 symbols long.";
    private int id;
    private String title;
    private String description;
    private TaskStatus status;
    private List<Comment> comments;
    private List<HistoryEntry> historyEntries;


    public TaskImpl(int id, String title,String description, TaskStatus status) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setStatus(status);
        this.comments = new ArrayList<>();
        this.historyEntries = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        ValidationHelpers.validateStringLength(title, TITLE_MIN_LENGTH, TITLE_MAX_LENGTH, TITLE_ERROR_MESSAGE);
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        ValidationHelpers.validateStringLength(description, DESC_MIN_LENGTH, DESC_MAX_LENGTH, DESC_ERROR_MESSAGE);
        this.description = description;
    }

    @Override
    public TaskStatus getStatus() {
        return status;
    }

    private void setStatus(TaskStatus status) {
        this.status = status;
    }

    protected void changeStatus(TaskStatus newStatus) {
        if (this.status != newStatus) {
            addToHistory(new HistoryEntryImpl(String.format("Status changed from %s to %s.", this.status, newStatus)));
            setStatus(newStatus);
        }
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public void addComment(Comment comment) {
        if (comment != null) {
            comments.add(comment);
            addToHistory(new HistoryEntryImpl("Comment added: " + comment.getContent()));
        }
    }

    @Override
    public List<HistoryEntry> getHistory() {
        return new ArrayList<>(historyEntries);
    }

    @Override
    public void addToHistory(HistoryEntry historyEntry) {
        if (historyEntry != null) {
            historyEntries.add(historyEntry);
        }
    }
}
