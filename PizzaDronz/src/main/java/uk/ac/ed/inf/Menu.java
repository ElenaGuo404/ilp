package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Menu class is used to present the restaurants' menu from REST-request.
 */
public class Menu {

    /**
     * The fields are used to cover each pizza's name and its cost in pence.
     */
    public int priceInPence;
    public String name;

    public Menu(@JsonProperty("name") String name,
                      @JsonProperty("priceInPence") int priceInPence){
        this.name = name;
        this.priceInPence = priceInPence;
    }
}
