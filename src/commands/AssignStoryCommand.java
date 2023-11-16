package commands;

import commands.contracts.Command;
import core.contracts.Repository;
import models.contracts.Story;
import models.contracts.Person;
import utils.ValidationHelpers;
import utils.ParsingHelpers;
import exceptions.InvalidUserInputException;

import java.util.List;

public class AssignStoryCommand implements Command {
    private final Repository repository;
    private static final int STORY_ID_INDEX = 0;
    private static final int PERSON_NAME_INDEX = 1;
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    public static final String STORY_ASSIGNED_MESSAGE = "Story with ID '%d' assigned to '%s'.";

    public AssignStoryCommand(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int storyId = ParsingHelpers.tryParseInteger(parameters.get(STORY_ID_INDEX), "Story ID");
        String personName = parameters.get(PERSON_NAME_INDEX);

        Story story = (Story) repository.findTaskById(repository.getTasks(), storyId);
        Person person = repository.findElementByName(personName, repository.getPeople(), "Person");
        person.addToActivityHistory(String.format("Story assigned to %s", personName));
        story.assignTo(person);

        return String.format(STORY_ASSIGNED_MESSAGE, storyId, personName);
    }
}
