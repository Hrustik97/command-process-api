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

    public abstract String helpMessageRepresentation();

    public String defaultValueAsString() {
        if (this.defaultValue != null) {
            return this.defaultValue.toString();
        }
        return null;
    }

}
