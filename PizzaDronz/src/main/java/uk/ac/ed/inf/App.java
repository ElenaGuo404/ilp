package uk.ac.ed.inf;

import uk.ac.ed.inf.controller.DeliveriesController;
import uk.ac.ed.inf.controller.DroneController;
import uk.ac.ed.inf.controller.FlightPathController;
import uk.ac.ed.inf.domain.ArgumentCheck;
import uk.ac.ed.inf.service.CentralAreaService;
import uk.ac.ed.inf.service.NoFlyZoneService;
import uk.ac.ed.inf.service.OrderService;
import uk.ac.ed.inf.service.RestaurantService;

/**
 * App class used to run main command of this system.
 */
public class App {

    public static void main( String[] args ) {

        if (ArgumentCheck.validTime(args[0]) && ArgumentCheck.validUrl(args[1])) {


            OrderService.init(args[1], args[0]);
            RestaurantService.init(args[1]);
            NoFlyZoneService.init(args[1]);
            CentralAreaService.init(args[1]);

            FlightPathController flightPathController = new FlightPathController();
            flightPathController.getDeliveredPath();
            flightPathController.fileGenerator(args[0]);

            DeliveriesController.deliveriesConverter();
            DeliveriesController.fileGenerator(args[0]);

            DroneController.getDailyMove();
            DroneController.fileGenerator(args[0]);
        }
    }
}
