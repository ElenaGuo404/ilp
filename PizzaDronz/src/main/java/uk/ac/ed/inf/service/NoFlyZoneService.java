package uk.ac.ed.inf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ed.inf.domain.NoFlyZone;

import java.io.IOException;
import java.net.URL;

/**
 * NoFlyZoneService is a singleton access pattern class.
 * It creates objects by reading data from REST-Server.
 */
public class NoFlyZoneService {

    /**
     * The private field zones is used to store list of no-fly zones from REST-Server.
     */
    private static NoFlyZoneService INSTANCE;
    private static NoFlyZone[] zones;

    public NoFlyZoneService(String serverBaseAddress){

        String echoBasis = "/noFlyZones";

        try{
            zones = new ObjectMapper().readValue(
                    new URL(serverBaseAddress + echoBasis), NoFlyZone[].class );
        } catch (IOException e) {
            e.printStackTrace();
        }
      }

    /**
     * The getter method allows other class to get list of no-fly zones.
     *
     * @return list of no-fly zones
     */
    public static NoFlyZone[] getZoneList() {
        return zones;
    }

    /**
     * This method is used to initialise no-fly zone service.
     *
     * @param serverBaseAddress String of valid REST-Server base address URL
     */
    public static void init(String serverBaseAddress){
        if (INSTANCE == null){
            INSTANCE = new NoFlyZoneService(serverBaseAddress);
        }
    }

}
