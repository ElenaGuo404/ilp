package uk.ac.ed.inf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ed.inf.domain.Order;

import java.io.IOException;
import java.net.URL;

/**
 * OrderService is a singleton access pattern class.
 * It creates the object by reading data from REST-service.
 */
public class OrderService {

    /**
     * The private field listOrders stores the list of orders from same day by REST-Server.
     */
    private static Order[] listOrders;
    private static OrderService INSTANCE;

    public OrderService(String serverBaseAddress, String date){

        String echoBasis = "/orders/";

        try{
            listOrders = new ObjectMapper().readValue(
                    new URL(serverBaseAddress + echoBasis + date), Order[].class );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A getter method allow other class to read list of orders.
     *
     * @return a list of orders from same day
     */
    public static Order[] getListOrders() {
        return listOrders;
    }

    /**
     * This method is used to initialise order service.
     *
     * @param serverBaseAddress input string of valid URL base address of REST-Server
     * @param date              input string of valid date in format: yyyy-mm-dd
     */
    public static void init(String serverBaseAddress, String date){
        if (INSTANCE == null){
            INSTANCE = new OrderService(serverBaseAddress,date);
        }
    }

}
