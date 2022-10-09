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
        for(int i=0; i<=centralAreaPos.length; i++){
            LngLat point1 = centralAreaPos[i];
            LngLat point2 = centralAreaPos[i+1];

            double distance = (point2.lng - point1.lng) * (this.lat - point1.lat) - (this.lng - point1.lng) * (point2.lat - point1.lat);


        }
        return true;
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


//    public LngLat nextPosition(CompassDirection direction, LngLat currentPos){
//        LngLat newPos = currentPos;
//
//        if (direction.getVal() == 0){
//            newPos.lng = currentPos.lng + 0.00015;
//        }
//        else if (direction.getVal() == 90){
//            newPos.lat = currentPos.lat + 0.00015;
//        }
//        else if (direction.getVal() == 180){
//            newPos.lng = currentPos.lng - 0.00015;
//        }
//        else if (direction.getVal() == 270){
//            newPos.lat = currentPos.lat - 0.00015;
//        }
//        else if(direction.getVal() < 90 && direction.getVal() > 0){
//            newPos.lat = currentPos.lat + Math.sin(direction.getVal())/(Math.sin(90)/0.00015);
//            double angle = 180-90-direction.getVal();
//            newPos.lng = currentPos.lng + Math.sin(angle)/(Math.sin(90)/0.00015);
//        }
//        return newPos;
//    }
}
