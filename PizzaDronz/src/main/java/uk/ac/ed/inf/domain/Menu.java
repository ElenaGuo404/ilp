package uk.ac.ed.inf.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Menu class is used to present the restaurant's menu from REST-request.
 */
public class Menu {

    /**
     * The fields are used to cover each pizza's name and its price (in pence).
     */
    private final int priceInPence;
    private final String name;

    public Menu(@JsonProperty("name") String name,
                      @JsonProperty("priceInPence") int priceInPence){
        this.name = name;
        this.priceInPence = priceInPence;
    }

    /**
     * A getter method used to get the corresponding pizza's price
     *
     * @return price of int format
     */
    public int getPriceInPence() {
        return priceInPence;
    }

    /**
     * A getter method used to get the pizza's name
     *
     * @return name of a pizza of String format
     */
    public String getName() {
        return name;
    }
}
