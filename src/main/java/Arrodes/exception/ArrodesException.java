package arrodes.exception;

/** Unchecked exception used for expected application-level errors. */
public class ArrodesException extends RuntimeException {
    /** Message used when no input is provided. */
    public static final String NO_INPUT = "Arrodes cannot decipher your intentions...";
    /** Message used for an unsupported command. */
    public static final String UNKNOWN_COMMAND = "Arrodes has not learn to process this request!!";
    /** Message used when a task description is absent. */
    public static final String EMPTY_DESCRIPTION = "Arrodes requires more information of the nature of the task";
    /** Message used when required parameters are absent. */
    public static final String INCORRECT_PARAMS_COUNT = "Arrodes requires more magic words"
            + "(parameters) for the request!!";
    /** Message used when parameters have an invalid shape. */
    public static final String INCORRECT_PARAMS = "Your ritualistic magic parameters is incorrect!!!";
    /** Message used when an event ends before it starts. */
    public static final String INVALID_EVENT_TIME = "Event end time must not be earlier than its start time.";

    /** Message used when the task list has reached capacity. */
    public static final String TASK_LIST_FULL = "Records of Arrodes reached its limit and"
            + "can no longer accept more request!!!";
    /** Message used when a requested task number is invalid. */
    public static final String ITEM_NOT_IN_LIST = "That task does not appear in Arrodes' annals.";

    /** Message used when a mark, unmark, or delete number is not numeric. */
    public static final String NOT_A_NUMBER = "Arrodes require a number mark/unmark 22";

    /**
     * Constructs an exception without a detail message.
     */
    public ArrodesException() {
        super();
    }

    /**
     * Constructs an exception with a detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public ArrodesException(String message) {
        super(message);
    }
}
