package command.process.api.option.argument;

import command.process.api.exception.CommandProcessAPIException;

public class IntegerArgument extends Argument {

    private Integer minValue;

    private Integer maxValue;

    public IntegerArgument() {
        super();
        this.construct(null, null);
    }

    public IntegerArgument(Integer defaultValue) {
        super(defaultValue);
        this.construct(null, null);
    }

    public IntegerArgument(Integer minValue, Integer maxValue) {
        super();
        this.construct(minValue, maxValue);
    }

    public IntegerArgument(Integer minValue, Integer maxValue, Integer defaultValue) {
        super(defaultValue);
        this.construct(minValue, maxValue);
    }

    private void construct(Integer minValue, Integer maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void assignValue(String value) {
        Integer integerValue;
        try {
            integerValue = Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new CommandProcessAPIException(String.format("The value '%s' can't be converted into an integer", value));
        }
        if (this.minValue != null && integerValue < this.minValue) {
            throw new CommandProcessAPIException(String.format("The value '%d' is not greater than or equal to the minValue '%d'", integerValue, this.minValue));
        }
        if (this.maxValue != null && integerValue > this.maxValue) {
            throw new CommandProcessAPIException(String.format("The value '%d' is not less than or equal to the maxValue '%d'", integerValue, this.maxValue));
        }
        this.value = integerValue;
    }

    @Override
    public String helpMessageRepresentation() {
        return "int";
    }

}
