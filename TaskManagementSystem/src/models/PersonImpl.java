package models;

import exceptions.NoSuchElementFoundException;
import models.contracts.Person;
import models.contracts.Task;
import utils.ValidationHelpers;
import java.util.ArrayList;
import java.util.List;

public class PersonImpl implements Person {
    private static final String NAME_ERROR_MESSAGE = "Name must be between 5 and 15 characters long.";
    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 15;
    public static final String NO_SUCH_TASK = "No such task found";
    private String name;
    private final List<Task> tasks;
    private final List<String> activityHistory;

    public PersonImpl(String name) {
        setName(name);
        this.tasks = new ArrayList<>();
        this.activityHistory = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    private void setName(String name) {
        ValidationHelpers.validateStringLength(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH, NAME_ERROR_MESSAGE);
        this.name = name;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void addTask(Task task) {
            tasks.add(task);
            addToActivityHistory("Added task: " + task.getId());
    }
    @Override
    public void removeTask(Task task) {
        if(getTasks().contains(task)) {
            tasks.remove(task);
            addToActivityHistory("Removed task: " + task.getId());
        }
        else {
            throw new NoSuchElementFoundException(NO_SUCH_TASK);
        }
    }

    @Override
    public List<String> getActivityHistory() {
        return new ArrayList<>(activityHistory);
    }

    @Override
    public void addToActivityHistory(String activity) {
        if(activity != null && !activity.isEmpty()) {
            activityHistory.add(activity);
        }
    }
}