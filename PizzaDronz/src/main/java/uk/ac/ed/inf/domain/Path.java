package uk.ac.ed.inf.domain;
import uk.ac.ed.inf.service.CentralAreaService;

import java.util.*;

/**
 * Path class has algorithm for find the shortest route from start point to its destination and routes to return to AT.
 */
public class Path {

    /**
     * The fields contain a fixed starting point coordinates of Appleton Tower ;
     * two lists of path stores coordinates of how to reach to destination and return to startPoint;
     * and its corresponding two list of angle direction.
     */
    private final LngLat startPoint = new LngLat(-3.186874,55.944494);
    private final LngLat destination;
    private final ArrayList<Double> angleList = new ArrayList<>();

    private final ArrayList<LngLat> pathEndToStart = new ArrayList<>();
    private final ArrayList<Double> angleEndToStart = new ArrayList<>();
    private ArrayList<LngLat> pathStartToEnd = new ArrayList<>();
    private ArrayList<Double> angleStartToEnd = new ArrayList<>();
    protected final double moveDistance = 0.00015;

    public Path(LngLat destination){
        this.destination = destination;
    }


    /**
     * This method uses A* search algorithm to find the local optimal path to reach the destination.
     */
    public void pathSearching(){

        HashMap<LngLat,Double> g = new HashMap<>();
        g.put(startPoint,0.0);

        HashMap<LngLat,Double> f = new HashMap<>();
        f.put(startPoint,startPoint.distanceTo(destination));

        PriorityQueue<LngLat> openSet = new PriorityQueue<>(300, Comparator.comparingDouble(a -> f.getOrDefault(a,1000000.0)));
        openSet.add(startPoint);

        HashMap<LngLat, LngLatCompass> pathFrom = new HashMap<>();
        boolean crossBoundary = false;

        while (!(openSet.isEmpty())) {

            LngLat currentPos = openSet.remove();
            ArrayList<CompassDirection> currentMove;

            if (currentPos.closeTo(destination)) {
                reconstructPath(pathFrom, currentPos);
                return;
            }

            //once entered the central area, cannot leave until order delivered.
            if (crossBoundary){
                currentMove = checkExistCentralArea(currentPos);
            }else {
                currentMove = currentPos.availableNeighbours();
            }

            for (CompassDirection direction : currentMove) {

                LngLat nextPos = currentPos.nextPosition(direction);

                if (currentPos.inArea(nextPos, CentralAreaService.getCentralAreaPos())){
                    crossBoundary = true;
                }
                double g_temp = g.get(currentPos) + moveDistance;
                g.putIfAbsent(nextPos, 1000000.0);

                if (g_temp < g.get(nextPos)) {

                    LngLatCompass currentPosDirection = new LngLatCompass(currentPos, direction);
                    pathFrom.put(nextPos,currentPosDirection);

                    f.put(nextPos, (g_temp + nextPos.distanceTo(destination) * 2));
                    g.put(nextPos, g_temp);

                    if (!(openSet.contains(nextPos))) {
                        openSet.add(nextPos);
                    }
                }
            }
        }
    }

    /**
     * This method is used to get valid direction that are able to move
     * if the drone entered central area but the order is not delivered.
     *
     * @param pos input position in LngLat format
     * @return a list of valid direction in from CompassDirection
     */
    public ArrayList<CompassDirection> checkExistCentralArea(LngLat pos){

        ArrayList<CompassDirection> currentMove = new ArrayList<>();

        for (CompassDirection direction : pos.availableNeighbours()) {

            LngLat nextPos = pos.nextPosition(direction);

            // only use the direction that are inside the central area
            if (!(pos.inArea(nextPos, CentralAreaService.getCentralAreaPos()))) {
                currentMove.add(direction);
            }
        }
        return currentMove;
    }

    /**
     * This method is used to construct path from start point to current position.
     *
     * @param pathFrom coordinates and direction of movement from start point to position in hashmap format
     * @param current a position in LngLat format
     */
    public void reconstructPath(HashMap<LngLat, LngLatCompass> pathFrom, LngLat current) {

        pathEndToStart.add(current);

        while (pathFrom.containsKey(current)) {

            LngLat nextPosition = pathFrom.get(current).getCoordinates();
            angleList.add(pathFrom.get(current).getDirection().getVal());
            pathEndToStart.add(nextPosition);
            current = nextPosition;
        }
    }

    /**
     * This method is used to get the inverse of given path and its corresponding direction.
     */
    public void pathInverse(){

        pathStartToEnd = new ArrayList<>(pathEndToStart);
        Collections.reverse(pathStartToEnd);

        angleStartToEnd = new ArrayList<>(angleList);
        Collections.reverse(angleStartToEnd);
        for (Double angle: angleList) {

            double newAngle;

            if (angle >= 180) {

                newAngle = angle - 180;

            } else {

                newAngle = angle + 180;
            }
            angleEndToStart.add(newAngle);
        }
    }

    /**
     * A getter method use to get corresponding path from Appleton Tower to target restaurant.
     *
     * @return a list of LngLat coordinates
     */
    public ArrayList<LngLat> getPathStartToEnd() {
        return pathStartToEnd;
    }

    /**
     * A getter method use to get corresponding directions from Appleton Tower to target restaurant.
     *
     * @return a list of direction values
     */
    public ArrayList<Double> getAngleStartToEnd() {
        return angleStartToEnd;
    }

    /**
     * A getter method use to get corresponding path from target restaurant to Appleton Tower.
     *
     * @return a list of LngLat coordinates
     */
    public ArrayList<LngLat> getPathEndToStart() {
        return pathEndToStart;
    }

    /**
     * A getter method use to get corresponding direction from target restaurant to Appleton Tower.
     *
     * @return a list of direction values
     */
    public ArrayList<Double> getAngleEndToStart() {
        return angleEndToStart;
    }

}
