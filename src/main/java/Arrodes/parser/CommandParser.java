package arrodes.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import arrodes.command.ByeCommand;
import arrodes.command.Command;
import arrodes.command.DeadlineCommand;
import arrodes.command.DeleteCommand;
import arrodes.command.EventCommand;
import arrodes.command.FindCommand;
import arrodes.command.ListCommand;
import arrodes.command.MarkCommand;
import arrodes.command.TodoCommand;
import arrodes.command.UnmarkCommand;
import arrodes.command.UpcomingCommand;
import arrodes.exception.ArrodesException;

/** Converts raw CLI text into validated command objects. */
public class CommandParser {

    /** Creates a parser; parsing operations are available as static methods. */
    public CommandParser() {
    }

    /**
     * Splits raw input into a command word, description, and slash-prefixed parameters.
     * @param userInput input entered at the CLI prompt
     * @return tokenized representation of the input
     * @throws ArrodesException if the input is null or blank, or has malformed parameters
     */
    public static TokenizedCommand tokenize(String userInput) throws ArrodesException {
        if (userInput == null || userInput.isBlank()) {
            throw new ArrodesException(ArrodesException.NO_INPUT);
        }

        /*
         * Splits the input given into 2 chunks [command, rest of input]
         */
        String[] splitInput = userInput.split(" ", 2);

        String command = splitInput[0];
        String description = "";
        Map<String, String> parameters = new HashMap<>();

        //description unavailable
        if (splitInput.length < 2) {
            return new TokenizedCommand(command, description, parameters);
        }

        String remainingInput = splitInput[1];
        String[] sections = splitByFlag(remainingInput);
        description = sections[0];
        parameters = formParameters(sections);
        return new TokenizedCommand(command, description, parameters);
    }

    /**
     * Validates tokenized input and creates the corresponding command.
     * @param userInput raw command entered by the user
     * @return executable command
     * @throws ArrodesException if the command or its arguments are invalid
     */
    public static Command parse(String userInput) {
        TokenizedCommand tokenizeCommand = tokenize(userInput);

        Map<String, String> parameters = tokenizeCommand.getParameters();
        Command command;
        switch (tokenizeCommand.getCommand()) {
            case "bye":
                if (tokenizeCommand.hasDescription()) {
                    throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                }
                command = new ByeCommand();
                break;
            case "mark":
                if (!tokenizeCommand.hasDescription()) {
                    throw new ArrodesException(ArrodesException.EMPTY_DESCRIPTION);
                }
                if (tokenizeCommand.hasParameters()) {
                    throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                }
                try {
                    command = new MarkCommand(Integer.parseInt(tokenizeCommand.getDescription()));
                } catch (NumberFormatException e) {
                    throw new ArrodesException(ArrodesException.NOT_A_NUMBER);
                }
                break;
            case "unmark":
                if (!tokenizeCommand.hasDescription()) {
                    throw new ArrodesException(ArrodesException.EMPTY_DESCRIPTION);
                }
                if (tokenizeCommand.hasParameters()) {
                    throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                }
                try {
                    command = new UnmarkCommand(Integer.parseInt(tokenizeCommand.getDescription()));
                } catch (NumberFormatException e) {
                    throw new ArrodesException(ArrodesException.NOT_A_NUMBER);
                }
                break;
            case "delete":
                if (!tokenizeCommand.hasDescription()) {
                    throw new ArrodesException(ArrodesException.EMPTY_DESCRIPTION);
                }
                if (tokenizeCommand.hasParameters()) {
                    throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                }
                try {
                    command = new DeleteCommand(Integer.parseInt(tokenizeCommand.getDescription()));
                } catch (NumberFormatException e) {
                    throw new ArrodesException(ArrodesException.NOT_A_NUMBER);
                }
                break;
            case "todo":
                if (!tokenizeCommand.hasDescription()) {
                    throw new ArrodesException(ArrodesException.EMPTY_DESCRIPTION);
                }
                if (tokenizeCommand.hasParameters()) {
                    throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                }
                command = new TodoCommand(tokenizeCommand.getDescription());
                break;
            case "deadline":
                if (!tokenizeCommand.hasDescription()) {
                    throw new ArrodesException(ArrodesException.EMPTY_DESCRIPTION);
                }
                if (!parameters.containsKey("by")) {
                    throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                }
                command = new DeadlineCommand(tokenizeCommand.getDescription(),
                        parseDateTime(parameters.get("by")));
                break;
            case "event":
                if (!tokenizeCommand.hasDescription()) {
                    throw new ArrodesException(ArrodesException.EMPTY_DESCRIPTION);
                }
                if (!parameters.containsKey("from")) {
                    throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                }
                if (!parameters.containsKey("to")) {
                    throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                }
                String from = parameters.get("from");
                String to = parameters.get("to");

                //Bitwise Xor, we only want both to be LocalDate or LocalDateTime
                if (from.contains("T") ^ to.contains("T")) {
                    throw new ArrodesException("Time provided should both be same format");
                }
                //Insert Code Between

                command = new EventCommand(tokenizeCommand.getDescription(),
                        parseDateTime(from),
                        parseDateTime(to),
                        from.contains("T"),
                        to.contains("T"));
                break;
            case "list":
                if (tokenizeCommand.hasDescription()) {
                    throw new ArrodesException("try list without other words");
                }
                if (tokenizeCommand.hasParameters()) {
                    throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                }
                command = new ListCommand();
                break;
            case "find":
                if (!tokenizeCommand.hasDescription()) {
                    throw new ArrodesException(ArrodesException.EMPTY_DESCRIPTION);
                }
                if (tokenizeCommand.hasParameters()) {
                    throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                }
                command = new FindCommand(tokenizeCommand.getDescription());
                break;
            case "upcoming":
                if (tokenizeCommand.hasDescription()) {
                    throw new ArrodesException("Description is not needed");
                }
                if (!parameters.containsKey("on")) {
                    throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
                }
                command = new UpcomingCommand(parseDateTime(parameters.get("on")), parameters.get("on").contains("T"));
                break;
            default:
                throw new ArrodesException(ArrodesException.UNKNOWN_COMMAND);
        }
        return command;
    }

