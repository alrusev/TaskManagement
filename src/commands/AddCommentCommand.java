package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Comment;
import models.contracts.Task;
import utils.ValidationHelpers;
import utils.ParsingHelpers;
import exceptions.InvalidUserInputException;

import java.util.List;

public class AddCommentCommand implements Command {
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

        List<Task> allTasks = repository.getTasks();
        Task task = repository.findTaskById(allTasks, taskId);
        if (task == null) {
            throw new InvalidUserInputException(String.format("No task with ID '%d' found.", taskId));
        }

        Comment comment = repository.createComment(author, content);
        task.addComment(comment);

        return String.format(COMMENT_ADDED_MESSAGE, taskId);
    }

}
