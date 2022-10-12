package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)

/**
 * Restaurant class is used to present the participating restaurant from REST-request.
 */

public class Restaurant {

    /**
     * The fields listRestaurants to cover the participating restaurant from REST-request;
     * menu to cover individual restaurant's menu;
     * name of individual restaurant.
     */
    public static Restaurant[] listRestaurants;
    public Menu[] menu;
    public String name;

    public Menu[] getMenu(){
        return this.menu;
    }

    public Restaurant(@JsonProperty("name") String name,
                      @JsonProperty("menu") Menu[] menu){
        this.name = name;
        this.menu = menu;
    }

    /**
     * This method is used to get all participating restaurants from REST request, including their menus.
     *
     * @param serverBaseAddress An url link giving information of participating restaurants.
     * @return list of participating restaurants
     */
    public static Restaurant[] getRestaurantsFromRestServer(URL serverBaseAddress){

        try{
            listRestaurants = new ObjectMapper().readValue(
                    serverBaseAddress , Restaurant[].class );
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listRestaurants ;
    }

    public static void main(String[] args) throws MalformedURLException {

        Restaurant [] participants = Restaurant.getRestaurantsFromRestServer(
                new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        for (Menu m: participants[0].getMenu()) {
            System.out.println(m.name);
        }
    }
}
