package net.getenjoyment.ivi;

public class MatchHistoryPullConfig {
    // atributi
    private int start;  // how far into the list of matches do i want the api to start fetching --> 0 = latest match, 1 = 2nd latest match...
    private String endTime; // shows the matches BEFORE the set endTime.
    private String startTime; // shows the matches AFTER the set startTime. if player has 2 matches 2.8. and 3 on 3.8. and start time is 3.8., it will only show those 3
    private int count; // how many matches to show. MIN{0}, MAX{100}, default = 20


    // konstruktor
    public MatchHistoryPullConfig  () {

    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
