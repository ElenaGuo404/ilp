package uk.ac.ed.inf;

import junit.framework.TestCase;
import uk.ac.ed.inf.domain.Order;
import uk.ac.ed.inf.domain.OrderOutcome;
import uk.ac.ed.inf.domain.OrderOutcomeCheck;
import uk.ac.ed.inf.service.CentralAreaService;
import uk.ac.ed.inf.service.NoFlyZoneService;
import uk.ac.ed.inf.service.OrderService;
import uk.ac.ed.inf.service.RestaurantService;

/**
 * Unit and Integration testing checks individual case and the whole order outcome assigning module
 */
public class OrderOutcomeCheckTest extends TestCase {

    public OrderOutcomeCheckTest(String testName){
        super(testName);
    }

    protected Order order1;
    protected Order[] orders;
    protected OrderOutcomeCheck check;


    @Override
    protected void setUp(){
        OrderService.init("https://ilp-rest.azurewebsites.net","2023-01-01");
        RestaurantService.init("https://ilp-rest.azurewebsites.net");
        NoFlyZoneService.init("https://ilp-rest.azurewebsites.net");
        CentralAreaService.init("https://ilp-rest.azurewebsites.net");
    }

    //unit level tests to ensure failure cases can be well found by system.
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

    // two integration tests to ensure the order outcome assign module well perform.
    public void testAssignOrderOutcomeTrueCase(){
        orders = OrderService.getListOrders();
        int count = 0;
        for (Order order : orders){
            check = new OrderOutcomeCheck(order);
            check.assignOrderOutcome();
            if (order.outcome == OrderOutcome.ValidButNotDelivered){
                count += 1;
            }
        }
        order1 = OrderService.getListOrders()[7];
        assertEquals(47,orders.length);
        assertEquals(40, count);
        assertEquals(OrderOutcome.ValidButNotDelivered,order1.outcome);
    }

    public void testAssignOrderOutcomeFalseCase(){
        Order order1 = null;
        try {
            check = new OrderOutcomeCheck(order1);
            check.assignOrderOutcome();
        }catch (NullPointerException e){
            assertNotNull(e);
        }
    }

}