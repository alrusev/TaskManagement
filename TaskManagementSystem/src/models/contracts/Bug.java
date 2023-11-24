package models.contracts;

import models.enums.BugStatus;
import models.enums.Priority;
import models.enums.Severity;

import java.util.List;

public interface Bug extends Task {
    List<String> getStepsToReproduce();
    Severity getSeverity();
    void updateSeverity(Severity newSeverity);
    Priority getPriority();
    void updatePriority(Priority newPriority);
    void assignTo(Person person);
    void unAssign();
    void changeBugStatus();
    BugStatus getBugStatus();
    Person getAssignee();
    void unAssignTask();
    void assignTask(Person person);
    String toString();

}
