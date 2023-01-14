package uk.ac.ed.inf;

import junit.framework.TestCase;
import uk.ac.ed.inf.domain.CompassDirection;
import uk.ac.ed.inf.domain.LngLat;
import uk.ac.ed.inf.domain.NoFlyZone;
import uk.ac.ed.inf.service.CentralAreaService;
import uk.ac.ed.inf.service.NoFlyZoneService;
import uk.ac.ed.inf.service.OrderService;
import uk.ac.ed.inf.service.RestaurantService;

import static org.junit.Assert.assertNotEquals;

public class LngLatTest extends TestCase {

    private LngLat p1;
    private LngLat p2;
    private LngLat p3;

    public LngLatTest(String testName){
        super(testName);
    }

    @Override
    protected void setUp(){
        OrderService.init("https://ilp-rest.azurewebsites.net","2023-01-01");
        RestaurantService.init("https://ilp-rest.azurewebsites.net");
        NoFlyZoneService.init("https://ilp-rest.azurewebsites.net");
        CentralAreaService.init("https://ilp-rest.azurewebsites.net");

        p1 = new LngLat(-3.18,55.93); //random point not inside/near any zones
        p2 = new LngLat(-3.19,55.95);
        p3 = new LngLat(-3.190,55.9438); //inside no-fly zone

    }

    public void testInArea() {
        assertTrue(p1.inArea(p3,CentralAreaService.getCentralAreaPos()));
        assertFalse(p1.inArea(p1,CentralAreaService.getCentralAreaPos()));

        int count = 0;
        for (NoFlyZone zone : NoFlyZoneService.getZoneList()){
            assertFalse(p1.inArea(p1,zone.noFlyZoneConverter()));
            if (p1.inArea(p3, zone.noFlyZoneConverter())){
                count += 1;
            }
            assertEquals(1,count);
        }
    }

    public void testDistanceTo(){
        double dis = Math.sqrt((p1.getLng() - p3.getLng())*(p1.getLng() - p3.getLng())
                + (p1.getLat() - p3.getLat())*(p1.getLat() - p3.getLat()));

        assertEquals(dis, p3.distanceTo(p1));
    }

    public void testCloseTo(){
        p1 = new LngLat(-3.19,55.95014);

        assertTrue(p1.closeTo(p2));
        assertFalse(p1.closeTo(p3));
    }

    public void testNextPosition(){
        p1 = new LngLat(-3.18,55.93);
        p2 = new LngLat(-3.18,55.93015);

        assertEquals(p2.getLat(), p1.nextPosition(CompassDirection.N).getLat());
        assertNotEquals(p2.getLat(),p1.nextPosition(CompassDirection.S).getLat());
    }

    public void testAvailableNeighbours(){
        int count;
        p2 = new LngLat(-3.190578818321228,55.9439);
        count = p2.availableNeighbours().size();

        assertNotEquals(16,count);
        assertEquals(16,p1.availableNeighbours().size());

    }

}

