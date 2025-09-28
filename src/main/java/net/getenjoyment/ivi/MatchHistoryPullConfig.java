package net.getenjoyment.ivi;

public class MatchHistoryPullConfig {
    // atributi
    private int start;  // how far into the list of matches do i want the api to start fetching --> 0 = latest match, 1 = 2nd latest match...
    private long endTime; // shows the matches BEFORE the set endTime.
    private long startTime; // shows the matches AFTER the set startTime. if player has 2 matches 2.8. and 3 on 3.8. and start time is 3.8., it will only show those 3
    private int count; // how many matches to show. MIN{0}, MAX{100}, default = 20


    // konstruktor
    public MatchHistoryPullConfig  () {

    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // additional methods
    // choosing match history pull parametres - 1 (startTime), 2 - (endTime)
//    public static void setParameters(MatchHistoryPullConfig matchHistoryPullConfig, int izbira, long unixDate) {
//        if(izbira == 1) {
//            matchHistoryPullConfig.setStartTime(unixDate);
//        } else if (izbira == 2) {
//            matchHistoryPullConfig.setEndTime(unixDate);
//        }
//    }

    public String toUrlParams(){
        StringBuilder mojUrl = new StringBuilder();
        Boolean first = true;

        // če je prvi param, ki smo ga dodali - append ?, drugače append &
        if(this.start > 0) {
            mojUrl.append(first ? "?" : "&").append("start=").append(start);
            first = false;
        }

        if(this.startTime > 0) {
            mojUrl.append(first ? "?" : "&").append("startTime=").append(startTime);
            first = false;
        }

        if(this.endTime > 0) {
            mojUrl.append(first ? "?" : "&").append("endTime=").append(endTime);
            first = false;
        }

        if(this.count > 0) {
            mojUrl.append(first ? "?" : "&").append("count=").append(count);
            first = false;
        }

        return mojUrl.toString();
    }
}
