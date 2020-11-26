package backend;

import java.util.ArrayList;
import java.util.Collections;


public class Leaderboard {

    private ArrayList<Profile> profileList;
    private int sortType;

//    public Leaderboard(int sortType, boolean asc){
//        this.profileList = sort(Menu.profiles, sortType, asc); //made menu profile static and added asc as parameters
//    }

    public int getSortType() {
        return sortType;
    }

//    public void setSortType(int sortType, boolean asc) {
//        this.sortType = sortType;
//        this.profileList = sort(Menu.profiles, sortType, asc); //added asc
//    }

    public ArrayList<Profile> getLeaderboard() {
        return profileList;
    }

    private ArrayList<Profile> sort(ArrayList<Profile> profileList, int sortType, boolean asc) {
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
