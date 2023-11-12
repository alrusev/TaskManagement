package models.contracts;

import models.enums.Priority;
import models.enums.Size;
import models.enums.TaskStatus;

public interface Story extends Task {
    Size getSize();
    void assignTo(Person person);
    void unassign();
    Priority getPriority();
    void updatePriority(Priority newPriority);
    void updateSize(Size newSize);
    void updateStatus (TaskStatus newStatus);
}
