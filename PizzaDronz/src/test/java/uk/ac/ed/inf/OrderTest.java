package uk.ac.ed.inf;

import junit.framework.TestCase;
import uk.ac.ed.inf.service.CentralAreaService;
import uk.ac.ed.inf.service.NoFlyZoneService;
import uk.ac.ed.inf.service.OrderService;
import uk.ac.ed.inf.service.RestaurantService;

public class OrderTest extends TestCase {

    public OrderTest(String testName){
        super(testName);
    }

    @Override
    protected void setUp() throws Exception{
        OrderService.init("https://ilp-rest.azurewebsites.net","2023-01-01");
        RestaurantService.init("https://ilp-rest.azurewebsites.net");
        NoFlyZoneService.init("https://ilp-rest.azurewebsites.net");
        CentralAreaService.init("https://ilp-rest.azurewebsites.net");
    }

    public void testSameSingleRestaurant() {
    }

    public void testGetPizzaPrice() {
    }

    public void testGetDeliveryCost() {
    }

    public void testGetOrderRestaurant() {
    }

    public void testGetOrderPath() {
    }

    public void testGetPathNumber() {
    }
}