package models.contracts;

public interface Feedback extends Task {
    int getRating();
    void updateRating(int newRating);
}
