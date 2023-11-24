package models;

import models.contracts.Person;
import models.contracts.Story;
import models.enums.Priority;
import models.enums.Size;
import models.enums.StoryStatus;

public class StoryImpl extends TaskImpl implements Story {
    private Size size;
    private Person assignee;
    Priority priority;
    StoryStatus storyStatus;


    public StoryImpl(int id, String title, String description, Priority priority, Size size) {
        super(id, title, description);
        setPriority(priority);
        setSize(size);
        setAssignee(assignee);
        storyStatus = StoryStatus.NOTDONE;
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
            addToHistory(new HistoryEntryImpl(String.format("Story priority updated from %s to %s.", this.priority, newPriority)));
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
            addToHistory(new HistoryEntryImpl("Story size changed to " + size));
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
    public void assignTask(Person person) {
        if (person != null) {
            setAssignee(person);
            person.addToActivityHistory(String.format("Story with ID %d assigned to %s.", getId(), person.getName()));
            addToHistory(new HistoryEntryImpl("Story assigned to " + person.getName()));
        }
    }

    @Override
    public void unAssignTask() {
        addToHistory(new HistoryEntryImpl("Story unassigned from " + (getAssignee() != null ? getAssignee().getName() : "None")));
        setAssignee(null);
    }

    @Override
    public void setStoryStatus(StoryStatus storyStatus) {
        this.storyStatus = storyStatus;
        addToHistory(new HistoryEntryImpl("Story status changed to " + storyStatus));
    }

    @Override
    public StoryStatus getStoryStatus() {
        return storyStatus;
    }

    @Override
    public String getTaskType(){
        return "Story";
    }
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        result.append(String.format("   Assignee: %s%n",getAssignee().getName()));
        result.append(String.format("   Status: %s%n", getStoryStatus()));
        result.append(String.format("   Priority: %s%n", getPriority()));
        result.append(String.format("   Size: %s%n", getSize()));
        getComments().forEach(comment ->  result.append(String.format("   Comments: %s%n",comment.toString())));
        return result.toString();
    }

}
