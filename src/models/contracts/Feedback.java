package models.contracts;

import models.enums.TaskStatus;

public interface Feedback extends Task {
    int getRating();
    void updateRating(int newRating);
    void updateStatus (TaskStatus newStatus);

}
