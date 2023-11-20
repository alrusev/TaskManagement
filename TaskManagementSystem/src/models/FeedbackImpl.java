package models;

import models.contracts.Feedback;
import models.contracts.Person;
import models.enums.FeedbackStatus;
import utils.ValidationHelpers;

public class FeedbackImpl extends TaskImpl implements Feedback {
    private static final int RATING_MIN_VALUE = 1;
    private static final int RATING_MAX_VALUE = 10;
    private static final String RATING_ERROR_MESSAGE = String.format("Rating Should be between %d and %d",RATING_MAX_VALUE,RATING_MAX_VALUE);
    public static final String RATING_UPDATE_ERROR = "Rating is already set at %s";
    private int rating;
    private FeedbackStatus feedbackStatus;

    public FeedbackImpl(int id, String title, String description , int rating) {
        super(id, title, description);
        setRating(rating);
        feedbackStatus = FeedbackStatus.NEW;
    }

    @Override
    public int getRating() {
        return rating;
    }

    private void setRating(int rating) {
        ValidationHelpers.validateIntRange(rating,RATING_MIN_VALUE,RATING_MAX_VALUE,RATING_ERROR_MESSAGE);
        this.rating = rating;
    }

    @Override
    public void updateRating(int newRating) {
        if (this.rating != newRating) {
            addToHistory(new HistoryEntryImpl(String.format("Rating updated from %s to %s.", this.rating, newRating)));
            setRating(newRating);
        }
        else{
            throw new IllegalArgumentException(String.format(RATING_UPDATE_ERROR,newRating));
        }
    }

    public void setFeedbackStatus(FeedbackStatus feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
        addToHistory(new HistoryEntryImpl("Feedback status changed to " + feedbackStatus));
    }

    public FeedbackStatus getFeedbackStatus() {
        return feedbackStatus;
    }

    @Override
    public void assignTask(Person person) {
        throw new IllegalArgumentException("Feedbacks cannot have assignees");
    }
    @Override
    public void unAssignTask() {
        throw new IllegalArgumentException("Feedbacks cannot have assignees");
    }

    @Override
    public Person getAssignee() {
        throw new IllegalArgumentException("Feedbacks do not have assigned");
    }
}
