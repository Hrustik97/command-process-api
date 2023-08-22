package command.process.api.option.argument;

public enum Operator {

    PLUS('+', ((l, r) -> l + r)),
    MINUS('-', ((l, r) -> l - r)),
    MULTIPLY('*', ((l, r) -> l * r)),
    DIVIDE('/', ((l, r) -> l / r));

    private final char operatorSign;

    private final IExpression expression;

    Operator(char operatorSign, IExpression expression) {
        this.operatorSign = operatorSign;
        this.expression = expression;
    }

    public char getOperatorSign() {
        return this.operatorSign;
    }

    public IExpression getExpression() {
        return this.expression;
    }

}
