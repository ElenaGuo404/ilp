package uk.ac.ed.inf.controller;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import uk.ac.ed.inf.domain.*;
import uk.ac.ed.inf.service.OrderService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The class DeliveriesController is used to generate files that contains list of deliveries objects,
 * including the delivered order and non-delivered orders for the given date.
 */
public class DeliveriesController {

    /**
     * The private field deliveries has list of Deliveries objects.
     */
    private static final ArrayList<Deliveries> deliveries = new ArrayList<>();

    /**
     * This method is used to get daily orders from OrderService,
     * and re-assign order outcome if the order have been delivered by drone.
     */
    public static void deliveriesConverter(){

        for (Order order : OrderService.getListOrders()) {

            OrderOutcomeCheck check = new OrderOutcomeCheck(order);
            check.assignOrderOutcome();

            if (FlightPathController.getDelivered().contains(order)){
                order.outcome = OrderOutcome.Delivered;
            }
            deliveries.add(new Deliveries(order.getOrderNo(),String.valueOf(order.outcome), order.getPriceTotalInPence()));
        }
    }

    /**
     * The method is used to generate json files for list of deliveries.
     *
     * @param date input String used to name the files
     */
    public static void fileGenerator(String date) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(new File("../deliveries-" + date + ".json"), deliveries);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
