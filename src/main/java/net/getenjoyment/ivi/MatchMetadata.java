package net.getenjoyment.ivi;

public class MatchMetadata {
    private String data_version;    // if before patch 9.22., then it is {1}  |||   if on patch 9.22 or after, then data version is {2}
    private String match_id;
    private String[] participants;

    public MatchMetadata() {

    }

    public String getData_version() {
        return data_version;
    }

    public void setData_version(String data_version) {
        this.data_version = data_version;
    }

    public String[] getParticipants() {
        return participants;
    }

    public void setParticipants(String[] participants) {
        this.participants = participants;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }
}
