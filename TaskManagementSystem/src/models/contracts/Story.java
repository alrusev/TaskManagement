package models.contracts;

import models.enums.Priority;
import models.enums.Size;
import models.enums.StoryStatus;

public interface Story extends Task {
    Size getSize();
    void assignTask(Person person);
    void unAssignTask();
    Priority getPriority();
    void setStoryStatus(StoryStatus storyStatus);

    StoryStatus getStoryStatus();
    void updatePriority(Priority newPriority);
    void updateSize(Size newSize);
    Person getAssignee();
    String toString();

}
