package uk.ac.ed.inf.domain;

import uk.ac.ed.inf.service.RestaurantService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderOutcomeCheck class is used to check the given order status to assign its order outcome.
 */
public class OrderOutcomeCheck {

    /**
     * The field is an Order object used for checking and assign outcomes.
     */
    private final Order order;

    public OrderOutcomeCheck(Order order){
        this.order = order;
    }

    /**
     * This method is used to check if the given order has invalid card numbers.
     *
     * @return A boolean value use 'true' to present invalid.
     */
    public boolean InvalidCardNumber() {

        int sum = 0;
        if (order.getCreditCardNumber().length() != 16) {
            return true;
        }

        //split String card numbers to array list of single digits
        char[] cardNumbers = order.getCreditCardNumber().toCharArray();
        int[] cardNumbersInt = new int[order.getCreditCardNumber().length()];

        for (int i = 0; i < order.getCreditCardNumber().length(); i++) {
            cardNumbersInt[i] = Character.getNumericValue(cardNumbers[i]);
        }
        //last digit of card numbers
        int checker = cardNumbersInt[order.getCreditCardNumber().length() - 1];

        for (int j = 0; j < cardNumbersInt.length; j++) {

            if (j % 2 == 0) {
                int adder = cardNumbersInt[j] * 2;

                while (adder != 0) {
                    sum += adder % 10;
                    adder = adder / 10;
                }
            } else {
                sum += cardNumbersInt[j];

            }
        }

        // If the given checker is 0, then it will return invalid for all orders.
        if (checker == 0){
            sum = (sum - checker) % 10;
        }
        else {
            sum = 10 - ((sum - checker) % 10);
        }
        return sum != checker;
    }

    /**
     * This method is used to check if the given order has invalid credit card expiry date.
     *
     * @return A boolean value use 'true' to present invalid.
     */
    public boolean InvalidExpiryDate() {
        LocalDate date = LocalDate.parse(order.getOrderDate());
        String[] time = order.getCreditCardExpiry().split("/");
        int month = Integer.parseInt(time[0]);
        int year = Integer.parseInt(time[1]);

        return order.getCreditCardExpiry().length() != 5 || year < date.getYear() - 2000 ||
                (year == date.getYear() - 2000) && (month < date.getMonthValue());
    }

    /**
     * This method is used to check if the given order has invalid credit card cvv.
     *
     * @return A boolean value use 'true' to present invalid.
     */
    public boolean InvalidCvv() {
        return !(order.getCvv().length() == 3);
    }

    /**
     * This method is used to check if the given order has invalid total order price.
     *
     * @return A boolean value use 'true' to present invalid.
     */
    public boolean InvalidTotal() {
        return order.getDeliveryCost() != order.getPriceTotalInPence();
    }

    /**
     * This method is used to check if the given order has invalid pizza name
     * that are not from any of participating restaurant.
     *
     * @return A boolean value use 'true' to present invalid.
     */
    public boolean InvalidPizzaNotDefined() {

        List<String> menus = new ArrayList<>();
        for (Restaurant r : RestaurantService.getListRestaurant()) {
            for (int i = 0; i < r.getMenu().size(); i++) {
                menus.add(r.getMenu().get(i).getName());
            }
        }
        for (String item : order.getOrderItems()) {
            if (!(menus.contains(item))) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to check if the given order has no ordered pizza or too many that drone cannot carry.
     *
     * @return A boolean value use 'true' to present invalid.
     */
    public boolean InvalidPizzaCount() {
        return order.getOrderItems().size() > 4 || order.getOrderItems().size() == 0;
    }

    /**
     * This method is used to check if the given order has pizzas from a same participating restaurant.
     *
     * @return A boolean value use 'true' to present invalid.
     */
    public boolean InvalidPizzaCombinationMultipleSuppliers() {
        for (Restaurant r : RestaurantService.getListRestaurant()) {
            if (order.sameSingleRestaurant(r, order.getOrderItems())) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to assign the given order's outcome based on its status.
     */
    public void assignOrderOutcome() {

        if (InvalidCardNumber()) {
            order.outcome = OrderOutcome.InvalidCardNumber;
        } else if (InvalidExpiryDate()) {
            order.outcome = OrderOutcome.InvalidExpiryDate;
        } else if (InvalidCvv()) {
            order.outcome = OrderOutcome.InvalidCvv;
        } else if (InvalidTotal()) {
            order.outcome = OrderOutcome.InvalidTotal;
        } else if (InvalidPizzaNotDefined()) {
            order.outcome = OrderOutcome.InvalidPizzaNotDefined;
        } else if (InvalidPizzaCount()) {
            order.outcome = OrderOutcome.InvalidPizzaCount;
        } else if (InvalidPizzaCombinationMultipleSuppliers()) {
            order.outcome = OrderOutcome.InvalidPizzaCombinationMultipleSuppliers;
        } else order.outcome = OrderOutcome.ValidButNotDelivered;
    }

}
