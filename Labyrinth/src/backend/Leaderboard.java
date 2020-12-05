package backend;

import java.util.ArrayList;
import java.util.Collections;

/**
 *This class sorts the leaderboards after a game to
 * make sure they are up to date.
 * wants them to be sorted
 * @author
 * @version 1.0.0
 */

public class Leaderboard {

    ArrayList<Profile> profileCopy;
    private ArrayList<Profile> profileList;
    private int sortType;

    /**
     * This constructer determines what profiles will be sorted and how.
     * @param profiles profiles being sorted
     * @param sortType type of sort
     * @param asc if it is in assending or descending order
     */

    public Leaderboard(ArrayList<Profile> profiles, int sortType, boolean asc) {
        this.profileCopy = new ArrayList<>(profiles);
        this.profileList = sort(profileCopy, sortType, asc);
    }

    /**
     * Returns switch key for sorting.
     * @return int between 0 and 3
     */
    public int getSortType() {
        return sortType;
    }

    /**
     * This method returns the leaderboard.
     * @return array list of profiles
     */
    public ArrayList<Profile> getLeaderboard() {
        return profileList;
    }

    /**
     * This method sorts a list of profiles depending on the type of sort requested.
     * @param profileList profiles getting sorted
     * @param sortType how they're getting sorted
     * @param asc ascending or descending order
     * @return sorted array list of profiles
     */
    public ArrayList<Profile> sort(ArrayList<Profile> profileList, int sortType, boolean asc) {
        switch (sortType) {
            case 0:
                return sortWins(profileList, asc);
            case 1:
                return sortLosses(profileList, asc);
            case 2:
                return sortWinRatio(profileList, asc);
            case 3:
                return sortGamesPlayed(profileList, asc);
            default:
                System.out.println("Error: unknown sort type (Use integers 0-3)");
                return null;
        }
    }

    /**
     * This methods sorts an array list of players depending on players wins.
     * @param profileList profiles getting sorted
     * @param asc ascending or descending order
     * @return array list of players in winning order
     */
    private ArrayList<Profile> sortWins(ArrayList<Profile> profileList, boolean asc) {
        for (int i = 1; i < profileList.size(); i++) {
            Profile key = profileList.get(i);
            int j = i - 1;

            while (j >= 0 && (profileList.get(j).getWinCount() > key.getWinCount())) {
                profileList.set((j + 1), profileList.get(j));
                j--;
            }
            profileList.set((j + 1), key);
        }
        if (asc) {
            return profileList;
        } else {
            Collections.reverse(profileList);
            return profileList;
        }
    }

    /**
     * This method sorts profiles depending on number of losses they have.
     * @param profileList profiles getting sorted
     * @param asc ascending or descending order
     * @return array list of players sorted by losses
     */

    private ArrayList<Profile> sortLosses(ArrayList<Profile> profileList, boolean asc) {
        for (int i = 1; i < profileList.size(); i++) {
            Profile key = profileList.get(i);
            int j = i - 1;

            while (j >= 0 && (profileList.get(j).getLoseCount() > key.getLoseCount())) {
                profileList.set((j + 1), profileList.get(j));
                j--;
            }
            profileList.set((j + 1), key);
        }
        if (asc) {
            return profileList;
        } else {
            Collections.reverse(profileList);
            return profileList;
        }
    }

    /**
     * This method sorts players depending on their win ration.
     * @param profileList profiles getting sorted
     * @param asc ascending or descending order
     * @return sorted array list of players based on win ratio
     */

    private ArrayList<Profile> sortWinRatio(ArrayList<Profile> profileList, boolean asc) {
        for (int i = 1; i < profileList.size(); i++) {
            Profile key = profileList.get(i);
            int j = i - 1;

            while (j >= 0 && (profileList.get(j).getWinRatio() > key.getWinRatio())) {
                profileList.set((j + 1), profileList.get(j));
                j--;
            }
            profileList.set((j + 1), key);
        }
        if (asc) {
            return profileList;
        } else {
            Collections.reverse(profileList);
            return profileList;
        }
    }

    /**
     * This method sorts players by the ammount of games they have played.
     * @param profileList profiles getting sorted
     * @param asc ascending or descending order
     * @return sorted array list by number of games played
     */
    private ArrayList<Profile> sortGamesPlayed(ArrayList<Profile> profileList, boolean asc) {
        for (int i = 1; i < profileList.size(); i++) {
            Profile key = profileList.get(i);
            int j = i - 1;

            while (j >= 0 && (profileList.get(j).getGamesPlayed() > key.getGamesPlayed())) {
                profileList.set((j + 1), profileList.get(j));
                j--;
            }
            profileList.set((j + 1), key);
        }
        if (asc) {
            return profileList;
        } else {
            Collections.reverse(profileList);
            return profileList;
        }
    }
}
