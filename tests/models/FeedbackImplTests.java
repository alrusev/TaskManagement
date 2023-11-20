package models;

import Utils.TestUtilities;
import models.contracts.Comment;
import models.contracts.Feedback;
import models.contracts.HistoryEntry;
import models.enums.FeedbackStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FeedbackImplTests {
    public static final String VALID_TITLE = TestUtilities.getString(11);
    public static final String INVALID_TITLE = TestUtilities.getString(4);
    public static final String VALID_DESCRIPTION = TestUtilities.getString(11);
    public static final String INVALID_DESCRIPTION = TestUtilities.getString(4);
    public static final String VALID_CONTENT = TestUtilities.getString(11);
    public static final String VALID_AUTHOR = TestUtilities.getString(11);
    public static final FeedbackStatus INITIAL_STATUS = FeedbackStatus.NEW;
    public static final int VALID_RATING = 2;

    Feedback feedback;

    @BeforeEach
    public void before() {
        feedback = new FeedbackImpl(1, VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);
    }

    @Test
    public void constructor_Should_CreateCollections() {
        Assertions.assertAll(
                () -> Assertions.assertNotNull(feedback.getComments()),
                () -> Assertions.assertNotNull(feedback.getHistory())
        );
    }

    @Test
    public void getComments_Should_ReturnCopy() {
        List<Comment> list = feedback.getComments();
        Assertions.assertNotSame(list, feedback.getComments());
    }

    @Test
    public void getHistory_Should_ReturnCopy() {
        List<HistoryEntry> list = feedback.getHistory();
        Assertions.assertNotSame(list, feedback.getHistory());
    }

    @Test
    public void constructor_Should_ThrowException_When_RatingOutOfBounds() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FeedbackImpl(1, VALID_TITLE, VALID_DESCRIPTION, 0));
    }
    @Test
    public void constructor_Should_ThrowException_When_DescriptionOutOfBounds() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FeedbackImpl(1, VALID_TITLE, INVALID_DESCRIPTION, 2));
    }
    @Test
    public void constructor_Should_ThrowException_When_TitleOutOfBounds() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FeedbackImpl(1, INVALID_TITLE, VALID_DESCRIPTION, 2));
    }

    @Test
    public void constructor_Should_InitialiseFeedbackWhenArgumentsValid() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_TITLE, feedback.getTitle()),
                () -> Assertions.assertEquals(VALID_DESCRIPTION, feedback.getDescription()),
                () -> Assertions.assertEquals(INITIAL_STATUS, feedback.getFeedbackStatus()),
                () -> Assertions.assertEquals(VALID_RATING, feedback.getRating())
        );
    }

    @Test
    public void updateRating_Should_Update_When_DifferentThanOriginal() {
        feedback.updateRating(5);
        Assertions.assertEquals(5, feedback.getRating());
    }

    @Test
    public void updateRating_Should_Throw_When_SameAsOriginal() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> feedback.updateRating(2));
    }
    @Test
    public void addComment_Should_AddComment(){
        Comment comment = new CommentImpl(VALID_AUTHOR,VALID_CONTENT);
        feedback.addComment(comment);
        Assertions.assertEquals(1,feedback.getComments().size());
    }
        @Test
    public void addToHistory_Should_AddToHistory(){
        HistoryEntry historyEntry = new HistoryEntryImpl(VALID_DESCRIPTION);
        feedback.addToHistory(historyEntry);
        Assertions.assertEquals(1,feedback.getHistory().size());
    }



}
