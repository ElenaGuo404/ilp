package uk.ac.ed.inf.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ed.inf.domain.LngLat;

import java.io.IOException;
import java.net.URL;

/**
 * CentralAreaService is a singleton access pattern class.
 * It creates the object by reading data from REST-service.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class CentralAreaService {

    /**
     * The field centralAreaPos is used to store the corner positions of this central area from REST-Server.
     */
    private static CentralAreaService INSTANCE;
    private static LngLat[] centralAreaPos;

    public CentralAreaService(String baseUrl){

        String echoBasis = "/centralArea";

        try{
            centralAreaPos = new ObjectMapper().readValue(
                    new URL(baseUrl + echoBasis) , LngLat[].class );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to initialise central area service.
     *
     * @param baseURL input string of valid REST-Server base address URL
     */
    public static void init(String baseURL){
        if(INSTANCE == null){
            INSTANCE = new CentralAreaService(baseURL);

        }
    }

    /**
     * This is a getter used to get corner coordinates.
     *
     * @return list of corner coordinates
     */
    public static LngLat[] getCentralAreaPos(){
        return centralAreaPos;
    }

}
