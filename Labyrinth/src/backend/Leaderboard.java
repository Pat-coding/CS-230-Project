package backend;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author
 * @version 1.0.0
 */

public class Leaderboard {

    private ArrayList<Profile> profileList;
    private int sortType;

    public Leaderboard(ArrayList<Profile> profiles, int sortType, boolean asc){
        this.profileList = sort(profiles, sortType, asc);
    }

    public int getSortType() {
        return sortType;
    }

    /**
     * this method returns the leaderboard
     * @return array list of profiles
     */
    public ArrayList<Profile> getLeaderboard() {
        return profileList;
    }

    /**
     * this method sorts a list of profiles depending on the type of sort requested
     * @param profileList
     * @param sortType
     * @param asc
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
     * this methods sorts an array list of players depending on players wins
     * @param profileList
     * @param asc
     * @return array list of players in winning order
     */
    private ArrayList<Profile> sortWins(ArrayList<Profile> profileList, boolean asc) {
        for(int i = 1; i < profileList.size(); i++) {
            Profile key = profileList.get(i);
            int j = i - 1;

            while (j >= 0 && (profileList.get(j).getWinCount() > key.getWinCount())) {
                profileList.set((j+1), profileList.get(j));
                j--;
            }
            profileList.set((j+1), key);
        }
        if(asc){
            return profileList;
        } else {
            Collections.reverse(profileList);
            return profileList;
        }
    }

    /**
     * this method sorts profiles depending on number of losses they have
     * @param profileList
     * @param asc
     * @return array list of players sorted by losses
     */

    private ArrayList<Profile> sortLosses(ArrayList<Profile> profileList, boolean asc) {
        for(int i = 1; i < profileList.size(); i++) {
            Profile key = profileList.get(i);
            int j = i - 1;

            while (j >= 0 && (profileList.get(j).getLoseCount() > key.getLoseCount())) {
                profileList.set((j+1), profileList.get(j));
                j--;
            }
            profileList.set((j+1), key);
        }
        if(asc){
            return profileList;
        } else {
            Collections.reverse(profileList);
            return profileList;
        }
    }

    /**
     * this method sorts players depending on their win ration
     * @param profileList
     * @param asc
     * @return sorted array list of players based on win ratio
     */

    private ArrayList<Profile> sortWinRatio(ArrayList<Profile> profileList, boolean asc) {
        for(int i = 1; i < profileList.size(); i++) {
            Profile key = profileList.get(i);
            int j = i - 1;

            while (j >= 0 && (profileList.get(j).getWinRatio() > key.getWinRatio())) {
                profileList.set((j+1), profileList.get(j));
                j--;
            }
            profileList.set((j+1), key);
        }
        if(asc){
            return profileList;
        } else {
            Collections.reverse(profileList);
            return profileList;
        }
    }

    /**
     * this method sorts players by the ammount of games they have played
     * @param profileList
     * @param asc
     * @return sorted array list by number of games played
     */
    private ArrayList<Profile> sortGamesPlayed(ArrayList<Profile> profileList, boolean asc) {
        for(int i = 1; i < profileList.size(); i++) {
            Profile key = profileList.get(i);
            int j = i - 1;

            while (j >= 0 && (profileList.get(j).getGamesPlayed() > key.getGamesPlayed())) {
                profileList.set((j+1), profileList.get(j));
                j--;
            }
            profileList.set((j+1), key);
        }
        if(asc){
            return profileList;
        } else {
            Collections.reverse(profileList);
            return profileList;
        }
    }
}
