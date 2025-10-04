package net.getenjoyment.ivi;

public class MatchInfo {
    private String endOfGameResult;
    private long gameCreation;
    private long gameId;
    private long game_datetime;
    private float game_length; // in seconds
    private String game_version; // which patch and when it was deployed
    private int mapId; // Convergence - the TFT map, others are LOL
    private MatchParticipant[] participants;
    private int queue_id;  // 1090 - TFT games, 1100 - Ranked TFT games, 1110 - TFT tutorial games, 1111 - TFT test games, 1150 (before set 7) & 1160 (after set 7) - Double up  1210 - Choncc
    private String tft_game_type; // standard, pairs, ...
    private String tft_set_core_name;
    private int tft_set_number;

    public MatchInfo() {

    }

    public String getEndOfGameResult() {
        return endOfGameResult;
    }

    public void setEndOfGameResult(String endOfGameResult) {
        this.endOfGameResult = endOfGameResult;
    }

    public long getGameCreation() {
        return gameCreation;
    }

    public void setGameCreation(long gameCreation) {
        this.gameCreation = gameCreation;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getGame_datetime() {
        return game_datetime;
    }

    public void setGame_datetime(long game_datetime) {
        this.game_datetime = game_datetime;
    }

    public float getGame_length() {
        return game_length;
    }

    public void setGame_length(float game_length) {
        this.game_length = game_length;
    }

    public String getGame_version() {
        return game_version;
    }

    public void setGame_version(String game_version) {
        this.game_version = game_version;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public MatchParticipant[] getParticipants() {
        return participants;
    }

    public void setParticipants(MatchParticipant[] participants) {
        this.participants = participants;
    }

    public int getQueue_id() {
        return queue_id;
    }

    public void setQueue_id(int queue_id) {
        this.queue_id = queue_id;
    }

    public String getTft_game_type() {
        return tft_game_type;
    }

    public void setTft_game_type(String tft_game_type) {
        this.tft_game_type = tft_game_type;
    }

    public String getTft_set_core_name() {
        return tft_set_core_name;
    }

    public void setTft_set_core_name(String tft_set_core_name) {
        this.tft_set_core_name = tft_set_core_name;
    }

    public int getTft_set_number() {
        return tft_set_number;
    }

    public void setTft_set_number(int tft_set_number) {
        this.tft_set_number = tft_set_number;
    }

    // additional methods
    public void izpisiParticipants() {
        for(MatchParticipant participant : participants) {
            System.out.println(participant.getRiotIdGameName() + " #" + participant.getRiotIdTagline());
        }
    }
}
