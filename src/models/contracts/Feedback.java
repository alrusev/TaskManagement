package models.contracts;


import models.enums.FeedbackStatus;

public interface Feedback extends Task {
    int getRating();
    void updateRating(int newRating);

}
