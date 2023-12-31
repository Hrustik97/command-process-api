package command.process.api;

import command.process.api.executor.CommandExecutor;
import command.process.api.option.ArgumentOption;
import command.process.api.option.NonArgumentOption;
import command.process.api.exception.CommandProcessAPIException;
import command.process.api.option.argument.Argument;
import command.process.api.option.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CommandProcessAPI {

    private final ArrayList<Option> options;

    private final HashMap<String, Option> optionsMap;

    private final CommandExecutor executor;

    public CommandProcessAPI(CommandExecutor executor) {
        if (executor == null) {
            throw new CommandProcessAPIException("The CommandExecutor can't be null");
        }
        this.executor = executor;
        this.options = new ArrayList<>();
        this.optionsMap = new HashMap<>();
        this.addNonArgumentOption(new String[]{"-h", "--help"}, "help option showing the help message");
    }

    /**
     * Parses, validates and executes the terminal command according to specified options. Deactivates all activated options afterwards to process another command.
     *
     * @param command terminal command
     * @return The response of the API after the command is executed.
     * @throws CommandProcessAPIException API error occurring during the processing of the command. Its message informs the user what went wrong.
     */
    public String process(String command) {
        try {
            this.parse(command);
            if (this.options.get(0).isActive()) { // if the command contains the help option which overrides all other options
                return this.getHelpMessage();
            }
            this.checkPresenceOfMandatoryOptions();
            return this.executor.execute(this.optionsMap);
        } finally {
            this.deactivateOptions();
        }
    }

    /**
     * Parses the terminal command according to specified options.
     *
     * @param command terminal command
     * @throws CommandProcessAPIException API error occurring during the parsing of the command.
     */
    private void parse(String command) {
        if (command == null) {
            throw new CommandProcessAPIException("The processed command can't be null");
        }
        String[] commandSplits = Arrays.stream(command.split(" ")).filter(s -> !s.isEmpty()).toArray(String[]::new);
        for (int i = 0; i < commandSplits.length; i++) {
            String optionAlias = commandSplits[i];
            Option currOption = this.optionsMap.getOrDefault(optionAlias, null);
            if (currOption == null) {
                throw new CommandProcessAPIException(String.format("No option with the given alias '%s'", optionAlias));
            }
            if (currOption instanceof ArgumentOption) {
                if (i + 1 >= commandSplits.length) {
                    throw new CommandProcessAPIException(String.format("Missing argument for the option '%s'", optionAlias));
                }
                ((ArgumentOption) currOption).activate(commandSplits[++i]);
            } else {
                ((NonArgumentOption) currOption).activate();
            }
        }
    }

    public String getHelpMessage() {
        return this.executor.getHelpMessage(this.options);
    }

    /**
     * Checks whether all mandatory options are activated (after the terminal command is parsed).
     *
     * @throws CommandProcessAPIException API error occurring when some mandatory options are not activated before the execution of the terminal command.
     */
    private void checkPresenceOfMandatoryOptions() {
        for (Option option : this.options) {
            if (option.isMandatory() && !option.isActive()) {
                throw new CommandProcessAPIException(String.format("Mandatory option '%s' is not specified in the command", option.getAliases()[0]));
            }
        }
    }

    /**
     * Deactivates all previously activated options, so that another terminal command can be processed.
     */
    private void deactivateOptions() {
        for (Option option : this.options) {
            option.deactivate();
        }
    }

    private void addOptionToCollections(Option option) {
        for (String alias : option.getAliases()) {
            if (this.optionsMap.getOrDefault(alias, null) != null) {
                throw new CommandProcessAPIException(String.format("An option with the alias '%s' already exists.", alias));
            }
            this.optionsMap.put(alias, option);
        }
        this.options.add(option);
    }

    public void addOption(Option option) {
        this.addOptionToCollections(option);
    }

    public void addArgumentOption(String[] aliases, String description, Argument argument) {
        this.addOption(new ArgumentOption(aliases, description, argument));
    }

    public void addNonArgumentOption(String[] aliases, String description) {
        this.addOption(new NonArgumentOption(aliases, description));
    }

}
