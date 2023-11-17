package models;

import models.contracts.Feedback;
import models.enums.FeedbackStatus;
import models.enums.StoryStatus;

public class FeedbackImpl extends TaskImpl implements Feedback {
    private int rating;
    private FeedbackStatus feedbackStatus;

    public FeedbackImpl(int id, String title, String description) {
        super(id, title, description);
        setRating(rating);
        feedbackStatus = FeedbackStatus.NEW;
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
            addToHistory(new HistoryEntryImpl(String.format("Rating updated from %s to %s.", this.rating, newRating)));
            setRating(newRating);
        }
    }

    public void setFeedbackStatus(FeedbackStatus feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
        addToHistory(new HistoryEntryImpl("Feedback status changed to " + feedbackStatus));
    }
}
