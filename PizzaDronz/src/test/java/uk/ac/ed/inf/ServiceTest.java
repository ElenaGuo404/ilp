package uk.ac.ed.inf;
import junit.framework.TestCase;
import uk.ac.ed.inf.service.CentralAreaService;
import uk.ac.ed.inf.service.NoFlyZoneService;
import uk.ac.ed.inf.service.OrderService;
import uk.ac.ed.inf.service.RestaurantService;

import java.net.UnknownHostException;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Integration Test for data reading module
 */
public class ServiceTest extends TestCase {

    protected String url;
    protected String wrongUrl;

    public ServiceTest(String testName){
        super(testName);
    }

    @Override
    protected void setUp(){
        url = "https://ilp-rest.azurewebsites.net";
        wrongUrl = "https://ilp-rest.net";
    }

    public void testCentralAreaService() {
        CentralAreaService.init(url);
        String data = Arrays.toString(CentralAreaService.getCentralAreaPos());
        assertNotNull(data);
    }

    public void testNoFlyZoneService() {
        NoFlyZoneService.init(url);
        String data = Arrays.toString(NoFlyZoneService.getZoneList());
        assertNotNull(data);
    }

    public void testOrderService() {
        OrderService.init(url, "2023-01-01");
        String data = Arrays.toString(OrderService.getListOrders());
        assertNotNull(data);
    }

    public void testRestaurantService() {
        RestaurantService.init(url);
        String data = Arrays.toString(RestaurantService.getListRestaurant());
        assertNotNull(data);
    }

    public void testRestaurantServiceDown() {
        try {
            RestaurantService.init(wrongUrl);
        } catch (UnknownError e) {
            assertNotNull(e);
        }
    }

    public void testOrderServiceDown() {
        try {
            OrderService.init(wrongUrl,"2023-01-01");
        } catch (UnknownError e) {
            assertNotNull(e);
        }
    }

    public void testNoFlyZoneServiceDown() {
        try {
            NoFlyZoneService.init(wrongUrl);
        } catch (UnknownError e) {
            assertNotNull(e);
        }
    }

    public void testCentralAreaServiceDown() {
        try {
            CentralAreaService.init(wrongUrl);
        } catch (UnknownError e) {
            assertNotNull(e);
        }
    }
}
