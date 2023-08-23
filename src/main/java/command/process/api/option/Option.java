package command.process.api.option;

import command.process.api.exception.CommandProcessAPIException;

import java.util.Arrays;

public abstract class Option {

    private static final String aliasRegex = "^(-[a-z]|--[a-z]+)$";

    protected final String[] aliases;

    protected final String description;

    public Option(String[] aliases, String description) {
        if (aliases == null || aliases.length == 0) {
            throw new CommandProcessAPIException("Each option must have at least one valid alias.");
        }
        if (description == null || description.isEmpty()) {
            throw new CommandProcessAPIException("Each option must have some non-empty description.");
        }
        aliases = this.removeAliasDuplicates(aliases);
        this.validateAliases(aliases);
        this.aliases = aliases;
        this.description = description;
    }

    private String[] removeAliasDuplicates(String[] aliases) {
        return Arrays.stream(aliases).distinct().toArray(String[]::new);
    }

    private void validateAliases(String[] aliases) {
        for (String alias : aliases) {
            if (!alias.matches(Option.aliasRegex)) {
                throw new CommandProcessAPIException(String.format(
                    "Invalid alias form: '%s'. The alias must consist of either a single hyphen followed by a single " +
                    "lowercase letter, or two hyphens followed by one or more lowercase letters (e.g., -h or --help).",
                    alias
                ));
            }
        }
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public String getDescription() {
        return this.description;
    }

    public abstract void deactivate();

    /**
     * @return The boolean indicator whether the option was specified in the terminal command and activated.
     */
    public abstract Boolean isActive();

    /**
     * @return The boolean indicator whether the option is mandatory or not. NonArgumentOption always returns false.
     * ArgumentOption returns true only if the default value of its argument is not set.
     */
    public abstract boolean isMandatory();

    /**
     * @return The value of the option. NonArgumentOption returns the same Boolean value as the method 'isActive'.
     * ArgumentOption returns the value of its argument (of the arbitrary object type).
     */
    public abstract Object getValue();

    public abstract String toHelpMessageRepresentation();

}
