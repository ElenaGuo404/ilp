package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class LngLat {

    public double lng;
    public double lat;

    public LngLat (@JsonProperty("longitude")double lng,
                   @JsonProperty("latitude")double lat){
        this.lng = lng;
        this.lat = lat;
    }


    public enum CompassDirection{
        E(0),
        ENE(22.5),
        NE(45),
        NNE(67.5),
        N(90),
        NNW(112.5),
        NW(135),
        WNW(157.5),
        W(180),
        WSW(202.5),
        SW(225),
        SSW(247.5),
        S(270),
        SSE(292.5),
        SE(315),
        ESE(337.5);

        private final double value;

        CompassDirection(double value) {
            this.value = value;
        }
        public double getVal() {
            return value;
        }
    }

    public boolean inCentralArea(){

        LngLat[] centralAreaPos = CentralArea.centralAreaPos;
        boolean threeEdges = true;
        boolean lastEdge = true;

        LngLat point0 = centralAreaPos[0];
        LngLat point3 = centralAreaPos[3];

        if ((point0.lng - point3.lng) * (this.lat - point3.lat) - (this.lng - point3.lng) * (point0.lat - point3.lat) < 0){
            lastEdge = false;
        }

        for(int i=0;i < centralAreaPos.length-1;i++){

            LngLat point1 = centralAreaPos[i];
            LngLat point2 = centralAreaPos[i+1];

            double distance = (point2.lng - point1.lng) * (this.lat - point1.lat) - (this.lng - point1.lng) * (point2.lat - point1.lat);

            if (distance < 0) {
                threeEdges = false;
            }
        }

        return threeEdges && lastEdge;
    }

    public double distanceTo (LngLat pos){
        double lng1 = pos.lng-this.lng;
        double lat1 = pos.lat-this.lat;
        double PythagoreanDis = Math.sqrt(lng1*lng1 + lat1*lat1);

        return PythagoreanDis;
    }

    public boolean closeTo (LngLat pos){
        return distanceTo(pos) < 0.00015;
    }

    public LngLat nextPosition(CompassDirection direction) {
        double lng = this.lng + 0.00015 * Math.sin(Math.toRadians(direction.getVal()));
        double lat = this.lat + 0.00015 * Math.cos(Math.toRadians(direction.getVal()));

        LngLat newPos = new LngLat(lng,lat);

        return newPos;
    }

}
