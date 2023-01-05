package uk.ac.ed.inf.controller;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import uk.ac.ed.inf.domain.FlightPath;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class DroneController is used to get data of daily drone movement path
 * and use it to generate json files.
 */
public class DroneController {

    /**
     * The private field points stores a list of point objects that from each flightpath object;
     * and private field featureCollection is used to store the collection of feature from point.
     */
    private static final List<Point> points = new ArrayList<>();
    private static FeatureCollection featureCollection;

    /**
     * This method is used to covert flightpath to a collection of points that used for serialization.
     */
    public static void pathsNodes(){

        for (FlightPath flightPath : FlightPathController.getDeliveredOrderPath()){
            points.add(Point.fromLngLat(flightPath.getFromLongitude(),flightPath.getFromLatitude()));
        }

    }


    /**
     * This method is used to covert points into a linestring and put it in collection of features.
     */
    public static void getDailyMove() {

        pathsNodes();
        Feature feature = Feature.fromGeometry(LineString.fromLngLats(points));
        featureCollection = FeatureCollection.fromFeatures(Collections.singletonList(feature));
    }

    /**
     * This method takes feature collection and use it to produce geojson files of how drone moved for the given date.
     *
     * @param date input String used to name the files
     */
    public static void fileGenerator(String date) {

        try {
            FileWriter writer = new FileWriter("../drone-" + date + ".geojson");
            writer.write(featureCollection.toJson());
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
