package models.contracts;

import models.enums.FeedbackStatus;
import models.enums.Priority;
import models.enums.Size;
import models.enums.StoryStatus;

public interface Story extends Task {
    Size getSize();
    void assignTo(Person person);
    void unassign();
    Priority getPriority();
    void setStoryStatus(StoryStatus storyStatus);

    StoryStatus getStoryStatus();
    void updatePriority(Priority newPriority);
    void updateSize(Size newSize);
    Person getAssignee();
}
