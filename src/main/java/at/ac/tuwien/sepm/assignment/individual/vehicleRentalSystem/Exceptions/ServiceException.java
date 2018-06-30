package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions;

public class ServiceException extends Exception{
    public ServiceException(){
        super();

    }
    /**
     * Creates a new ServiceException.
     * @param  error is the error.
     */
    public ServiceException(String error){
        super(error);
    }
}
