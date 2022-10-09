package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Restaurant {
    public static Restaurant[] listRestaurants;
    public Menu[] menu;
    public String name;

    public Menu[] getMenu(){
        return menu;
    }

    public Restaurant(@JsonProperty("name") String name,
                      @JsonProperty("menu") Menu[] menu){
        this.name = name;
        this.menu = menu;
    }

    public static Restaurant[] getRestaurantsFromRestServer(URL serverBaseAddress){
//        String baseUrl = "https://ilp-rest.azurewebsites.net/";
//        String echoBasis = "restaurants";

        try{
            listRestaurants = new ObjectMapper().readValue(
                    new URL(serverBaseAddress) , Restaurant[]. class );
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

        System.out.println(participants.length);
    }
}
