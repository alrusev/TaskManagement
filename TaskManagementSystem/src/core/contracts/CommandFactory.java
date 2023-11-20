package core.contracts;

import commands.contracts.Command;

public interface CommandFactory {
    public Command createCommandFromCommandName(String commandName, Repository repository);
}
