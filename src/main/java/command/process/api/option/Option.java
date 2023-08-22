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

    public abstract Boolean isActive();

    public abstract boolean isMandatory();

    public abstract Object getValue();

    public abstract String helpMessageRepresentation();

}
