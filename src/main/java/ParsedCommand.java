import java.lang.reflect.Parameter;
import java.util.Map;

public class ParsedCommand {
    private String command;
    private String description;
    private Map<String, String> parameters;

    public ParsedCommand(String command, String description, Map<String, String> parameters) {
        this.command = command;
        this.description = description;
        this.parameters = parameters;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
