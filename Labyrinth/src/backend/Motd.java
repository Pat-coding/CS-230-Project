package backend; /**
 * @author Deniz Oral 1915691
 * @version 1.0
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Message of the day model. Will get the message from url,
 * decode the message.
 */
public class Motd {

    /**
     * Getter for message of the day
     * @return decoded message
     */
    public static String getMessage() {
        return decodeMotd();
    }

    /**
     * This method will get the message of the day
     * using a url
     * @param url input is parameters
     * @return MOTD
     */
    private static String getMotd(String url) {

        String motd = "";

        //We need to use a try-catch
        try {
            URL puzzle = new URL(url);

            //Open a URL connection and receive the data.
            URLConnection con = puzzle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            //decode the url data
            while ((inputLine = in.readLine()) != null) {
                motd += inputLine;
            }
            //closing the input stream
            in.close();
        } catch (IOException e){
            //displays error message
            System.out.println(e);
        }
        return motd;
    }

    /**
     * This method will decode the message of the day
     * into a suitable string
     * @return MOTD string
     */
    private static String decodeMotd(){

        String motd = getMotd("http://cswebcat.swansea.ac.uk/puzzle");
        String decodedMotd = "";
        int characterCount = motd.length() + 6;
        int shift = 1;
        int direction = 0;
        char characters;

        for (int i = 0; i<motd.length(); i++) {
            if (direction == 0) {
                characters = (char)(((int)motd.charAt(i) - shift + 26 - 65) % 26 + 65);
                direction = 1;
            } else {
                characters = (char)(((int)motd.charAt(i) + shift - 65) % 26 + 65);
                direction = 0;
            }
            shift++;
            decodedMotd += characters;
        }
        return getMotd("http://cswebcat.swansea.ac.uk/message?solution=CS-230" + decodedMotd + characterCount);
    }

}
