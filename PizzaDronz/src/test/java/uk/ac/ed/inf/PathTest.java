package uk.ac.ed.inf;

import junit.framework.TestCase;
import uk.ac.ed.inf.domain.LngLat;
import uk.ac.ed.inf.domain.Path;
import uk.ac.ed.inf.service.CentralAreaService;
import uk.ac.ed.inf.service.NoFlyZoneService;

public class PathTest extends TestCase {

    protected LngLat destination;

    @Override
    protected void setUp(){
        NoFlyZoneService.init("https://ilp-rest.azurewebsites.net");
        CentralAreaService.init("https://ilp-rest.azurewebsites.net");
    }

    public void testPath(){
        destination = new LngLat(-3,55);
        Path path = new Path(destination);
        path.pathSearching();
        path.pathInverse();

        assertNotNull(path.getPathStartToEnd());
        assertNotNull(path.getPathEndToStart());
    }


}