package uk.ac.ed.inf.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ed.inf.service.NoFlyZoneService;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * LngLat is a base class for all coordinates contexts
 * which allows the given position's coordinate to be shown.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class LngLat {

    /**
     * The fields lng and lat are used to present the longitudes and latitudes of a given coordinate;
     * moveDistance stores value for each move.
     */
    private final double lng;
    private final double lat;
    private final double moveDistance = 0.00015;

    public LngLat (@JsonProperty("longitude")double lng,
                   @JsonProperty("latitude")double lat){
        this.lng = lng;
        this.lat = lat;
    }

    /**
     * The method is used to test if the line of current position
     * and next position has intersection with given area.
     *
     * @param nextCoordinates next position in LngLat format
     * @param area list of corner coordinates of an area in LngLat
     * @return A boolean value of true for there is an intersection.
     */
    public boolean inArea(LngLat nextCoordinates, LngLat[] area){

        boolean inside = false;
        Line2D segment1 = new Line2D.Double(this.lng,this.lat,nextCoordinates.lng,nextCoordinates.lat);

        for (int i = 0; i <area.length-1; i++){
            Line2D segment2 = new Line2D.Double(area[i].lng,area[i].lat,area[i+1].lng,area[i+1].lat);
            if (segment1.intersectsLine(segment2)){
                inside = true;
            }
        }
        Line2D lastSeg = new Line2D.Double(area[0].lng,area[0].lat,area[area.length-1].lng,area[area.length-1].lat);
        if (segment1.intersectsLine(lastSeg)){
            inside = true;
        }
        return inside;
    }

    /**
     * This method is used to get the pythagorean distance between current position and its target.
     *
     * @param pos Target position with LngLat format.
     * @return A double value of pythagorean distance between two points.
     */
    public double distanceTo (LngLat pos){

        double PythagoreanDis = 0;

        if (pos == null){
            System.err.println("Invalid input position.");
        }
        else {
            double lng1 = pos.lng - this.lng;
            double lat1 = pos.lat - this.lat;
            PythagoreanDis = Math.sqrt(lng1 * lng1 + lat1 * lat1);
        }
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
        else return distanceTo(pos) < moveDistance;

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
            double lng = this.lng + moveDistance * Math.cos(Math.toRadians(direction.getVal()));
            double lat = this.lat + moveDistance * Math.sin(Math.toRadians(direction.getVal()));

            newPos = new LngLat(lng, lat);
        }
        return newPos;
    }

    /**
     * This class is usd to get valid neighbours of given position
     * by delete all the neighbours that are inside no-fly zones.
     *
     * @return a list of direction that are valid for move.
     */
    public ArrayList<CompassDirection> availableNeighbours() {

        CompassDirection[] directions = CompassDirection.values();
        ArrayList<CompassDirection> availableNeighbours = new ArrayList<>(Arrays.asList(directions));

        for (CompassDirection d : directions) {
            for (NoFlyZone zone : NoFlyZoneService.getZoneList()) {

                if ((inArea(nextPosition(d), zone.noFlyZoneConverter()))) {
                    availableNeighbours.remove(d);
                }
            }
        }
        return availableNeighbours;
    }

    /**
     * A getter method used to get longitude of a given position.
     *
     * @return longitude of a coordinates.
     */
    public double getLng() {
        return lng;
    }

    /**
     * A getter method used to get latitude of a given position.
     *
     * @return latitude of a coordinates.
     */
    public double getLat() {
        return lat;
    }

}

