package core;

import commands.*;
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

        switch (commandType) {
            case ADDCOMMENTCOMMAND:
                return new AddCommentCommand();
            case ADDPERSONTOTEAMCOMMAND:
                return new AddPersonToTeamCommand();
            case ASSIGNTASKCOMMAND:
                return new AssignTaskCommand();
            case CHANGEBUGPRIORITYCOMMAND:
                return  new ChangeBugPriorityCommand(repository);
            case CHANGEBUGSEVERITYCOMMAND:
                return new ChangeBugSeverityCommand(repository);
            case CHANGEBUGSTATUSCOMMAND:
                return new ChangeBugStatusCommand(repository);
            case CHANGEFEEDBACKRATINGCOMMAND:
                return new ChangeFeedbackRatingCommand(repository);
            case CHANGEFEEDBACKSTATUSCOMMAND:
                return new ChangeFeedbackStatusCommand(repository);
            case CHANGESTORYPRIORITYCOMMAND:
                return new ChangeStoryPriorityCommand(repository);
            case CHANGESTORYSIZECOMMAND:
                return new ChangeStorySizeCommand(repository);
            case CHANGESTORYSTATUSCOMMAND:
                return new ChangeStoryStatusCommand(repository);
            case CREATEBOARDCOMMAND:
                return new CreateBoardCommand(repository);
            case CREATEBUGINBOARDCOMMAND:
                return new CreateBugInBoardCommand(repository);
            case CREATEFEEDBACKINBOARDCOMMAND:
                return new CreateFeedbackInBoardCommand(repository);
            case CREATESTORYINBOARDCOMMAND:
                return new CreateStoryInBoardCommand(repository);
            case CREATEPERSONCOMMAND:
                return new CreatePersonCommand(repository);
            case CREATETEAMCOMMAND:
                return new CreateTeamCommand(repository);
            case SHOWALLPEOPLECOMMAND:
                return new ShowAllPeopleCommand();
            case SHOWALLTEAMBOARDSCOMMAND:
                return new ShowAllTeamBoardsCommand();
            case SHOWALLTEAMMEMBERSCOMMAND:
                return new ShowAllTeamMembersCommand();
            case SHOWALLTEAMSCOMMAND:
                return new ShowAllTeamsCommand();
            case SHOWBOARDACTIVITYCOMMAND:
                return new ShowBoardActivityCommand();
            case SHOWPERSONACTIVITYCOMMAND:
                return new ShowPersonActivityCommand();
            case SHOWTEAMACTIVITYCOMMAND:
                return new ShowTeamActivityCommand();
            case UNASSIGNTASKCOMMAND:
                return new UnassignTaskCommand();

            default:
                throw new IllegalArgumentException(String.format(INVALID_COMMAND, commandName));
        }
    }
}
