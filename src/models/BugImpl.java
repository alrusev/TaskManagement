package models;

import models.contracts.*;
import models.enums.Priority;
import models.enums.Severity;
import models.enums.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public class BugImpl extends TaskImpl implements Bug {
    private List<String> stepsToReproduce;
    private Severity severity;
    private Person assignee;
    Priority priority;

    public BugImpl(int id, String title, String description, Priority priority, Severity severity, TaskStatus status, Person assignee, List<String> stepsToReproduce) {
        super(id, title, description, status);
        setPriority(priority);
        setAssignee(assignee);
        setSeverity(severity);
        setStepsToReproduce(stepsToReproduce);
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void updatePriority(Priority newPriority) {
        if (newPriority != null && !newPriority.equals(this.priority)) {
            //addToHistory(new HistoryEntry());
            this.priority = newPriority;
        }
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }

    public List<String> getStepsToReproduce() {
        return new ArrayList<>(stepsToReproduce);
    }

    private void setStepsToReproduce(List<String> stepsToReproduce) {
        if(stepsToReproduce != null) {
            this.stepsToReproduce = new ArrayList<>(stepsToReproduce);
        }
    }

    public Severity getSeverity() {
        return severity;
    }

    private void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public void updateSeverity(Severity newSeverity) {
        if (newSeverity != null && !newSeverity.equals(this.severity)) {
            //addToHistory(new HistoryEntry());
            this.severity = newSeverity;
        }
    }

    public Person getAssignee() {
        return assignee;
    }

    private void setAssignee(Person person) {
        this.assignee = person;
    }

    public void assignTo(Person person) {
        if (person != null) {
            setAssignee(person);
            //addToHistory(new HistoryEntry("Bug assigned to " + person.getName()));
        }
    }

    public void unassign() {
        //addToHistory(new HistoryEntry("Bug unassigned from " + (getAssignee() != null ? getAssignee().getName() : "None")));
        setAssignee(null);
    }

    public void markAsDone() {
        if (getStatus() != TaskStatus.DONE) {
            changeStatus(TaskStatus.DONE);
            //addToHistory(new HistoryEntry("Bug marked as done."));
        }
    }

    public void reopenBug() {
        if (getStatus() == TaskStatus.DONE) {
            changeStatus(TaskStatus.ACTIVE);
            //addToHistory(new HistoryEntry("Bug reopened and marked as active."));
        }
    }
}
