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
            case ADDCOMMENT:
                return new AddCommentCommand();
            case ADDPERSONTOTEAM:
                return new AddPersonToTeamCommand();
            case ASSIGNTASK:
                return new AssignTaskCommand();
            case CHANGEBUGPRIORITY:
                return  new ChangeBugPriorityCommand(repository);
            case CHANGEBUGSEVERITY:
                return new ChangeBugSeverityCommand(repository);
            case CHANGEBUGSTATUS:
                return new ChangeBugStatusCommand(repository);
            case CHANGEFEEDBACKRATING:
                return new ChangeFeedbackRatingCommand(repository);
            case CHANGEFEEDBACKSTATUS:
                return new ChangeFeedbackStatusCommand(repository);
            case CHANGESTORYPRIORITY:
                return new ChangeStoryPriorityCommand(repository);
            case CHANGESTORYSIZE:
                return new ChangeStorySizeCommand(repository);
            case CHANGESTORYSTATUS:
                return new ChangeStoryStatusCommand(repository);
            case CREATEBOARD:
                return new CreateBoardCommand(repository);
            case CREATEBUGINBOARD:
                return new CreateBugInBoardCommand(repository);
            case CREATEFEEDBACKINBOARD:
                return new CreateFeedbackInBoardCommand(repository);
            case CREATESTORYINBOARD:
                return new CreateStoryInBoardCommand(repository);
            case CREATEPERSON:
                return new CreatePersonCommand(repository);
            case CREATETEAM:
                return new CreateTeamCommand(repository);
            case SHOWALLPEOPLE:
                return new ShowAllPeopleCommand();
            case SHOWALLTEAMBOARDS:
                return new ShowAllTeamBoardsCommand();
            case SHOWALLTEAMMEMBERS:
                return new ShowAllTeamMembersCommand();
            case SHOWALLTEAMS:
                return new ShowAllTeamsCommand();
            case SHOWBOARDACTIVITY:
                return new ShowBoardActivityCommand();
            case SHOWPERSONACTIVITY:
                return new ShowPersonActivityCommand();
            case SHOWTEAMACTIVITY:
                return new ShowTeamActivityCommand();
            case UNASSIGNTASK:
                return new UnassignTaskCommand();

            default:
                throw new IllegalArgumentException(String.format(INVALID_COMMAND, commandName));
        }
    }
}
