package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import exceptions.NoSuchElementFoundException;
import models.contracts.Comment;
import models.contracts.Task;
import utils.ValidationHelpers;
import utils.ParsingHelpers;

import java.util.List;

public class AddCommentCommand implements Command {
    public static final String AUTHOR_NOT_FOUND = "The author name does not align with the name of an existing person";
    private final Repository repository;
    private static final int TASK_ID_INDEX = 0;
    private static final int AUTHOR_INDEX = 1;
    private static final int COMMENT_CONTENT_INDEX = 2;
    private static final int EXPECTED_PARAMETERS_COUNT = 3;
    public static final String COMMENT_ADDED_MESSAGE = "Comment added successfully to Task with ID '%d'.";

    public AddCommentCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int taskId = ParsingHelpers.tryParseInteger(parameters.get(TASK_ID_INDEX), "Task ID");
        String author = parameters.get(AUTHOR_INDEX);
        String content = parameters.get(COMMENT_CONTENT_INDEX);
        Task task = repository.findTaskById(taskId,repository.getTasks());
        if (!repository.getPeople().contains(repository.findElementByName(author,repository.getPeople(),"person")))
            throw new NoSuchElementFoundException(AUTHOR_NOT_FOUND);
        Comment comment = repository.createComment(author, content);
        task.addComment(comment);

        return String.format(COMMENT_ADDED_MESSAGE, taskId);
    }

}
