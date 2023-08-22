package command.process.api.option.argument;

@FunctionalInterface
public interface IExpression {

    Integer compute(Integer leftOperand, Integer rightOperand);

}