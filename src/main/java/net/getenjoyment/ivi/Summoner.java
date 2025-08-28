package net.getenjoyment.ivi;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Summoner {

    // atributi
    private String gameName;
    private String tagLine;
    private String puuid;

    // konstruktor
    public Summoner(String gameName, String tagLine) {
        this.gameName = gameName;
        this.tagLine = tagLine;
    }

    public Summoner() {

    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getTagLine() {
        return tagLine;
    }

    public String getGameName() {
        return gameName;
    }

    // additional methods
    public String toUrlParams() {
        return URLEncoder.encode(this.gameName, StandardCharsets.UTF_8).replace("+", "%20") + "/" + URLEncoder.encode(this.tagLine, StandardCharsets.UTF_8);
    }

}
