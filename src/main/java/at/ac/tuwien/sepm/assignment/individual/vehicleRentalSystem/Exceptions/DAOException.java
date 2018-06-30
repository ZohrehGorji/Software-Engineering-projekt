package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Exceptions;

public class DAOException extends Exception{

    public DAOException(){
        super();

    }

    /**
     * Creates a new DAOException.
     * @param  error is the message.
     */
    public DAOException(String error){
        super(error);
    }
}
