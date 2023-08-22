package command.process.api.executor;

import command.process.api.option.argument.Operator;
import command.process.api.option.Option;

import java.util.HashMap;

public class CalculatorCommandExecutor extends CommandExecutor {

    @Override
    public String execute(HashMap<String, Option> optionsMap) {
        Integer leftOperand = (Integer) optionsMap.get("-l").getValue();
        Integer rightOperand = (Integer) optionsMap.get("-r").getValue();
        Operator operator = (Operator) optionsMap.get("-o").getValue();
        Boolean verbose = (Boolean) optionsMap.get("-v").getValue();

        Integer result = operator.getExpression().compute(leftOperand, rightOperand);
        if (verbose) {
            return String.format("%d%c%d=%d", leftOperand, operator.getOperatorSign(), rightOperand, result);
        }
        return result.toString();
    }

}
