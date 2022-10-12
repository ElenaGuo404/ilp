//package uk.ac.ed.inf;
//
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;
//
///**
// * Unit test for simple App.
// */
//public class AppTest
//{
//    /**
//     * Rigorous Test :-)
//     */
//    @Test
//    public void shouldAnswerWithTrue()
//    {
//        assertTrue( true );
//    }
//}

package uk.ac.ed.inf;

//import Exceptions.InvalidPizzaCombinationException;
//import Exceptions.InvalidPizzaCount;
//import Exceptions.PizzaUndefined;
//import Exceptions.UnexpectedNullInput;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Random;


/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigorous Test :-)
     */
//    public void testApp() {
//        var a = new LngLat(3, 5);
//
//
//    }
//
//    public void testIsCentralAreaTrueCase() {
//        for (int i = 0; i < 100; i++) {
//            var rndLng = -3.184319 + (-3.192473 + 3.184319) * Math.random();
//            var rndLat = 55.942617 + (55.946233 - 55.942617) * Math.random();
//            var lngLat = new LngLat(rndLng, rndLat);
//            assertTrue(lngLat.inCentralArea());
//        }
//
//    }

//    public void testIsCentralAreaBoundaryCase() {
//        assertTrue(new LngLat(-3.192473, 55.946233).CentralArea());
//        assertTrue(new LngLat(-3.192473, 55.942617).CentralArea());
//        assertTrue(new LngLat(-3.184319, 55.942617).CentralArea());
//        assertTrue(new LngLat(-3.184319, 55.946233).CentralArea());
//
//        assertTrue(new LngLat(-3.190000, 55.946233).CentralArea());
//        assertTrue(new LngLat(-3.192473, 55.944000).CentralArea());
//        assertTrue(new LngLat(-3.188000, 55.942617).CentralArea());
//        assertTrue(new LngLat(-3.184319, 55.944000).CentralArea());
//    }

//    public void testIsCentralAreaFalseCase() {
//        for (int i = 0; i < 50; i++) {
//            var rndLng = -3.192473 + (-3.192473 + 3.184319) * Math.random();
//            var rndLat = 55.946233 + (55.946233 - 55.942617) * Math.random();
//            var lngLat = new LngLat(rndLng, rndLat);
//            assertFalse(lngLat.isCentralArea());
//        }
//
//        for (int i = 0; i < 50; i++) {
//            var rndLng = -3.184319 - (-3.192473 + 3.184319) * Math.random();
//            var rndLat = 55.942617 - (55.946233 - 55.942617) * Math.random();
//            var lngLat = new LngLat(rndLng, rndLat);
//            assertFalse(lngLat.isCentralArea());
//        }
//    }
}