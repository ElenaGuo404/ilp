package uk.ac.ed.inf;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Order class is used to present valid consumer's order and its details,
 * including ordered items and total price needs to pay.
 */
public class Order {

    /**
     * The field is the total cost in pence needs to pay of having pizzas delivered by drone.
     */
    public int pricePence;

    /**
     * This method is used to detect whether these ordered pizzas are from the same suppliers or not.
     *
     * @param participants a single participating restaurant
     * @param listOfPizzas An order has list of pizzas in string format.
     * @return A boolean value that uses 'true' to present these pizzas are from same supplier.
     */
    public boolean sameRestaurant(Restaurant participants, List<String> listOfPizzas) {

        int count = 0;
        for (String pizza : listOfPizzas) {
            for (int j = 0; j < participants.menu.length; j++)
                if (pizza.equals(participants.menu[j].name)) {
                    count += 1;
                }
        }
        if (count == listOfPizzas.size()) {
            return true;
        }else return false;
    }

    /**
     * This method is used to get this total cost in pence from valid order of having pizzas delivered by drone.
     *
     * @param participants participating restaurants
     * @param listOfPizzas Variable number of strings for the individual pizzas ordered.
     * @return Total cost in pence of having pizzas delivered by drone.
     * @throws InvalidPizzaCombinationException Throw exception when the ordered pizza combination cannot be delivered by the same restaurant.
     */
    public int getDeliveryCost(Restaurant[] participants, List<String> listOfPizzas) throws InvalidPizzaCombinationException{
        int deliveryCharge = 100;

        if (listOfPizzas.size() == 0){
            throw new InvalidPizzaCombinationException("you don't have pizza ordered");
            //System.err.println("you don't have pizza ordered");
        }

        if (listOfPizzas.size() > 4){
            //System.err.println("You eat too much");
            throw new InvalidPizzaCombinationException("You eat too much");

        }
        else {
            for (Restaurant r : participants) {
                if (sameRestaurant(r, listOfPizzas)) {
                    for (String pizza : listOfPizzas) {
                        for (int j = 0; j < r.menu.length; j++)
                            if (pizza.equals(r.menu[j].name)) {
                                pricePence += r.menu[j].priceInPence;
                            }
                    }
                }
            }

            if (pricePence == 0) {
                throw new InvalidPizzaCombinationException("Not same restaurant");
            } else pricePence = pricePence + deliveryCharge;
        }
        return pricePence;
    }

    public static void main(String[] args) throws MalformedURLException, InvalidPizzaCombinationException {
        Restaurant[] participants =
                Restaurant.getRestaurantsFromRestServer(new
                        URL("https://ilp-rest.azurewebsites.net/restaurants"));
        Order order = new Order();
        List<String> pizzas = new ArrayList<>();

//        pizzas.add("Margarita");
//        pizzas.add("Margarita");
//        pizzas.add("Margarita");
//        pizzas.add("Margarita");
//        pizzas.add("Margarita");
//        pizzas.add("Vegan Delight");
//        pizzas.add("Meat Lover");
//        pizzas.add("Meat Lover");
//        pizzas.add("Meat Lover");
//
//        pizzas.add("Vegan Delight");
//        pizzas.add("Meat Lover");
        //System.out.println(order.sameRestaurant(participants[0], pizzas));
        System.out.println(order.getDeliveryCost(participants,pizzas));
    }
}
