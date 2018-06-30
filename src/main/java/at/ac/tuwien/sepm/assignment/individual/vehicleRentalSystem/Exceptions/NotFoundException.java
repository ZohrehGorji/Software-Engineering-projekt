package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions;


public class NotFoundException extends Exception {

    public NotFoundException() {
        super(); }
    /**
     * Creates a new NotFoundException.
     * @param  ExceptionMessage is the message.
     */
    public NotFoundException(String ExceptionMessage) {
        super(ExceptionMessage);
    }
    /**
     * Creates a new NotFoundException.
     * @param  cause is the cause.
     */
    public NotFoundException(Throwable cause) {
        super(cause);
    }
    /**
     * Creates a new NotFoundException.
     * @param  ExceptionMessage is the message.
     * @param cause is the cause.
     */
    public NotFoundException(String ExceptionMessage, Throwable cause) {
        super(ExceptionMessage, cause);
    }


}
