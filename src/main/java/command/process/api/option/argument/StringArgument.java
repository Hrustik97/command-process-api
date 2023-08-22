package command.process.api.option.argument;

public class StringArgument extends Argument {

    public StringArgument() {
        super();
    }

    public StringArgument(String defaultValue) {
        super(defaultValue);
    }

    @Override
    public void assignValue(String value) {
        this.value = value;
    }

    @Override
    public String helpMessageRepresentation() {
        return "str";
    }

}
