public class ArrodesException extends RuntimeException {
    public static final String NO_INPUT = "Arrodes cannot decipher your intentions...";
    public static final String UNKNOWN_COMMAND = "Arrodes has not learn to process this request!!";
    public static final String EMPTY_DESCRIPTION = "Arrodes requires more information of the nature of the task";
    public static final String INCORRECT_PARAMS_COUNT = "Arrodes requires more magic words (parameters) for the request!!";
    public static final String INCORRECT_PARAMS = "Your ritualistic magic parameters is incorrect!!!";
    public static final String TASK_LIST_FULL = "Records of Arrodes reached its limit and can no longer accept more request!!!";

    /**
     * Constructs a new ArrodesException with the default RuntimeException
     */
    public ArrodesException() {
        super();
    }

    /**
     * Constructs a new ArrodesException with the default RuntimeException
     *
     * @param message the detail message explaining the reason for the exception
     */
    public ArrodesException(String message) {
        super(message);
    }
}
