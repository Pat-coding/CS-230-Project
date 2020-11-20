/**
 * @author Ryan Humphreys 1903246
 */

import java.util.concurrent.atomic.AtomicInteger;

public class Profile {

    private String profileName;
    private int profileWinCount;
    private int profileLossCount;
    private int profileGamesPlayed;
    public double winRatio;

    /**
     * Constructor for new profiles made during runtime
     * @param profileName profile name
     */
    public Profile(String profileName) {
        this.profileName = profileName;
        this.profileWinCount = 0;
        this.profileLossCount = 0;
        this.profileGamesPlayed = 0;
    }

    /**
     * Constructor for importing profiles from file
     * @param profileName profile name
     * @param profileWinCount number of wins
     * @param profileLossCount number of losses
     */
    public Profile(String profileName, int profileWinCount, int profileLossCount){
        this.profileName = profileName;
        this.profileWinCount = profileWinCount;
        this.profileLossCount = profileLossCount;
        this.profileGamesPlayed = profileWinCount + profileLossCount;
    }

    /**
     * @return number of wins
     */
    public int getWinCount(){
        return profileWinCount;
    }

    /**
     * @return number of losses
     */
    public int getLoseCount(){
        return profileLossCount;
    }

    /**
     * @return ratio of wins/losses
     */
    public double getWinRatio(){
        return winRatio;
    }

    /**
     * @return profile name
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * @param profileName new profile name to be set
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * @return total number of games played
     */
    public int getGamesPlayed() {
        return profileGamesPlayed;
    }

    /**
     * Adds 1 to profile win count
     */
    public void incrementWinCount(){
        this.profileWinCount++;
        this.profileGamesPlayed++;
        if (profileWinCount == 0 | profileLossCount == 0) {
            this.winRatio = 0;
        } else{
            this.winRatio = profileWinCount / profileLossCount;
        }
    }

    /**
     * Adds 1 to profile loss count
     */
    public void incrementLoseCount() {
        this.profileLossCount++;
        this.profileGamesPlayed++;
        if (profileWinCount == 0 | profileLossCount == 0) {
            this.winRatio = 0;
        } else {
            this.winRatio = profileWinCount / profileLossCount;
        }
    }
}
