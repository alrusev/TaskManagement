package models;

import models.contracts.Comment;
import utils.ValidationHelpers;

public class CommentImpl implements Comment {
    private static final int AUTHOR_MIN_LENGTH = 5;
    private static final int AUTHOR_MAX_LENGTH = 15;
    private static final String AUTHOR_ERROR_MESSAGE =
            String.format("Author must be between %d and %d symbols", AUTHOR_MIN_LENGTH, AUTHOR_MAX_LENGTH);
    private static final int CONTENT_MIN_LENGTH = 2;
    private static final int CONTENT_MAX_LENGTH = 50;
    private static final String CONTENT_ERROR_MESSAGE =
            String.format("Content must be between %d and %d symbols", CONTENT_MIN_LENGTH, CONTENT_MAX_LENGTH);


    private String author;
    private String content;

    public CommentImpl(String author, String content) {
        setAuthor(author);
        setContent(content);
    }

    @Override
    public String getAuthor() {
        return author;
    }

    private void setAuthor(String author) {
        ValidationHelpers.validateIntRange(author.length(), AUTHOR_MIN_LENGTH, AUTHOR_MAX_LENGTH, AUTHOR_ERROR_MESSAGE);
        this.author = author;
    }

    @Override
    public String getContent() {
        return content;
    }

    private void setContent(String content) {
        ValidationHelpers.validateIntRange(content.length(), CONTENT_MIN_LENGTH, CONTENT_MAX_LENGTH, CONTENT_ERROR_MESSAGE);
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("[{%s} by %s]", getContent(), getAuthor());
    }
}
