package backend;

/**
 * This class stores the profile information including results of all their games.
 * @author Ryan Humphreys
 * @version 1.0
 */

public class Profile {

    public double winRatio;
    private String profileName;
    private int profileWinCount;
    private int profileLossCount;
    private int profileGamesPlayed;

    /**
     * Constructor for new profiles made during runtime.
     *
     * @param profileName profile name
     */
    public Profile(String profileName) {
        this.profileName = profileName;
        this.profileWinCount = 0;
        this.profileLossCount = 0;
        this.profileGamesPlayed = 0;
    }

    /**
     * Constructor for importing profiles from file.
     *
     * @param profileName      profile name
     * @param profileWinCount  number of wins
     * @param profileLossCount number of losses
     */
    public Profile(String profileName, int profileWinCount, int profileLossCount) {
        this.profileName = profileName;
        this.profileWinCount = profileWinCount;
        this.profileLossCount = profileLossCount;
        this.profileGamesPlayed = profileWinCount + profileLossCount;
    }

    /**
     * Gets number of win of profile.
     * @return number of wins
     */
    public int getWinCount() {
        return profileWinCount;
    }

    /**
     * Gets number of losses of profile.
     * @return number of losses
     */
    public int getLoseCount() {
        return profileLossCount;
    }

    /**
     * Gets win/loss ration of profile.
     * @return ratio of wins/losses
     */
    public double getWinRatio() {
        if (profileWinCount == 0 | profileLossCount == 0) {
            return this.winRatio = 0;
        } else {
            return this.winRatio = profileWinCount / profileLossCount ;
        }
    }

    /**
     * Gets name of profile.
     * @return profile name
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * Sets name of profile.
     * @param profileName new profile name to be set
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * Gets number of games played.
     * @return total number of games played
     */
    public int getGamesPlayed() {
        return profileGamesPlayed;
    }

    /**
     *
     * Adds 1 to profile win count.
     */
    public void incrementWinCount() {
        this.profileWinCount++;
        this.profileGamesPlayed++;

    }

    /**
     * Adds 1 to profile loss count.
     */
    public void incrementLoseCount() {
        this.profileLossCount++;
        this.profileGamesPlayed++;
    }
}
