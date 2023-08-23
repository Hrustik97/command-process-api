package command.process.api.option.argument;

public abstract class Argument {

    protected Object value;

    protected Object defaultValue;

    public Argument() {
        this.construct(null);
    }

    public Argument(Object defaultValue) {
        this.construct(defaultValue);
    }

    private void construct(Object defaultValue) {
        this.value = null;
        this.defaultValue = defaultValue;
    }


    /**
     * Parses the string representation of the argument value to its real representation of the arbitrary object type which is then assigned to the class variable 'value'.
     *
     * @param value The argument value parsed from the terminal command.
     */
    public abstract void assignValue(String value);

    public Object getValue() {
        return this.value;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public void clearValue() {
        this.value = null;
    }

    public abstract String toHelpMessageRepresentation();

    public String getDefaultValueAsString() {
        if (this.defaultValue != null) {
            return this.defaultValue.toString();
        }
        return null;
    }

}
