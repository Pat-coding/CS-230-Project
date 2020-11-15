/**
 * @author Ryan Humphreys 1903246
 * @version 1.0
 */

import java.util.concurrent.atomic.AtomicInteger;

public class Profile {

    private int profileID;
    private static AtomicInteger profIDCounter = new AtomicInteger(0);
    private String profileName;
    private int[] profileWinCount;

    public Profile(String name, String profileName) {
        this.profileID = profIDCounter.incrementAndGet();
        this.profileName = profileName;
    }
}
