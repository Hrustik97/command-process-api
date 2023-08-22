package command.process.api;

import command.process.api.exception.CommandProcessAPIException;
import command.process.api.executor.CalculatorCommandExecutor;
import command.process.api.option.argument.EnumArgument;
import command.process.api.option.argument.IntegerArgument;
import command.process.api.option.argument.Operator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandProcessAPITest {

    private static CommandProcessAPI cpa;

    @BeforeAll
    static void setUp() {
        cpa = new CommandProcessAPI(new CalculatorCommandExecutor());
        cpa.addArgumentOption(new String[]{"-l"}, "Left operand of the expression.", new IntegerArgument(-10, 10));
        cpa.addArgumentOption(new String[]{"-r"}, "Right operand of the expression.", new IntegerArgument(-10, 10));
        cpa.addArgumentOption(new String[]{"-o"}, "Operator of the expression.", new EnumArgument(Operator.class, Operator.PLUS));
        cpa.addNonArgumentOption(new String[]{"-v"}, "Verbose option to return the whole expression along with its result.");
    }

    @AfterAll
    static void tearDown() {
        cpa = null;
    }

    @Test
    void process_allOptions_operatorPlus() {
        assertEquals("-10+10=0", cpa.process("-l -10 -r 10 -o PLUS -v"));
    }

    @Test
    void process_allOptions_operatorMinus() {
        assertEquals("-10-10=-20", cpa.process("-l -10 -r 10 -o MINUS -v"));
    }

    @Test
    void process_allOptions_operatorMultiply() {
        assertEquals("-10*10=-100", cpa.process("-l -10 -r 10 -o MULTIPLY -v"));
    }

    @Test
    void process_allOptions_operatorDivide() {
        assertEquals("-10/10=-1", cpa.process("-l -10 -r 10 -o DIVIDE -v"));
    }

    @Test
    void process_withoutVerboseOption() {
        assertEquals("0", cpa.process("-l -10 -r 10 -o PLUS"));
    }

    @Test
    void process_withoutOperatorOption_defaultPlusOperator() {
        assertEquals("-10+10=0", cpa.process("-l -10 -r 10 -v"));
    }

    @Test
    void process_withoutOperatorAndVerboseOptions() {
        assertEquals("0", cpa.process("-l -10 -r 10"));
    }

    @Test
    void process_withoutMandatoryArguments_throwsException() {
        assertThrows(CommandProcessAPIException.class, () -> cpa.process("-o PLUS -v"));
    }

    @Test
    void process_helpOptionShortAlias() {
        assertEquals(cpa.getHelpMessage(), cpa.process("-h"));
    }

    @Test
    void process_helpOptionLongAlias() {
        assertEquals(cpa.getHelpMessage(), cpa.process("--help"));
    }

    @Test
    void process_helpOptionOverridingOtherOptions() {
        assertEquals(cpa.getHelpMessage(), cpa.process("-l -10 -r 10 -o PLUS -v -h"));
    }

    @Test
    void process_invalidCommand_throwsException() {
        assertThrows(CommandProcessAPIException.class, () -> cpa.process("invalid command"));
    }

    @Test
    void process_commandWithRandomlyPlacedWhiteSpaces_worksNormally() {
        assertEquals("-10+10=0", cpa.process("   -l  -10 -r     10  -o   PLUS -v  "));
    }

    @Test
    void process_zeroDivision_throwsException() {
        assertThrows(ArithmeticException.class, () -> cpa.process("-l -10 -r 0 -o DIVIDE -v"));
    }

    @Test
    void process_minValueRestrictionViolation_throwsException() {
        assertThrows(CommandProcessAPIException.class, () -> cpa.process("-l -11 -r 10 -o PLUS -v"));
    }

    @Test
    void process_maxValueRestrictionViolation_throwsException() {
        assertThrows(CommandProcessAPIException.class, () -> cpa.process("-l -10 -r 11 -o PLUS -v"));
    }

    @Test
    void process_invalidOperatorValue_throwsException() {
        assertThrows(CommandProcessAPIException.class, () -> cpa.process("-l -10 -r 11 -o INVALID -v"));
    }

}