package uk.ac.ed.inf;

import junit.framework.TestCase;
import uk.ac.ed.inf.domain.LngLat;
import uk.ac.ed.inf.domain.Path;
import uk.ac.ed.inf.service.CentralAreaService;
import uk.ac.ed.inf.service.NoFlyZoneService;

/**
 * A simple test checks the path has been created but will use
 * <a href="https://geojson.io/#map=16.37/55.943805/-3.18955">...</a>
 * to visualise the actual path for efficiency purpose.
 */
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