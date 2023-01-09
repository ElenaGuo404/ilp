package uk.ac.ed.inf;

import junit.framework.TestCase;
import uk.ac.ed.inf.service.CentralAreaService;
import uk.ac.ed.inf.service.NoFlyZoneService;
import uk.ac.ed.inf.service.OrderService;
import uk.ac.ed.inf.service.RestaurantService;
import uk.ac.ed.inf.domain.Order;
import uk.ac.ed.inf.domain.Restaurant;


import java.util.Collections;
import java.util.List;

public class OrderTest extends TestCase {

    public OrderTest(String testName){
        super(testName);
    }

    protected Order order1;
    protected Order order2;
    protected Restaurant restaurant1;
    protected List<String> listPizzas1;
    protected List<String> listPizzas2;

    @Override
    protected void setUp() throws Exception{
        OrderService.init("https://ilp-rest.azurewebsites.net","2023-01-01");
        RestaurantService.init("https://ilp-rest.azurewebsites.net");
        NoFlyZoneService.init("https://ilp-rest.azurewebsites.net");
        CentralAreaService.init("https://ilp-rest.azurewebsites.net");
        order1 = OrderService.getListOrders()[9];
        order2 = OrderService.getListOrders()[10];
        restaurant1 = RestaurantService.getListRestaurant()[2];
        listPizzas1 = order1.getOrderItems();
        listPizzas2 = order2.getOrderItems();
    }

    public void testSameSingleRestaurant() {
        assertTrue(order1.sameSingleRestaurant(restaurant1,listPizzas1));
        assertFalse(order2.sameSingleRestaurant(restaurant1,listPizzas2));
    }

    public void testGetPizzaPrice() {
        assertEquals(order1.getPriceTotalInPence() - 100,order1.getPizzaPrice(restaurant1,listPizzas1));
    }

    public void testGetDeliveryCost() {
        assertEquals(order1.getPriceTotalInPence(),order1.getDeliveryCost());
    }

    public void testGetOrderRestaurant() {
        assertEquals(restaurant1,order1.getOrderRestaurant());
        assertNotSame(restaurant1,order2.getOrderRestaurant());
    }
}