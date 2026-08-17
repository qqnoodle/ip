public class ArrodesException extends RuntimeException {

    public static final String UNKNOWN_COMMAND = "Arrodes has not learn to process this request!!";
    public static final String EMPTY_DESCRIPTION = "Arrodes requires more information of the nature of the task";
    public static final String INSUFFICIENT_PARAMS = "Arrodes has not learn to process this request!!";

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
