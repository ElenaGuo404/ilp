package uk.ac.ed.inf.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ed.inf.service.RestaurantService;

import java.util.List;

/**
 * Order class is used to present consumer's order and its details,
 * including ordered items and total price needs to pay.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    /**
     * The fields contain individual order's data and the delivery charge applied to every order.
     */
    protected final int deliveryCharge = 100;
    private final String orderNo;
    private final String orderDate;
    protected final String customer;
    private final String creditCardNumber;
    private final String creditCardExpiry;
    private final String cvv;
    private final Integer priceTotalInPence;
    private final List<String> orderItems;
    public OrderOutcome outcome;

    public Order(@JsonProperty("orderNo") String orderNo,
                 @JsonProperty("orderDate") String orderDate,
                 @JsonProperty("customer") String customer,
                 @JsonProperty("creditCardNumber") String creditCardNumber,
                 @JsonProperty("creditCardExpiry") String creditCardExpiry,
                 @JsonProperty("cvv") String cvv,
                 @JsonProperty("priceTotalInPence") Integer priceTotalInPence,
                 @JsonProperty("orderItems") List<String> orderItems) {

        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.customer = customer;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpiry = creditCardExpiry;
        this.cvv = cvv;
        this.priceTotalInPence = priceTotalInPence;
        this.orderItems = orderItems;
    }


    /**
     * This method is used to detect whether these ordered pizzas are from a same suppliers or not.
     *
     * @param participant  a single participating restaurant
     * @param listOfPizzas An order has list of pizzas in string format.
     * @return A boolean value that uses 'true' to present these pizzas are from same supplier.
     */
    public boolean sameSingleRestaurant(Restaurant participant, List<String> listOfPizzas) {

        int count = 0;
        for (String pizza : listOfPizzas) {
            for (int j = 0; j < participant.getMenu().size(); j++)
                if (pizza.equals(participant.getMenu().get(j).getName())) {
                    count += 1;
                }
        }
        return count == listOfPizzas.size();
    }

    /**
     * This method is used to get pizzas' price by checking the pizza name
     * has a corresponding menu name from a restaurant.
     *
     * @param restaurant   a single participating restaurant
     * @param listOfPizzas list of ordered pizzas
     * @return a int value of pizzas' cost
     */
    public int getPizzaPrice(Restaurant restaurant, List<String> listOfPizzas) {
        int price = 0;

        for (String pizza : listOfPizzas) {
            for (Menu menu : restaurant.getMenu()) {
                if (pizza.equals(menu.getName())) {
                    price += menu.getPriceInPence();
                }
            }
        }
        return price;
    }

    /**
     * This method is used to get ordered pizzas' price from all restaurant with delivery charge fee.
     *
     * @return an int value of total price for given order
     */
    public int getDeliveryCost() {

        int pricePence = 0;

        for (Restaurant r : RestaurantService.getListRestaurant()) {
            pricePence += getPizzaPrice(r, orderItems);
        }
        pricePence += deliveryCharge;
        return pricePence;
    }

    /**
     * This method is used to get the given order's corresponding restaurant
     *
     * @return a Restaurant object
     */
    public Restaurant getOrderRestaurant() {

        for (Restaurant r : RestaurantService.getListRestaurant()) {
            if (sameSingleRestaurant(r, orderItems)) {
                return r;
            }
        }
        return null;
    }

    /**
     * This method is used to get the given Order's corresponding path from AT to restaurant and back.
     *
     * @return a Path object
     */
    public Path getOrderPath() {

        LngLat target = getOrderRestaurant().getLocation();

        Path path = new Path(target);
        path.pathSearching();
        path.pathInverse();

        return path;
    }

    /**
     * This method is used to get the numbers of given order's path nodes.
     *
     * @return a int value of number of nodes
     */
    public int getPathNumber() {
        return getOrderPath().getPathEndToStart().size();
    }

    /**
     * A getter method used to get corresponding order number.
     *
     * @return order number in String format
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * A getter method used to get corresponding order date.
     *
     * @return order date in String format
     */
    public String getOrderDate() {

        return orderDate;
    }

    /**
     * A getter method used to get corresponding credit card number used to pay.
     *
     * @return card number in String format
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * A getter method used to get corresponding credit card's expiry dates.
     *
     * @return expiry dates in String format
     */
    public String getCreditCardExpiry() {
        return creditCardExpiry;
    }

    /**
     * A getter method used to get corresponding credit card cvv.
     *
     * @return cvv number in String format
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * A getter method used to get corresponding total price.
     *
     * @return order price in Integer format
     */
    public int getPriceTotalInPence() {
        return priceTotalInPence;
    }

    /**
     * A getter method used to get corresponding list of ordered pizza items.
     *
     * @return list of ordered pizzas items in String format
     */
    public List<String> getOrderItems() {
        return orderItems;
    }

}