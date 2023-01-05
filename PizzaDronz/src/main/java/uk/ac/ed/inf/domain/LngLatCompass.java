package uk.ac.ed.inf.domain;

/**
 * LngLatCompass class is used to create pair objects that has a given coordinate and its direction for next move.
 */
public class LngLatCompass {

    /**
     * The private fields presents a coordinates and its corresponding direction.
     */
    private final LngLat coordinates;
    private final CompassDirection direction;

    public LngLatCompass(LngLat coordinates, CompassDirection direction) {
        this.coordinates = coordinates;
        this.direction = direction;
    }

    /**
     * A getter method used to get coordinates of a given LngLatCompass object.
     *
     * @return a LngLat object
     */
    public LngLat getCoordinates() {
        return coordinates;
    }

    /**
     * A getter method used to get direction of a given LngLatCompass object.
     *
     * @return a direction in CompassDirection format
     */
    public CompassDirection getDirection() {
        return direction;
    }
}
