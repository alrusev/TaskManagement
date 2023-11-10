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
            case CHANGEBUGCOMMAND:
                return new ChangeBugCommand();
            case CHANGEFEEDBACKCOMMAND:
                return new ChangeFeedbackCommand();
            case CHANGESTORYCOMMAND:
                return new ChangeStoryCommand();
            case CREATEBOARDCOMMAND:
                return new CreateBoardCommand();
            case CREATEBUGINBOARDCOMMAND:
                return new CreateBugInBoardCommand();
            case CREATEFEEDBACKINBOARDCOMMAND:
                return new CreateFeedbackInBoardCommand();
            case CREATESTORYINBOARDCOMMAND:
                return new CreateStoryInBoardCommand();
            case CREATEPERSONCOMMAND:
                return new CreatePersonCommand();
            case CREATETEAMCOMMAND:
                return new CreateTeamCommand();
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
