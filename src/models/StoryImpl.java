package models;

import models.contracts.Person;
import models.contracts.Story;
import models.enums.Priority;
import models.enums.Size;
import models.enums.TaskStatus;

public class StoryImpl extends TaskImpl implements Story {
    private Size size;
    private Person assignee;
    Priority priority;


    public StoryImpl(int id, String title, String description, Priority priority, Size size, TaskStatus status, Person assignee) {
        super(id, title, description, status);
        setPriority(priority);
        setSize(size);
        setAssignee(assignee);
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public void updatePriority(Priority newPriority) {
        if (newPriority != null && !newPriority.equals(this.priority)) {
            addToHistory(new HistoryEntryImpl(String.format("Priority updated from %s to %s.", this.priority, newPriority)));
            setPriority(newPriority);
        }
    }

    @Override
    public Size getSize() {
        return size;
    }

    private void setSize(Size size) {
        this.size = size;
    }

    @Override
    public void updateSize(Size newSize) {
        if (newSize != null && !newSize.equals(this.size)) {
            addToHistory(new HistoryEntryImpl("Size changed to " + size));
            setSize(newSize);
        }
    }


    public Person getAssignee() {
        return assignee;
    }

    private void setAssignee(Person person) {
        this.assignee = person;
    }

    @Override
    public void assignTo(Person person) {
        if (person != null) {
            setAssignee(person);
            addToHistory(new HistoryEntryImpl("Story assigned to " + person.getName()));
        }
    }

    @Override
    public void unassign() {
        addToHistory(new HistoryEntryImpl("Story unassigned from " + (getAssignee() != null ? getAssignee().getName() : "None")));
        setAssignee(null);
    }

//    @Override
//    public void updateStatus(TaskStatus newStatus) {
//        if (newStatus != null && !newStatus.equals(this.getStatus())){
//            addToHistory(new HistoryEntryImpl("Story status changed to " + getStatus()));
//            changeStatus(getStatus());
//        }
//    }
}
