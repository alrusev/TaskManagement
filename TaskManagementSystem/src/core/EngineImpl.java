package core;

import commands.contracts.Command;
import core.contracts.CommandFactory;
import core.contracts.Engine;
import core.contracts.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EngineImpl implements Engine {
    private static final String TERMINATION_COMMAND = "Exit";
    private static final String EMPTY_COMMAND_ERROR = "Command cannot be empty.";
    private static final String COMMENT_OPEN_SYMBOL = "{{";
    private static final String COMMENT_CLOSE_SYMBOL = "}}";
    private static final String MAIN_SPLIT_SYMBOL = " ";
    private final CommandFactory commandFactory;
    public final Repository repository;

    public EngineImpl() {
        this.commandFactory = new CommandFactoryImpl();
        this.repository = new RepositoryImpl();
    }
    public void run(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String inputLine = scanner.nextLine();
                if (inputLine.isBlank()) {
                    System.out.println(EMPTY_COMMAND_ERROR);
                    continue;
                }
                if (inputLine.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                processCommand(inputLine);
            } catch (Exception ex) {
                if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
                    System.out.println(ex.getMessage());
                } else {
                    System.out.println(ex.toString());
                }
            }
        }
    }
    private void processCommand(String inputLine) {
        String commandName = extractCommandName(inputLine);
        Command command = commandFactory.createCommandFromCommandName(commandName, repository);
        List<String> parameters = extractCommandParameters(inputLine);
        String executionResult = command.execute(parameters);
        System.out.println(executionResult);
    }
    private String extractCommandName(String inputLine) {
        return inputLine.split(" ")[0];
    }
    private List<String> extractCommandParameters(String inputLine) {
        if (inputLine.contains(COMMENT_OPEN_SYMBOL)) {
            return extractCommentParameters(inputLine);
        }
        String[] commandParts = inputLine.split(" ");
        List<String> parameters = new ArrayList<>();
        for (int i = 1; i < commandParts.length; i++) {
            parameters.add(commandParts[i]);
        }
        return parameters;
    }

    public List<String> extractCommentParameters(String fullCommand) {
        int indexOfFirstSeparator = fullCommand.indexOf(MAIN_SPLIT_SYMBOL);
        int indexOfOpenComment = fullCommand.indexOf(COMMENT_OPEN_SYMBOL);
        int indexOfCloseComment = fullCommand.indexOf(COMMENT_CLOSE_SYMBOL);
        List<String> parameters = new ArrayList<>();
        if (indexOfOpenComment >= 0) {
            parameters.add(fullCommand.substring(indexOfOpenComment + COMMENT_OPEN_SYMBOL.length(), indexOfCloseComment));
            fullCommand = fullCommand.replaceAll("\\{\\{.+(?=}})}}", "");
        }

        List<String> result = new ArrayList<>(Arrays.asList(fullCommand.substring(indexOfFirstSeparator + 1).split(MAIN_SPLIT_SYMBOL)));
        result.removeAll(Arrays.asList(" ", "", null));
        parameters.addAll(result);
        return parameters;
    }
}
