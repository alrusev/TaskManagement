package models;

import Utils.TestUtilities;
import models.contracts.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommentImplTests {
    public static final String VALID_CONTENT = TestUtilities.getString(6);
    public static final String INVALID_CONTENT = TestUtilities.getString(4);
    public static final String VALID_AUTHOR = TestUtilities.getString(6);
    public static final String INVALID_AUTHOR = TestUtilities.getString(4);

    @Test
    public void constructor_Should_ThrowException_When_ContentOutOfBounds(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommentImpl(VALID_AUTHOR,INVALID_CONTENT));
    }
        @Test
    public void constructor_Should_ThrowException_When_AuthorOutOfBounds(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommentImpl(INVALID_AUTHOR,VALID_CONTENT));
    }
            @Test
    public void constructor_Should_InitializeComment_When_ArgumentsAreValid(){
                Comment comment = new CommentImpl(VALID_AUTHOR,VALID_CONTENT);
                Assertions.assertAll(
                        () -> Assertions.assertEquals(VALID_AUTHOR,comment.getAuthor()),
                        () -> Assertions.assertEquals(VALID_CONTENT,comment.getContent())
                );
    }


}
