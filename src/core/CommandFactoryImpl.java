package core;

import commands.*;
import commands.AssignBugCommand;
import commands.contracts.Command;
import commands.enums.CommandType;
import core.contracts.CommandFactory;
import core.contracts.Repository;
import utils.ParsingHelpers;

public class CommandFactoryImpl implements CommandFactory {
    private static final String INVALID_COMMAND = "Invalid command name: %s!";

    @Override
    public Command createCommandFromCommandName(String commandName, Repository repository) {
        CommandType commandType = ParsingHelpers.tryParseEnum(commandName, CommandType.class, String.format(INVALID_COMMAND, commandName));

        return switch (commandType) {
            case ADDCOMMENTCOMMAND -> new AddCommentCommand(repository);
            case ADDPERSONTOTEAMCOMMAND -> new AddPersonToTeamCommand(repository);
            case ASSIGNBUGCOMMAND -> new AssignBugCommand(repository);
            case ASSIGNSTORYCOMMAND -> new AssignStoryCommand(repository);
            case CHANGEBUGPRIORITYCOMMAND -> new ChangeBugPriorityCommand(repository);
            case CHANGEBUGSEVERITYCOMMAND -> new ChangeBugSeverityCommand(repository);
            case CHANGEBUGSTATUSCOMMAND -> new ChangeBugStatusCommand(repository);
            case CHANGEFEEDBACKRATINGCOMMAND -> new ChangeFeedbackRatingCommand(repository);
            case CHANGEFEEDBACKSTATUSCOMMAND -> new ChangeFeedbackStatusCommand(repository);
            case CHANGESTORYPRIORITYCOMMAND -> new ChangeStoryPriorityCommand(repository);
            case CHANGESTORYSIZECOMMAND -> new ChangeStorySizeCommand(repository);
            case CHANGESTORYSTATUSCOMMAND -> new ChangeStoryStatusCommand(repository);
            case CREATEBOARDCOMMAND -> new CreateBoardCommand(repository);
            case CREATEBUGINBOARDCOMMAND -> new CreateBugInBoardCommand(repository);
            case CREATEFEEDBACKINBOARDCOMMAND -> new CreateFeedbackInBoardCommand(repository);
            case CREATESTORYINBOARDCOMMAND -> new CreateStoryInBoardCommand(repository);
            case CREATEPERSONCOMMAND -> new CreatePersonCommand(repository);
            case CREATETEAMCOMMAND -> new CreateTeamCommand(repository);
            case SHOWALLPEOPLECOMMAND -> new ShowAllPeopleCommand(repository);
            case SHOWALLTEAMBOARDSCOMMAND -> new ShowAllTeamBoardsCommand();
            case SHOWALLTEAMMEMBERSCOMMAND -> new ShowAllTeamMembersCommand();
            case SHOWALLTEAMSCOMMAND -> new ShowAllTeamsCommand(repository);
            case SHOWBOARDACTIVITYCOMMAND -> new ShowBoardActivityCommand();
            case SHOWPERSONACTIVITYCOMMAND -> new ShowPersonActivityCommand();
            case SHOWTEAMACTIVITYCOMMAND -> new ShowTeamActivityCommand();
            case UNASSIGNBUGCOMMAND -> new UnassignBugCommand(repository);
            case UNASSIGNSTORYCOMMAND -> new UnassignStoryCommand(repository);
            default -> throw new IllegalArgumentException(String.format(INVALID_COMMAND, commandName));
        };
    }
}
