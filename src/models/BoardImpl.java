package models;

import models.contracts.Board;
import models.contracts.HistoryEntry;
import models.contracts.Task;
import utils.ValidationHelpers;

import java.util.ArrayList;
import java.util.List;

public class BoardImpl implements Board {
    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 15;
    private static final String NAME_ERROR_MESSAGE =
            String.format("Name must be between %d and %d symbols", NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    private static final String NO_SUCH_TASK = "There is no such task in this board";
    private List<Task> tasks;
    private List<HistoryEntry> activityHistory;
    private String name;

    public BoardImpl(List<Task> tasks, List<HistoryEntry> activityHistory, String name) {
        this.tasks = new ArrayList<>(tasks);
        this.activityHistory = new ArrayList<>(activityHistory);
        setName(name);
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public List<HistoryEntry> getActivityHistory() {
        return new ArrayList<>(activityHistory);
    }

    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        ValidationHelpers.validateIntRange(name.length(), NAME_MIN_LENGTH, NAME_MAX_LENGTH, NAME_ERROR_MESSAGE);
        this.name = name;
    }

    public void addTaskToBoard(Task task) {
        tasks.add(task);
    }

    public void removeTaskFromBoard(Task task) {
        if (!tasks.contains(task))
            throw new IllegalArgumentException(NO_SUCH_TASK);
        tasks.remove(task);
    }

    public void addHistoryEntryToBoard(HistoryEntry historyEntry) {
        activityHistory.add(historyEntry);
    }
}
