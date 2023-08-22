package command.process.api.executor;

import command.process.api.option.ArgumentOption;
import command.process.api.option.Option;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CommandExecutor {

    protected String getUsageHelpMessage(ArrayList<Option> options) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < options.size(); i++) {
            Option currOption = options.get(i);
            sb.append(currOption.getAliases()[0]);
            if (currOption instanceof ArgumentOption) {
                sb.append(" [").append(((ArgumentOption) currOption).argumentHelpMessageRepresentation()).append("]");
            }
            if (i < options.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public String getHelpMessage(ArrayList<Option> options) {
        StringBuilder sb = new StringBuilder();
        String usageHelpMessage = this.getUsageHelpMessage(options);
        if (usageHelpMessage != null && !usageHelpMessage.isEmpty()) {
            sb.append("Usage: ").append(usageHelpMessage).append('\n');
        }
        sb.append("Options and arguments:\n");
        for (Option option : options) {
            sb.append(option.helpMessageRepresentation()).append('\n');
        }
        return sb.toString();
    }

    public abstract String execute(HashMap<String, Option> optionsMap);

}
