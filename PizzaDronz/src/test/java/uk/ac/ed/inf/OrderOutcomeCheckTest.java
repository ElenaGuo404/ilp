package uk.ac.ed.inf;

import junit.framework.TestCase;
import uk.ac.ed.inf.domain.Order;
import uk.ac.ed.inf.domain.OrderOutcome;
import uk.ac.ed.inf.domain.OrderOutcomeCheck;
import uk.ac.ed.inf.domain.Restaurant;
import uk.ac.ed.inf.service.CentralAreaService;
import uk.ac.ed.inf.service.NoFlyZoneService;
import uk.ac.ed.inf.service.OrderService;
import uk.ac.ed.inf.service.RestaurantService;

import java.util.List;

public class OrderOutcomeCheckTest extends TestCase {

    public OrderOutcomeCheckTest(String testName){
        super(testName);
    }

    protected Order order1;
    protected OrderOutcomeCheck check;


    @Override
    protected void setUp() throws Exception{
        OrderService.init("https://ilp-rest.azurewebsites.net","2023-01-01");
        RestaurantService.init("https://ilp-rest.azurewebsites.net");
        NoFlyZoneService.init("https://ilp-rest.azurewebsites.net");
        CentralAreaService.init("https://ilp-rest.azurewebsites.net");
    }

    public void testInvalidCardNumber() {
        order1 = OrderService.getListOrders()[0];
        check = new OrderOutcomeCheck(order1);
        assertTrue(check.InvalidCardNumber());
    }

    public void testInvalidExpiryDate() {
        order1 = OrderService.getListOrders()[1];
        check = new OrderOutcomeCheck(order1);
        assertTrue(check.InvalidExpiryDate());
    }

    public void testInvalidCvv() {
        order1 = OrderService.getListOrders()[2];
        check = new OrderOutcomeCheck(order1);
        assertTrue(check.InvalidCvv());
    }

    public void testInvalidTotal() {
        order1 = OrderService.getListOrders()[3];
        check = new OrderOutcomeCheck(order1);
        assertTrue(check.InvalidTotal());
    }

    public void testInvalidPizzaNotDefined() {
        order1 = OrderService.getListOrders()[4];
        check = new OrderOutcomeCheck(order1);
        assertTrue(check.InvalidPizzaNotDefined());
    }

    public void testInvalidPizzaCount() {
        order1 = OrderService.getListOrders()[5];
        check = new OrderOutcomeCheck(order1);
        assertTrue(check.InvalidPizzaCount());
    }

    public void testInvalidPizzaCombinationMultipleSuppliers() {
        order1 = OrderService.getListOrders()[6];
        check = new OrderOutcomeCheck(order1);
        assertTrue(check.InvalidPizzaCombinationMultipleSuppliers());
    }

    public void testAssignOrderOutcome(){
        order1 = OrderService.getListOrders()[7];
        check = new OrderOutcomeCheck(order1);
        check.assignOrderOutcome();
        assertEquals(OrderOutcome.ValidButNotDelivered,order1.outcome);
    }
}