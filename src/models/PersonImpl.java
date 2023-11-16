package models;

import models.contracts.Person;
import models.contracts.Task;
import utils.ValidationHelpers;
import java.util.ArrayList;
import java.util.List;

public class PersonImpl implements Person {
    private static final String NAME_ERROR_MESSAGE = "Name must be between 5 and 15 characters long.";
    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 15;
    private String name;
    private List<Task> tasks;
    private List<String> activityHistory;

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
        if(task != null) {
            tasks.add(task);
            addToActivityHistory("Added task: " + task.getTitle());
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