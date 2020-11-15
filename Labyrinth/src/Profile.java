/**
 * @author Ryan Humphreys 1903246
 * @version 1.0
 */

import java.util.concurrent.atomic.AtomicInteger;

public class Profile {

    private int profileID;
    private static AtomicInteger profIDCounter = new AtomicInteger(0);
    private String profileName;
    private int profileWinCount;
    private int profileLossCount;
    public double winRatio;

    public Profile(String profileName) {
        this.profileID = profIDCounter.incrementAndGet();
        this.profileName = profileName;
        this.profileWinCount = 0;
    }
    public int getWinCount(){
        return profileWinCount;
    }

    public int getLoseCount(){
        return profileLossCount;
    }

    public double getWinRatio(){
        return winRatio;
    }

    public void incrementWinCount(){
        this.profileWinCount++;
        if (profileWinCount == 0 | profileLossCount == 0) {
            this.winRatio == 0;
        } else{
            this.winRatio = profileWinCount / profileLossCount;
        }
    }

    public void incrementLoseCount() {
        this.profileLossCount++;
        if (profileWinCount == 0 | profileLossCount == 0) {
            this.winRatio == 0;
        } else {
            this.winRatio = profileWinCount / profileLossCount;
        }
    }

    public int getProfileID() {
        return profileID;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
}