    /**
     * Splits the portion after the command word at slash separators and trims each section.
     * @param remainingInput content after the command keyword
     * @return description followed by parameter sections
     */
    private static String[] splitByFlag(String remainingInput) {
        String[] sections = remainingInput.split("/");
        for (int i = 0; i < sections.length; i++) {
            sections[i] = sections[i].strip();
        }
        return sections;
    }

    /**
     * Converts parameter sections into flag-to-value mappings.
     * @param sections description followed by parameter sections
     * @return parsed parameter mappings
     * @throws ArrodesException if a parameter does not contain both a flag and value
     */
    private static Map<String, String> formParameters(String[] sections) throws ArrodesException {
        Map<String, String> parameters = new HashMap<>();
        try {
            for (int i = 1; i < sections.length; i++) {
                String[] flagAndValue = sections[i].split(" ", 2);
                String flag = flagAndValue[0];
                String value = flagAndValue[1];
                parameters.put(flag, value);
            }
        } catch (Exception e) {
            throw new ArrodesException(ArrodesException.INCORRECT_PARAMS);
        }
        return parameters;
    }
    /**
     * Parses an ISO date or date-time, normalising date-only values to midnight.
     * @param value date in {@code yyyy-MM-dd} or date-time in {@code yyyy-MM-ddTHH:mm} form
     * @return parsed local date-time
     * @throws ArrodesException if the value has an invalid format or date
     */
    private static LocalDateTime parseDateTime(String value) throws ArrodesException {
        boolean hasExpectedShape = value != null
                && value.matches("\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2})?");
        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException exception) {
            try {
                return LocalDate.parse(value).atStartOfDay();
            } catch (DateTimeParseException ignored) {
                if (hasExpectedShape) {
                    throw new ArrodesException(
                            "That date is invalid because the specified day or time does not exist.");
                }
                throw new ArrodesException("Use event times in yyyy-MM-dd or yyyy-MM-ddTHH:mm format.");
            }
        }
    }
}
