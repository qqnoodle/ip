import java.util.Map;
import java.util.HashMap;

public class CommandParser {

    /**
     * Receives user input and returns ParsedCommand which encapsulates the input
     * @param userInput input that user gave the CLI when prompted for response
     * @return ParsedCommand
     */
    public static ParsedCommand parse(String userInput) throws ArrodesException{
        if (userInput == null || userInput.isBlank()) {
            throw new ArrodesException(ArrodesException.NO_INPUT);
        }

        /*
         * Splits the input given into 2 chunks [command, rest of input]
         */
        String[] splitInput = userInput.split(" ", 2);

        String command = splitInput[0];
        String description = "";
        Map<String,String> parameters = new HashMap<>();

        //description unavailable
        if (splitInput.length < 2) return new ParsedCommand(command,description,parameters);

        String remainingInput = splitInput[1];
        String[] sections = splitByFlag(remainingInput);
        description = sections[0];
        parameters = formParameters(sections);
        return new ParsedCommand(command, description, parameters);
    }

    /**
     * Splits the remaining input by /
     * Input: buy ice cream /from today /by tomorrow
     * Returns : ["buy ice cream", "from today", "by tomorrow"]
     * @param remainingInput content of command after the command keyword
     * @return string split into chunks
     */
    private static String[] splitByFlag(String remainingInput) {
        String[] sections = remainingInput.split("/");
        for (int i = 0; i < sections.length; i++) {
            sections[i] = sections[i].strip();
        }
        return sections;
    }

    /**
     *
     * @param sections contains chunks of string after broken down by separator
     * @return Map of parameters
     */
    private static Map<String, String> formParameters(String[] sections) throws ArrodesException {
        Map<String,String> parameters = new HashMap<>();
        try {
            for (int i = 1; i < sections.length; i ++) {
                String[] flagAndValue = sections[i].split(" ",2);
                String flag = flagAndValue[0];
                String value = flagAndValue[1];
                parameters.put(flag, value);
            }
        } catch (Exception e) {
            throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
        }
        return parameters;
    }
}
