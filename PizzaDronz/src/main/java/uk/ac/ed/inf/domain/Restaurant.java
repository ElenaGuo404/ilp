package uk.ac.ed.inf.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Restaurant class is used to present a single participating restaurant and its data retrieved from RestaurantService.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class Restaurant {

    /**
     * The fields menu covers individual restaurant's menu;
     * name of individual participating restaurant;
     * location with given longitude and latitude.
     */
    private final ArrayList<Menu> menu;
    protected final String name;
    private final LngLat location;


    public Restaurant(@JsonProperty("name") String name,
                      @JsonProperty("menu") ArrayList<Menu> menu,
                      @JsonProperty("longitude") double lng,
                      @JsonProperty("latitude") double lat){
        this.name = name;
        this.menu = menu;
        this.location = new LngLat(lng,lat);
    }

    /**
     * A getter method used to get menus list from the given restaurant.
     *
     * @return a list of Menu object
     */
    public ArrayList<Menu> getMenu() {
        return menu;
    }

    /**
     * a getter method used to get the given restaurant's location.
     *
     * @return location in LngLat format
     */
    public LngLat getLocation() {
        return location;
    }
}
