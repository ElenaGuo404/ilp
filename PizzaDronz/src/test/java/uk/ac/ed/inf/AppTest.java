package uk.ac.ed.inf;

import static org.junit.Assert.assertNotEquals;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static uk.ac.ed.inf.CentralArea.getINSTANCE;
import static uk.ac.ed.inf.CentralArea.centralAreaPos;

/**
 * Unit test for simple App.
 */

public class AppTest extends TestCase
{
    /**
     * Rigorous Test :-)
     */

    protected LngLat position1;
    protected LngLat position2;

    protected Menu item1;
    protected Menu item2;

    protected Restaurant participant;
    protected Restaurant[] participants;


    public AppTest(String testName){
        super(testName);
    }

//    public static Test suite(){
//        TestSuite suite = new TestSuite();
//        suite.addTest(new AppTest("testCentralAreaCoordinates"));
//
//        return suite();
//    }

    @Override
    protected void setUp() throws Exception{
        getINSTANCE();
        position1 = new LngLat(-3.18,55.93);
        position2 = new LngLat(-3.19,55.95);

        item1 = new Menu("Meat Lover", 1400);
        item2 = new Menu("Vegan Delight", 1100);

        participant = new Restaurant("Sora Lella Vegan Restaurant",new Menu[]{item1,item2});
        participants = Restaurant.getRestaurantsFromRestServer(
                new URL("https://ilp-rest.azurewebsites.net/restaurants"));
    }


    public void testCentralAreaCoordinates(){
        position1 = new LngLat(-3.192473, 55.946233);
        position2 = new LngLat(-3.192473, 55.942617);
        LngLat position3 = new LngLat(-3.184319, 55.942617);
        LngLat position4 = new LngLat(-3.184319, 55.946233);

        assertEquals(position1.lng,centralAreaPos[0].lng);
        assertEquals(position1.lat,centralAreaPos[0].lat);

        assertEquals(position2.lng,centralAreaPos[1].lng);
        assertEquals(position2.lat,centralAreaPos[1].lat);

        assertEquals(position3.lng,centralAreaPos[2].lng);
        assertEquals(position3.lat,centralAreaPos[2].lat);

        assertEquals(position4.lng,centralAreaPos[3].lng);
        assertEquals(position4.lat,centralAreaPos[3].lat);
    }

    public void testInCentralAreaTrueCase(){
        position1 = new LngLat(-3.184319, 55.946233);
        assertTrue(position1.inCentralArea());
    }

    public void testInCentralAreaFalseCase(){

        LngLat random1 = new LngLat(-3.193,55.945);
        LngLat random2 = new LngLat(-3.19, 55.93);

        assertFalse(position1.inCentralArea());
        assertFalse(position2.inCentralArea());
        assertFalse(random1.inCentralArea());
        assertFalse(random2.inCentralArea());
    }

    public void testDistanceTo(){

        double dis = Math.sqrt((position1.lng - position2.lng)*(position1.lng - position2.lng)
                + (position1.lat - position2.lat)*(position1.lat - position2.lat));

        assertEquals(dis,position2.distanceTo(position1));
    }

    public void testCloseToTrueCase(){
        position1 = new LngLat(-3.19,55.95014);

        assertTrue(position1.closeTo(position2));
    }

    public void testCloseToFalseCase(){
        position1 = new LngLat(-3.19,55.94);

        assertFalse(position1.closeTo(position2));
    }

    public void testNextPositionTrueCase(){
        position1 = new LngLat(-3.18,55.93);
        position2 = new LngLat(-3.18,55.93015);

        assertEquals(position2.lat,position1.nextPosition(CompassDirection.N).lat);
    }

    public void testNextPositionFalseCase(){
        position1 = new LngLat(-3.18,55.93);
        position2 = new LngLat(-3.18,55.94);

        assertNotEquals(position1.nextPosition(CompassDirection.N).lat,position2.lat);
    }

    public void testGetRestaurantFromRestSever(){
        assertEquals(participants[1].name,participant.name);
        assertEquals(participants[1].menu[0].name, participant.menu[0].name);
    }


    public void testGetDeliveryCostTrueCase(){
        Order order = new Order();
        List<String> pizzas = new ArrayList<>();

        pizzas.add("Meat Lover");
        pizzas.add("Meat Lover");
        pizzas.add("Vegan Delight");
        pizzas.add("Vegan Delight");

        assertEquals(5100, order.getDeliveryCost(participants,pizzas));
    }

    public void testInvalidPizzaCount(){
        Order order = new Order();
        List<String> pizzas = new ArrayList<>();
        List<String> noOrder = new ArrayList<>();

        pizzas.add("Meat Lover");
        pizzas.add("Meat Lover");
        pizzas.add("Vegan Delight");
        pizzas.add("Vegan Delight");
        pizzas.add("Vegan Delight");

        try {
            order.getDeliveryCost(participants,noOrder);
        }catch (Exception e){
            assertEquals("You don't have pizza ordered",e.getMessage());
        }

        try{
            order.getDeliveryCost(participants,pizzas);
        }catch (Exception e){
            assertEquals("The drone cannot carry more than FOUR pizzas", e.getMessage());
        }
    }

    public void testInvalidPizzaCombination(){
        Order order = new Order();
        List<String> pizzas = new ArrayList<>();

        pizzas.add("Meat Lover");
        pizzas.add("Margarita");

        try {
            order.getDeliveryCost(participants,pizzas);
        }catch (Exception e){
            assertEquals("This pizza combination cannot be delivered from same restaurant",e.getMessage());
        }
    }


}
