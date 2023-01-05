package uk.ac.ed.inf.domain;

/**
 * CompassDirection is a base enum class for all compass directions which has its value of angle.
 */

public enum CompassDirection {

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
