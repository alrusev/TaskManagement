package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.*;
import utils.ValidationHelpers;
import utils.ParsingHelpers;

import java.util.List;

public class AssignTaskCommand implements Command {
    public static final String NOT_PART_OF_TEAM = "%s is not a part of the team";
    public static final String FEEDBACK_ASSIGNEE_ERROR = "Feedbacks cannot have assignees";
    private final Repository repository;
    private static final int BUG_ID_INDEX = 0;
    private static final int PERSON_NAME_INDEX = 1;
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    public static final String TASK_ASSIGNED_MESSAGE = "Item with ID '%d' assigned to '%s'.";

    public AssignTaskCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int id = ParsingHelpers.tryParseInteger(parameters.get(BUG_ID_INDEX), "Bug ID");
        String personName = parameters.get(PERSON_NAME_INDEX);
        Task task = repository.findTaskById(id, repository.getTasks());
        if (isFeedback(task))
            throw new IllegalArgumentException(FEEDBACK_ASSIGNEE_ERROR);

        Person person = repository.findElementByName(personName, repository.getPeople(), "Person");
        Team team = getTeamOfTask(task);
        if (!isPartOfMembers(person, team)) {
            throw new IllegalArgumentException(String.format(NOT_PART_OF_TEAM, person.getName()));
        }
        person.addTask(task);

        if (isBug(task)) {
            Bug bug = (Bug) task;
            bug.assignTask(person);
        }
        if (isStory(task)){
            Story story = (Story) task;
            story.assignTask(person);
        }
        return String.format(TASK_ASSIGNED_MESSAGE, id, personName);
    }

    private boolean isFeedback(Task task) {
        for (Feedback feedback : repository.getFeedbacks()) {
            if (feedback.getId() == task.getId())
                return true;
        }
        return false;
    }
    private boolean isBug(Task task) {
        for (Bug bug : repository.getBugs()) {
            if (bug.getId() == task.getId())
                return true;
        }
        return false;
    }
    private boolean isStory(Task task) {
        for (Story story : repository.getStories()) {
            if (story.getId() == task.getId())
                return true;
        }
        return false;
    }

    private boolean isPartOfMembers(Person person, Team team) {
        return (team.getMembers().contains(person));
    }

    private Team getTeamOfTask(Task task) {
        for (Board board : repository.getBoards()) {
            if (board.getTasks().contains(task)) {
                for (Team team : repository.getTeams()) {
                    if (team.getBoards().contains(board))
                        return team;
                }
            }
        }
        throw new IllegalArgumentException("No Tasks in the board?");
    }
}



