package command.process.api.option;

import command.process.api.exception.CommandProcessAPIException;
import command.process.api.option.argument.Argument;

public class ArgumentOption extends Option {

    private final Argument argument;

    public ArgumentOption(String[] aliases, String description, Argument argument) {
        super(aliases, description);
        if (argument == null) {
            throw new CommandProcessAPIException("Option arguments can't be null");
        }
        this.argument = argument;
    }

    public void activate(String argumentValue) {
        this.argument.assignValue(argumentValue);
    }

    @Override
    public void deactivate() {
        this.argument.clearValue();
    }

    @Override
    public Boolean isActive() {
        return this.argument.getValue() != null;
    }

    @Override
    public boolean isMandatory() {
        return this.argument.getDefaultValue() == null;
    }

    @Override
    public Object getValue() {
        if (this.argument.getValue() != null) {
            return this.argument.getValue();
        }
        return this.argument.getDefaultValue();
    }

    public String argumentHelpMessageRepresentation() {
        return this.argument.helpMessageRepresentation();
    }

    @Override
    public String helpMessageRepresentation() {
        StringBuilder sb = new StringBuilder();
        for (String alias : this.aliases) {
            sb.append(alias).append(" ");
        }
        sb.append('[').append(this.argumentHelpMessageRepresentation()).append(']');
        sb.append(" : ").append(this.description).append(" (");
        if (this.isMandatory()) {
            sb.append("Mandatory)");
        } else {
            sb.append("Optional, default value = '").append(this.argument.defaultValueAsString()).append("')");
        }
        return sb.toString();
    }

}
