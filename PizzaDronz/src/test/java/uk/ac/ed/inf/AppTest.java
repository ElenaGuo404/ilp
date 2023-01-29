package uk.ac.ed.inf;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import uk.ac.ed.inf.domain.*;
import uk.ac.ed.inf.controller.*;
import uk.ac.ed.inf.service.*;
import java.io.File;
import java.io.IOException;



/**
 * System Test checks for the generated 3 output files has its expected information
 * and checks for the runtime of the system.
 */

public class AppTest extends TestCase
{

    protected String url;
    protected String date;
    private static final long maxRunTime = 60000;


    public AppTest(String testName){
        super(testName);
    }

    @Override
    protected void setUp(){

        url = "https://ilp-rest.azurewebsites.net";
        date = "2023-01-01";

        OrderService.init(url,date);
        RestaurantService.init(url);
        NoFlyZoneService.init(url);
        CentralAreaService.init(url);

        FlightPathController flightPathController = new FlightPathController();
        flightPathController.getDeliveredPath();
        flightPathController.fileGenerator(date);

    }


    //checks the output flightpath json file has expected information
    public void testFlightPathFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("/Users/jiayingguo/Desktop/st/flightpath-2023-01-01.json"));

        int count = 0;
        for (int i = 0; i < root.size(); i++){
            for (FlightPath path : FlightPathController.getDeliveredOrderPath()){
                if (root.get(i).toString().contains(String.valueOf(path.getFromLongitude()))
                        && root.get(i).toString().contains(String.valueOf(path.getFromLatitude()))){
                    count++;
                    break;
                }
            }
        }
        assertEquals(FlightPathController.getDeliveredOrderPath().size(),count);
    }

    //checks the output deliveries json file has expected information
    public void testDeliveriesFile() throws IOException {
        DeliveriesController.deliveriesConverter();
        DeliveriesController.fileGenerator(date);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("/Users/jiayingguo/Desktop/st/deliveries-2023-01-01.json"));

        int count = 0;

        for (Deliveries delivery : DeliveriesController.getDeliveries()){
            for (int i = 0; i < root.size(); i++) {
                if (root.get(i).toString().contains(delivery.orderNo)) {
                    count++;
                }
            }
        }
        assertEquals(DeliveriesController.getDeliveries().size(),count);
    }

    //checks the output drone geojson file has expected information
    public void testDroneFile() throws IOException {
        DroneController.getDailyMove();
        DroneController.fileGenerator(date);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("/Users/jiayingguo/Desktop/st/drone-2023-01-01.geojson"));

        assertEquals(mapper.readTree(DroneController.getFeatureCollection().toJson()), root);
    }

    public void testRuntime(){

        long startTime = System.currentTimeMillis();
        App.main(new String[]{date, url});
        long endTime = System.currentTimeMillis();
        long runTime = endTime - startTime;

        System.out.println(runTime);
        assertTrue((maxRunTime - runTime) >= 0);
    }
}
