package models;

import models.contracts.Feedback;
import models.enums.TaskStatus;

public class FeedbackImpl extends TaskImpl implements Feedback {
    private int rating;

    public FeedbackImpl(int id, String title, String description, TaskStatus status) {
        super(id, title, description, status);
        setRating(rating);
    }

    @Override
    public int getRating() {
        return rating;
    }

    private void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public void updateRating(int newRating) {
        if (this.rating != newRating) {
            //addToHistory(new HistoryEntry());
            this.rating = newRating;
        }
    }
}
