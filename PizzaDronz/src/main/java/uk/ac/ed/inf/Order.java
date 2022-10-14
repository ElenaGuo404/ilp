package uk.ac.ed.inf;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Order class is used to present consumer's order and its details,
 * including ordered items and total price needs to pay.
 */
public class Order {

    /**
     * The field is the total cost in pence needs to pay of having pizzas delivered by drone.
     */
    public int pricePence;

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
            for (int j = 0; j < participant.menu.length; j++)
                if (pizza.equals(participant.menu[j].name)) {
                    count += 1;
                }
        }
        if (count == listOfPizzas.size()) {
            return true;
        } else return false;
    }

    /**
     * This method is used to get the ordered pizza price if this pizza combination is from a same restaurant.
     * @param participant list of participating restaurants
     * @param listOfPizzas An order has list of pizzas in string format.
     * @return An int value of total pizza price in pence.
     */
    public int sameRestaurants(Restaurant[] participant, List<String> listOfPizzas) {
        for (Restaurant r : participant) {
            if (sameSingleRestaurant(r, listOfPizzas)) {
                for (String pizza : listOfPizzas) {
                    for (int i = 0; i < r.menu.length; i++) {
                        if (pizza.equals(r.menu[i].name)) {
                            pricePence += r.menu[i].priceInPence;
                        }
                    }
                }
            }
        }
        return pricePence;
    }

    /**
     * This method is used to get total delivery cost in pence after checking if the order is valid from various exceptions.
     *
     * @param participants participating restaurants
     * @param listOfPizzas Variable number of strings for the individual pizzas ordered.
     * @return Total cost in pence of having pizzas delivered by drone.
     * @throws InvalidPizzaCombinationException Throw exception when the ordered pizza combination cannot be delivered by the same restaurant.
     * @throws InvalidPizzaCount Throw exception when the ordered pizza count is not valid.
     */
    public int getDeliveryCost(Restaurant[] participants, List<String> listOfPizzas)
            throws InvalidPizzaCombinationException,InvalidPizzaCount {

        int deliveryCharge = 100;

        if (listOfPizzas.size() == 0) {
            throw new InvalidPizzaCount("You don't have pizza ordered");

        } else if (listOfPizzas.size() > 4) {
            throw new InvalidPizzaCount("The drone cannot carry more than FOUR pizzas");

        } else {
            sameRestaurants(participants, listOfPizzas);

            if (pricePence == 0) {
                throw new InvalidPizzaCombinationException("This pizza combination cannot be delivered from same restaurant");
            }
            pricePence = pricePence + deliveryCharge;
        }
        return pricePence;
    }

}
