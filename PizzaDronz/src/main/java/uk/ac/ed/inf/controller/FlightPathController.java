package uk.ac.ed.inf.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import uk.ac.ed.inf.domain.*;
import uk.ac.ed.inf.service.OrderService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Float.NaN;

/**
 * The class FlightPathController is used to get daily delivered orders' flightpath
 * and use the data to generate json file.
 */
public class FlightPathController {

    /**
     * The private field delivered stores the list of orders that have been delivered;
     * the deliveredOrderPath stores flightpath of all delivered orders;
     * sortedOrders stores list of valid orders that are able for deliveries;
     * maxBattery stores the value of maximum battery capacity of a drone.
     */
    private static final ArrayList<FlightPath> deliveredOrderPath = new ArrayList<>();
    private static final ArrayList<Order> delivered = new ArrayList<>();
    private static ArrayList<Order> sortedOrders = new ArrayList<>();
    private static final int maxBattery = 2000;

    /**
     * This method is used to get list of valid orders from OrderService
     * and sorted the new list in ascending order based on numbers of individual order flightpath nodes.
     */
    public static void sortedOrderList(){

        for (Order order : OrderService.getListOrders()){

            OrderOutcomeCheck check = new OrderOutcomeCheck(order);
            check.assignOrderOutcome();

            if (order.outcome == OrderOutcome.ValidButNotDelivered){
                sortedOrders.add(order);
            }
        }
        sortedOrders = (ArrayList<Order>) sortedOrders.stream().sorted(Comparator.comparingInt(Order::getPathNumber)).collect(Collectors.toList());
    }

    /**
     * This method takes an order and its path and restore them as flightpath objects;
     * also, as specification given, drone hoovers when it reaches its destination;
     * when delivery the last order back to AT, the order number should be "no-order".
     *
     * @param order An order object
     * @return a list of flightpath objects.
     */
    public static ArrayList<FlightPath> flightPathConvertor(Order order) {

        ArrayList<FlightPath> flightPath = new ArrayList<>();

        String orderNo = order.getOrderNo();
        Path path = order.getOrderPath();

        for (int i = 0; i < path.getPathStartToEnd().size()-1; i++) {
            float fromLongitude = (float) path.getPathStartToEnd().get(i).getLng();
            float fromLatitude = (float) path.getPathStartToEnd().get(i).getLat();

            float angle = path.getAngleStartToEnd().get(i).floatValue();
            float toLongitude = (float) path.getPathStartToEnd().get(i + 1).getLng();
            float toLatitude = (float) path.getPathStartToEnd().get(i + 1).getLat();
            long ticksSinceStartOfCalculation = System.nanoTime();

            flightPath.add(new FlightPath(orderNo, fromLongitude, fromLatitude, angle, toLongitude,
                    toLatitude, ticksSinceStartOfCalculation));
        }

        long ticksSinceStartOfCalculation = System.nanoTime();
        LngLat hoover = path.getPathStartToEnd().get(path.getPathStartToEnd().size()-1);

        // hoover should have angle of null
        flightPath.add(new FlightPath(orderNo, (float)hoover.getLng(),
                (float)hoover.getLat(), NaN, (float)hoover.getLng(),
                (float)hoover.getLat(), ticksSinceStartOfCalculation));

            //from restaurant to Appleton Tower
        String lastOrder = sortedOrders.get(sortedOrders.size() - 1).getOrderNo();
        if (Objects.equals(order.getOrderNo(), lastOrder)){
            orderNo = "no-order";
        }

        for (int i = 0; i < path.getPathEndToStart().size()-1; i++) {

            float fromLongitude = (float) path.getPathEndToStart().get(i).getLng();
            float fromLatitude = (float) path.getPathEndToStart().get(i).getLat();
            float angle = path.getAngleEndToStart().get(i).floatValue();
            float toLongitude = (float) path.getPathEndToStart().get(i + 1).getLng();
            float toLatitude = (float) path.getPathEndToStart().get(i + 1).getLat();
            long ticksSinceStartOfCalculation2 = System.nanoTime();

            flightPath.add(new FlightPath(orderNo, fromLongitude, fromLatitude, angle, toLongitude,
                    toLatitude, ticksSinceStartOfCalculation2));
        }

        long ticksSinceStartOfCalculation3 = System.nanoTime();
        LngLat hoover2 = path.getPathEndToStart().get(path.getPathEndToStart().size()-1);

        flightPath.add(new FlightPath(orderNo, (float)hoover2.getLng(),
                (float)hoover2.getLat(), NaN, (float)hoover2.getLng(),
                (float)hoover2.getLat(), ticksSinceStartOfCalculation3));

        return flightPath;
    }

    /**
     * This method generates daily flightpath based on initial drone battery of 2000;
     * and store the order and its corresponding flightpath.
     */
    public void getDeliveredPath(){

        int battery = maxBattery;
        sortedOrderList();

        for (Order order : sortedOrders) {

            ArrayList<FlightPath> flightPaths = flightPathConvertor(order);
            int batteryNeed = flightPaths.size();

            if (batteryNeed <= battery) {
                deliveredOrderPath.addAll(flightPaths);
                order.outcome = OrderOutcome.Delivered;
                delivered.add(order);

                battery -= batteryNeed;
            }
        }
    }

    /**
     * The method used to generate json file of list of daily delivered orders' flightpath.
     *
     * @param date input String used to name the file
     */
    public void fileGenerator(String date){

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        try {
            writer.writeValue(new File("../flightpath-" + date + ".json"), deliveredOrderPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A getter method used to get list of delivered orders.
     *
     * @return list of delivered orders.
     */
    public static ArrayList<Order> getDelivered() {
        return delivered;
    }

    /**
     * A getter method used to get list of flightpath of all delivered orders.
     *
     * @return list of flightpath
     */
    public static ArrayList<FlightPath> getDeliveredOrderPath() {
        return deliveredOrderPath;
    }

}

