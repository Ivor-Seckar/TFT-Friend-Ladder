package net.getenjoyment.ivi;

import javax.lang.model.type.UnionType;

public class MatchParticipant {
    private MatchParticipantCompanion companion;
    private int gold_left;
    private int last_round;
    private int level;
    private int placement;
    private int players_eliminated;
    private String puuid;
    private String riotIdGameName;
    private String riotIdTagline;
    private float time_eliminated;
    private int total_damage_to_players;
    private Trait[] traits;
    private Unit[] units;
    private boolean win;

    public MatchParticipant() {

    }

    public MatchParticipantCompanion getCompanion() {
        return companion;
    }

    public void setCompanion(MatchParticipantCompanion companion) {
        this.companion = companion;
    }

    public int getGold_left() {
        return gold_left;
    }

    public void setGold_left(int gold_left) {
        this.gold_left = gold_left;
    }

    public int getLast_round() {
        return last_round;
    }

    public void setLast_round(int last_round) {
        this.last_round = last_round;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPlacement() {
        return placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public int getPlayers_eliminated() {
        return players_eliminated;
    }

    public void setPlayers_eliminated(int players_eliminated) {
        this.players_eliminated = players_eliminated;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getRiotIdGameName() {
        return riotIdGameName;
    }

    public void setRiotIdGameName(String riotIdGameName) {
        this.riotIdGameName = riotIdGameName;
    }

    public String getRiotIdTagline() {
        return riotIdTagline;
    }

    public void setRiotIdTagline(String riotIdTagline) {
        this.riotIdTagline = riotIdTagline;
    }

    public float getTime_eliminated() {
        return time_eliminated;
    }

    public void setTime_eliminated(float time_eliminated) {
        this.time_eliminated = time_eliminated;
    }

    public int getTotal_damage_to_players() {
        return total_damage_to_players;
    }

    public void setTotal_damage_to_players(int total_damage_to_players) {
        this.total_damage_to_players = total_damage_to_players;
    }

    public Trait[] getTraits() {
        return traits;
    }

    public void setTraits(Trait[] traits) {
        this.traits = traits;
    }

    public Unit[] getUnits() {
        return units;
    }

    public void setUnits(Unit[] units) {
        this.units = units;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}
