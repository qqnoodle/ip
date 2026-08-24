import java.lang.reflect.Parameter;
import java.util.Map;

public class TokenizedCommand {
    private final String command;
    private final String description;
    private final Map<String, String> parameters;

    public TokenizedCommand(String command, String description, Map<String, String> parameters) {
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
    public boolean hasDescription() {
        return !description.isBlank() | !description.isEmpty();
    }
    public Map<String, String> getParameters() {
        return parameters;
    }

    public boolean hasParameters() {
        return !parameters.isEmpty();
    }
}
