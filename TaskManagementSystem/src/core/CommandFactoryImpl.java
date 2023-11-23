package core;

import commands.*;
import commands.contracts.Command;
import commands.enums.CommandType;
import commands.listing.*;
import core.contracts.CommandFactory;
import core.contracts.Repository;
import utils.ParsingHelpers;

public class CommandFactoryImpl implements CommandFactory {
    private static final String INVALID_COMMAND = "Invalid command name: %s!";

    @Override
    public Command createCommandFromCommandName(String commandName, Repository repository) {
        CommandType commandType = ParsingHelpers.tryParseEnum(commandName, CommandType.class, String.format(INVALID_COMMAND, commandName));

        return switch (commandType) {
            case ADDCOMMENT -> new AddCommentCommand(repository);
            case ADDPERSONTOTEAM -> new AddPersonToTeamCommand(repository);
            case ASSIGNTASK -> new AssignTaskCommand(repository);
            case CHANGEBUGPRIORITY -> new ChangeBugPriorityCommand(repository);
            case CHANGEBUGSEVERITY -> new ChangeBugSeverityCommand(repository);
            case CHANGEBUGSTATUS -> new ChangeBugStatusCommand(repository);
            case CHANGEFEEDBACKRATING -> new ChangeFeedbackRatingCommand(repository);
            case CHANGEFEEDBACKSTATUS -> new ChangeFeedbackStatusCommand(repository);
            case CHANGESTORYPRIORITY -> new ChangeStoryPriorityCommand(repository);
            case CHANGESTORYSIZE -> new ChangeStorySizeCommand(repository);
            case CHANGESTORYSTATUS -> new ChangeStoryStatusCommand(repository);
            case CREATEBOARD -> new CreateBoardCommand(repository);
            case CREATEBUGINBOARD -> new CreateBugInBoardCommand(repository);
            case CREATEFEEDBACKINBOARD -> new CreateFeedbackInBoardCommand(repository);
            case CREATESTORYINBOARD -> new CreateStoryInBoardCommand(repository);
            case CREATEPERSON -> new CreatePersonCommand(repository);
            case CREATETEAM -> new CreateTeamCommand(repository);
            case FILTERBUGSBYASSIGNEE -> new FilterBugsByAssigneeCommand(repository);
            case FILTERBUGSBYSTATUSANDASSIGNEE -> new FilterBugsByStatusAndAssigneeCommand(repository);
            case FILTERBUGSBYSTATUS -> new FilterBugsByStatusCommand(repository);
            case FILTERFEEDBACKSBYSTATUS -> new FilterFeedbacksByStatus(repository);
            case FILTERSTORIESBYASSIGNEE -> new FilterStoriesByAssigneeCommand(repository);
            case FILTERTASKSBYNAME -> new FilterTasksByNameCommand(repository);
            case FILTERSTORIESBYSTATUS -> new FilterStoriesByStatusCommand(repository);
            case FILTERSTORIESBYSTATUSANDASSIGNEE -> new FilterStoriesByStatusAndAssigneeCommand(repository);
            case SHOWALLPEOPLE -> new ShowAllPeopleCommand(repository);
            case SHOWALLTEAMBOARDS -> new ShowAllTeamBoardsCommand(repository);
            case SHOWALLTEAMMEMBERS -> new ShowAllTeamMembersCommand(repository);
            case SHOWALLTEAMS -> new ShowAllTeamsCommand(repository);
            case SHOWBOARDACTIVITY -> new ShowBoardActivityCommand(repository);
            case SHOWPERSONACTIVITY -> new ShowPersonActivityCommand(repository);
            case SHOWTEAMACTIVITY -> new ShowTeamActivityCommand(repository);
            case UNASSIGNTASK -> new UnassignTaskCommand(repository);
            case SORTFEEDBACKSBYTITLE -> new SortFeedbacksByTitleCommand(repository);
            case SORTTASKSBYNAME -> new SortTasksByNameCommand(repository);
            case SORTSTORIESBYTITLE -> new SortStoriesByTitleCommand(repository);
            case SORTBUGSBYTITLE -> new SortBugsByTitleCommand(repository);
            case SORTFEEDBACKSBYRATING -> new SortFeedbacksByRatingCommand(repository);
            case SORTSTORIESBYSIZE -> new SortStoriesBySizeCommand(repository);
            case SORTBUGSBYPRIORITY -> new SortBugsByPriorityCommand(repository);
            case SORTBUGSBYSEVERITY -> new SortBugsBySeverityCommand(repository);
            case SORTSTORIESBYPRIORITY -> new SortStoriesByPriorityCommand(repository);
        };
    }
}
