import java.lang.reflect.Parameter;
import java.util.Map;

public class ParsedCommand {
    private final Command command;
    private final String description;
    private final Map<String, String> parameters;

    public ParsedCommand(Command command, String description, Map<String, String> parameters) {
        this.command = command;
        this.description = description;
        this.parameters = parameters;
    }

    public Command getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
