package uk.ac.ed.inf;

/**
 * InvalidPizzaCombinationException class is used to throw error
 * when ordered pizza combination cannot be delivered by the same restaurant.
 */

public class InvalidPizzaCombinationException extends RuntimeException{

    /**
     * @param message Text messages when error produced in String format.
     */
    public InvalidPizzaCombinationException (String message){
        super(message);
    }
}
