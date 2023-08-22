package command.process.api.option.argument;

import command.process.api.exception.CommandProcessAPIException;

import java.util.Arrays;

public class EnumArgument extends Argument {

    private Class enumClass;

    public <T extends Enum<T>> EnumArgument(Class<T> enumClass) {
        super();
        this.validateAndConstruct(enumClass);
    }

    public <T extends Enum<T>> EnumArgument(Class<T> enumClass, T defaultValue) {
        super(defaultValue);
        this.validateAndConstruct(enumClass);
    }

    private <T extends Enum<T>> void validateAndConstruct(Class<T> enumClass) {
        if (enumClass == null || enumClass.getEnumConstants().length == 0) {
            throw new CommandProcessAPIException("enumClass must contain at least one valid enum constant");
        }
        this.enumClass = enumClass;
    }

    @Override
    public void assignValue(String value) {
        for (Object enumConstant : this.enumClass.getEnumConstants()) {
            if (value.equals(enumConstant.toString())) {
                this.value = enumConstant;
                return;
            }
        }
        throw new CommandProcessAPIException(String.format(
            "The value '%s' doesn't match any of the permitted values: [%s]", value, this.helpMessageRepresentation()
        ));
    }

    @Override
    public String helpMessageRepresentation() {
        return String.join(" | ", Arrays.stream(this.enumClass.getEnumConstants()).map(Object::toString).toArray(String[]::new));
    }

}
