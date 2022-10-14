package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * LngLat is a base class for all coordinates contexts
 * which allows the given position;s coordinate to be shown.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class LngLat {

    /**
     * The field lng and lat are used to present the longitudes and latitudes of a given coordinate.
     */

    public double lng;
    public double lat;

    /**
     * Create coordinate for the given position.
     *
     * @param lng the longitude of coordinate
     * @param lat the latitude of coordinate
     */
    public LngLat (@JsonProperty("longitude")double lng,
                   @JsonProperty("latitude")double lat){
        this.lng = lng;
        this.lat = lat;
    }


    /**
     * This method has no parameter,
     * and it is used to check if the given coordinate's location are inside the central area.
     *
     * @return A boolean value that uses 'true' to present the input coordinate are inside the central are, vice versa.
     */
    public boolean inCentralArea(){

        LngLat[] centralAreaPos = CentralArea.centralAreaPos;
        boolean threeEdges = true;
        boolean lastEdge = true;

        LngLat point0 = centralAreaPos[0];
        LngLat point3 = centralAreaPos[3];

        if ((point0.lng - point3.lng) * (this.lat - point3.lat)
                - (this.lng - point3.lng) * (point0.lat - point3.lat) < 0){
            lastEdge = false;
        }

        for(int i=0;i < centralAreaPos.length-1;i++){

            LngLat point1 = centralAreaPos[i];
            LngLat point2 = centralAreaPos[i+1];

            double distance = (point2.lng - point1.lng) * (this.lat - point1.lat)
                    - (this.lng - point1.lng) * (point2.lat - point1.lat);

            if (distance < 0) {
                threeEdges = false;
            }
        }

        return threeEdges && lastEdge;
    }


    /**
     * This method is used to get the pythagorean distance between current position and its target.
     *
     * @param pos Target position with LngLat format.
     * @return A double value of pythagorean distance between two points.
     */

    public double distanceTo (LngLat pos){
        if (pos == null){
            System.err.println("Invalid input position.");
        }
        double lng1 = pos.lng-this.lng;
        double lat1 = pos.lat-this.lat;
        double PythagoreanDis = Math.sqrt(lng1*lng1 + lat1*lat1);

        return PythagoreanDis;
    }

    /**
     * This method is used to check whether the input position and this current position is close or not.
     *
     * @param pos Target position with LngLat format.
     * @return A boolean value uses 'true' to indicate the points are close to each other.
     */

    public boolean closeTo (LngLat pos){
        if (pos == null){
            System.err.println("Invalid input position.");
        }
        else if (distanceTo(pos) < 0){
            System.err.println("The distance cannot smaller than 0");
        }
        else if (distanceTo(pos) < 0.00015){
            return true;
        }
        return false;
    }

    /**
     * This method is used to get this new position of the drone if it makes a move.
     *
     * @param direction A compass direction that has angle value by using enum format.
     * @return New coordinate of the drone in LngLat format.
     */

    public LngLat nextPosition(CompassDirection direction) {
        LngLat newPos;

        if (direction == null){
            return this;
        }
        else {
            double lng = this.lng + 0.00015 * Math.cos(Math.toRadians(direction.getVal()));
            double lat = this.lat + 0.00015 * Math.sin(Math.toRadians(direction.getVal()));

            newPos = new LngLat(lng, lat);
        }
        return newPos;
    }

}
