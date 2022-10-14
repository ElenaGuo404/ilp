package uk.ac.ed.inf;

/**
 * InvalidPizzaCount is used to throw error when there are too many pizzas in one order OR it is an empty order.
 */
public class InvalidPizzaCount extends RuntimeException{

    /**
     * @param message Text messages produced when error occur in String format.
     */
    public InvalidPizzaCount (String message){
        super(message);
    }
}
