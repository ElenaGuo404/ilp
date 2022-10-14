package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * CentralArea is a singleton access pattern class.
 * It creates the object by reading data from REST-service.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class CentralArea {

    /**
     * The field is used to cover the corner positions of this central area.
     * The INSTANCE to control the class.
     */
    private static CentralArea INSTANCE;
    public static LngLat[] centralAreaPos;

    private CentralArea(){


        String echoBasis = "centralArea";

        try{
            centralAreaPos = new ObjectMapper().readValue(
                    new URL("https://ilp-rest.azurewebsites.net/" + echoBasis) , LngLat[]. class );
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to generate instance for central area.
     *
     * @return instance of central area
     */
    public static CentralArea getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new CentralArea();
        }
        return INSTANCE;
    }

}
