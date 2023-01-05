package uk.ac.ed.inf.domain;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * ArgumentCheck class is used to check if the input argument is in valid format
 */
public class ArgumentCheck {

    /**
     * This method is used to check if the input string has correct date format
     *
     * @param date date of String format in "yyyy-mm-dd"
     * @return a boolean value of true for pass the check
     */
    public static boolean validTime(String date){

        if (!date.trim().equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            try {
                dateFormat.parse(date);
            }
            catch (ParseException e) {
                System.err.println(date + "---Invalid Input Date!");
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to check if the input string can be converted to URl format
     *
     * @param baseUrl base url in String format
     * @return a boolean value of true for pass the check
     */
    public static boolean validUrl(String baseUrl) {

        try {
            new URL(baseUrl).toURI();
            return true;
        }

        catch (Exception e) {
            System.err.println(baseUrl + "---Invalid Input URL!");
            return false;
        }
    }
}
