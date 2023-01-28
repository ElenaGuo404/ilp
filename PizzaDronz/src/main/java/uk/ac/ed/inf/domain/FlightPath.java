package uk.ac.ed.inf.domain;

/**
 * FlightPath class is used to present a single flightpath object and its data retrieved from Order and Path.
 */
public class FlightPath {

    /**
     * The fields present individual order's order number and its path's movement, direction and time for move.
     */
    private final String orderNo;
    private final float fromLongitude;
    private final float fromLatitude;
    protected final float angle;
    protected final float toLongitude;
    protected final float toLatitude;
    protected final long ticksSinceStartOfCalculation;

    public FlightPath(String orderNo, float fromLongitude, float fromLatitude,
                float angle, float toLongitude, float toLatitude, long ticksSinceStartOfCalculation) {
        this.orderNo = orderNo;
        this.fromLongitude = fromLongitude;
        this.fromLatitude = fromLatitude;
        this.angle = angle;
        this.toLongitude = toLongitude;
        this.toLatitude = toLatitude;
        this.ticksSinceStartOfCalculation = ticksSinceStartOfCalculation;
    }

    /**
     * A getter method used to get the longitude of current position.
     *
     * @return float number of longitude
     */
    public float getFromLongitude() {
        return fromLongitude;
    }

    /**
     * A getter method used to get the latitude of current position.
     *
     * @return float number of latitude
     */
    public float getFromLatitude() {
        return fromLatitude;
    }

    public String getOrderNo() {
        return orderNo;
    }
}
