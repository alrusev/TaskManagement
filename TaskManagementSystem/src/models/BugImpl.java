package models;

import models.contracts.*;
import models.enums.BugStatus;
import models.enums.Priority;
import models.enums.Severity;
import java.util.ArrayList;
import java.util.List;

public class BugImpl extends TaskImpl implements Bug {
    private List<String> stepsToReproduce;
    private Severity severity;
    private Person assignee;
    private Priority priority;
    private BugStatus bugStatus;


    public BugImpl(String title, String description, Priority priority, Severity severity, List<String> stepsToReproduce, int id) {
        super(id, title, description);
        setPriority(priority);
        setSeverity(severity);
        setStepsToReproduce(stepsToReproduce);
        bugStatus = BugStatus.ACTIVE;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public void updatePriority(Priority newPriority) {
        if (newPriority != null && !newPriority.equals(this.priority)) {
            addToHistory(new HistoryEntryImpl(String.format("Bug priority updated from %s to %s.", this.priority, newPriority)));
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

    void setBugStatus(BugStatus bugStatus){
        this.bugStatus = bugStatus;
    }
    public BugStatus getBugStatus(){
        return bugStatus;
    }
    @Override
    public String getTaskType(){
        return "Bug";
    }

    public void updateSeverity(Severity newSeverity) {
        if (newSeverity != null && !newSeverity.equals(this.severity)) {
            addToHistory(new HistoryEntryImpl(String.format("Bug severity updated from %s to %s", this.severity, newSeverity)));
            setSeverity(newSeverity);
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
            person.addToActivityHistory(String.format("Bug with ID %d assigned to %s", getId(), person.getName()));
            addToHistory(new HistoryEntryImpl("Bug assigned to " + person.getName()));
        }
    }

    public void unAssign() {
        addToHistory(new HistoryEntryImpl("Bug unassigned from " + (getAssignee() != null ? getAssignee().getName() : "None")));
        setAssignee(null);
    }

    @Override
    public void changeBugStatus() {
        if (bugStatus == BugStatus.DONE) {
            setBugStatus(BugStatus.ACTIVE);
            addToHistory(new HistoryEntryImpl("Bug reopened and marked as active."));
        }
        else {
            setBugStatus(BugStatus.DONE);
            addToHistory(new HistoryEntryImpl("Bug marked as done."));
        }
    }

    @Override
    public void assignTask(Person person) {
        assignee = person;
    }

    @Override
    public void unAssignTask() {
        assignee = null;
    }
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        result.append(String.format("   Assignee: %s%n",getAssignee().getName()));
        result.append(String.format("   Status: %s%n", getBugStatus()));
        result.append(String.format("   Severity: %s%n", getSeverity()));
        result.append(String.format("   Priority: %s%n", getPriority()));
        result.append(String.format("   Steps to reproduce: %s%n", getStepsToReproduce()));
        getComments().forEach(comment ->  result.append(String.format("   Comments: %s%n",comment.toString())));
        return result.toString();
    }

}
