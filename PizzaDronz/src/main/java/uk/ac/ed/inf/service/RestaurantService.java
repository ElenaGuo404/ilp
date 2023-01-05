package uk.ac.ed.inf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ed.inf.domain.Restaurant;

import java.io.IOException;
import java.net.URL;

/**
 * RestaurantService is a singleton access pattern class.
 * It creates objects by reading data from REST-Server.
 */
public class RestaurantService {

    /**
     * The private field listRestaurant stores list of participating restaurant from REST-Server.
     */
    private static Restaurant[] listRestaurant;
    private static RestaurantService INSTANCE;

     public RestaurantService(String serverBaseAddress){

         String echoBasis = "/restaurants";

         try{
             listRestaurant = new ObjectMapper().readValue(
                     new URL(serverBaseAddress + echoBasis), Restaurant[].class );
        } catch (IOException e) {
            e.printStackTrace();
        }
     }

    /**
     * A getter method used to allow other class have access of list of participating restaurants.
     *
     * @return list of participating restaurants
     */
    public static Restaurant[] getListRestaurant() {
        return listRestaurant;
    }

    /**
     * This method is used to initialise the restaurant service.
     *
     * @param serverBaseAddress input String of valid REST-Server base address URL
     */
    public static void init(String serverBaseAddress){
        if (INSTANCE == null){
            INSTANCE = new RestaurantService(serverBaseAddress);
        }
    }

}
