package uk.ac.ed.inf.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * NoFlyZone class is used to present a single area that the drone are not allowed to fly inside it.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoFlyZone {

    /**
     * The field shows a list of corner coordinates from a given no-fly zone.
     */
    private final ArrayList<Double[]> coordinates;

    public NoFlyZone (@JsonProperty("coordinates") ArrayList<Double[]> coordinates){
        this.coordinates = coordinates;

}

    /**
     * This method is used to convert the no-fly zone coordinates to LngLat format for easier use.
     *
     * @return a list of corner coordinates of LngLat format
     */
    public LngLat[] noFlyZoneConverter(){
        LngLat[] cornerCoordinates = new LngLat[coordinates.size() - 1];

        for (int i = 0; i < coordinates.size() - 1; i++){

            cornerCoordinates[i] = new LngLat(coordinates.get(i)[0],coordinates.get(i)[1]);
        }return cornerCoordinates;
    }

}
