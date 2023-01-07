package uk.ac.ed.inf;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import uk.ac.ed.inf.domain.CompassDirection;
import uk.ac.ed.inf.domain.LngLat;
import uk.ac.ed.inf.domain.NoFlyZone;
import uk.ac.ed.inf.service.CentralAreaService;
import uk.ac.ed.inf.service.NoFlyZoneService;
import uk.ac.ed.inf.service.OrderService;
import uk.ac.ed.inf.service.RestaurantService;

import java.util.Arrays;

import static org.junit.Assert.assertNotEquals;

public class LngLatTest extends TestCase {

    private LngLat p1;
    private LngLat p2;
    private LngLat p3;
    private LngLat p4;
    private LngLat[] noFlyZoneList;

    public LngLatTest(String testName){
        super(testName);
    }

    @Override
    protected void setUp() throws Exception{
        OrderService.init("https://ilp-rest.azurewebsites.net","2023-01-01");
        RestaurantService.init("https://ilp-rest.azurewebsites.net");
        NoFlyZoneService.init("https://ilp-rest.azurewebsites.net");
        CentralAreaService.init("https://ilp-rest.azurewebsites.net");

        p1 = new LngLat(-3.18,55.93); //random point not inside/near any zones
        p2 = new LngLat(-3.19,55.95);
        p3 = new LngLat(-3.184319, 55.946233); //corner coordinate of central area
        p4 = new LngLat(-3.190,55.9438); //inside no-fly zone

    }

    public void testInArea() {
        assertTrue(p1.inArea(p4,CentralAreaService.getCentralAreaPos()));
        assertFalse(p1.inArea(p1,CentralAreaService.getCentralAreaPos()));

        int count = 0;
        for (NoFlyZone zone : NoFlyZoneService.getZoneList()){
            assertFalse(p1.inArea(p1,zone.noFlyZoneConverter()));
            if (p1.inArea(p4, zone.noFlyZoneConverter())){
                count += 1;
            }
            assertEquals(1,count);
        }
    }

    public void testDistanceTo(){
        double dis = Math.sqrt((p1.getLng() - p4.getLng())*(p1.getLng() - p4.getLng())
                + (p1.getLat() - p4.getLat())*(p1.getLat() - p4.getLat()));

        assertEquals(dis,p4.distanceTo(p1));
    }
    public void testCloseTo(){
        p1 = new LngLat(-3.19,55.95014);

        assertTrue(p1.closeTo(p2));
        assertFalse(p1.closeTo(p4));
    }

    public void testNextPosition(){
        p1 = new LngLat(-3.18,55.93);
        p2 = new LngLat(-3.18,55.93015);

        assertEquals(p2.getLat(), p1.nextPosition(CompassDirection.N).getLat());
        assertNotEquals(p1.nextPosition(CompassDirection.S).getLat(), p2.getLat());
    }

    public void testAvailableNeighbours(){
        int count = 0;
        p2 = new LngLat(-3.190578818321228,55.9439);
        count = p2.availableNeighbours().size();

        assertNotEquals(count,16);
        assertEquals(p1.availableNeighbours().size(),16);

    }

}

