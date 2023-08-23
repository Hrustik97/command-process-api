package command.process.api.option.argument;

import command.process.api.exception.CommandProcessAPIException;

public class BooleanArgument extends Argument {

    private String trueValue;

    private String falseValue;

    public BooleanArgument(String trueValue, String falseValue) {
        super();
        this.validateAndConstruct(trueValue, falseValue);
    }

    public BooleanArgument(String trueValue, String falseValue, String defaultValue) {
        super(defaultValue);
        this.validateAndConstruct(trueValue, falseValue);
        if (defaultValue != null && !defaultValue.equals(trueValue) && !defaultValue.equals(falseValue)) {
            throw new CommandProcessAPIException(String.format(
                "The defaultValue '%s' of the BooleanArgument doesn't match any of the permitted values: [%s]",
                defaultValue, this.toHelpMessageRepresentation()
            ));
        }
    }

    private void validateAndConstruct(String trueValue, String falseValue) {
        if (trueValue == null || trueValue.isEmpty() || falseValue == null || falseValue.isEmpty()) {
            throw new CommandProcessAPIException("trueValue and falseValue of the BooleanArgument must be non-empty strings");
        }
        this.trueValue = trueValue;
        this.falseValue = falseValue;
    }

    @Override
    public void assignValue(String value) {
        if (value.equals(this.trueValue) || value.equals(this.falseValue)) {
            this.value = value;
        } else {
            throw new CommandProcessAPIException(String.format(
                "The value '%s' doesn't match any of the permitted values: [%s]", value, this.toHelpMessageRepresentation()
            ));
        }
    }

    @Override
    public Object getValue() {
        if (this.value != null) {
            return this.value.equals(this.trueValue);
        }
        return null;
    }

    @Override
    public Object getDefaultValue() {
        if (this.defaultValue != null) {
            return this.defaultValue.equals(this.trueValue);
        }
        return null;
    }

    @Override
    public String toHelpMessageRepresentation() {
        return String.format("%s | %s", this.trueValue, this.falseValue);
    }

}
