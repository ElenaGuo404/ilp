package uk.ac.ed.inf;

import junit.framework.TestCase;

public class LngLatTest extends TestCase {

    public void testInCentralArea() {
        LngLat kfc = new LngLat(-3.184319, 55.946233);
        LngLat newpos = kfc.nextPosition(LngLat.CompassDirection.N);
//        LngLat hill = new LngLat(-3.192473, 55.946233);
//        LngLat Meadows = new LngLat(-3.192473, 55.942617);
//        LngLat busStop = new LngLat(-3.184319, 55.942617);
//        LngLat random1 = new LngLat(-3.19,55.95);
//        LngLat random2 = new LngLat(-3.193,55.945);
//        LngLat random3 = new LngLat(-3.19, 55.93);
//        LngLat random4 = new LngLat(-3.18,55.93);

        CentralArea.getINSTANCE();
//        System.out.println(kfc.inCentralArea());
//        System.out.println(hill.inCentralArea());
//        System.out.println(Meadows.inCentralArea());
//        System.out.println(busStop.inCentralArea());
//        System.out.println(random1.inCentralArea());
//        System.out.println(random2.inCentralArea());
//        System.out.println(random3.inCentralArea());
//        System.out.println(random4.inCentralArea());

//        System.out.println(newpos.inCentralArea());

    }
}