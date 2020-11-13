/**
 * @author Deniz Oral 1915691
 * @version 1.0
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Motd {

    static String message = "";

    private static void getMessage() {
        String motd = get_motd("http://cswebcat.swansea.ac.uk/puzzle");
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
        message = get_motd("http://cswebcat.swansea.ac.uk/message?solution=CS-230" + decodedMotd + characterCount);
    }

    private static String get_motd(String url) {
        String motd = "";
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

            in.close();
        } catch (IOException e){
            System.out.println(e);
        }
        return motd;
    }

    public static void main(String[] args) {
        getMessage();
        System.out.println(message);
    }
}