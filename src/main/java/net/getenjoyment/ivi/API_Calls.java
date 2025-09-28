package net.getenjoyment.ivi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class API_Calls {
    public static String getPUUID (Summoner igralec) {
        String url = "https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + igralec.toUrlParams();

        System.out.println(url);

        // naredimo nov api call - v našem primeru GET
        HttpRequest summonerGet = HttpRequest.newBuilder()
                // iz zgornjega Stringa naredimo URL
                .uri(URI.create(url))

                // VSAK DAN rabim nov api key.!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                .header("X-Riot-Token", API_Key.getApiKey())

                /*          Tako bi naredil body in objasnil da se gre za GET če NE BI uporabljal GSON.
                .GET(HttpRequest.BodyPublishers.ofString({"startTime": "1754006400"}))
                                                            body text ^
                 */

                // Objasnimo, da se gre za get in ne post / put / delete
                .GET()

                // zbuildamo?
                .build();

        // Odpremo nov http klient (predstavljaj si kaksen web extension ki poslje api call)
        HttpClient httpClient = HttpClient.newHttpClient();

        // v klientu pošljemo naš request in z bodyhandlers sporočimo da nazaj pričakujemo String.
        // (pred =) shranimo odgovor v getResponse
        HttpResponse<String> nameResponse;
        try {
            nameResponse = httpClient.send(summonerGet, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(nameResponse.body());
    }

    public static String[] getMatchHistory(Summoner igralec, int count, Long endTime, Long startTime, Integer start) {

        // naredimo nov match history za nasega igralca, s katerim bomo manipulirali
        MatchHistoryPullConfig matchHistoryPullConfig = new MatchHistoryPullConfig();

        if(start != null) matchHistoryPullConfig.setStart(start);
        if(endTime != null) matchHistoryPullConfig.setEndTime(endTime);
        if(startTime != null) matchHistoryPullConfig.setStartTime(startTime);
        matchHistoryPullConfig.setCount(count);

        // sestavimo url za API call
        String url = "https://europe.api.riotgames.com/tft/match/v1/matches/by-puuid/" + igralec.getPuuid() + "/ids" + matchHistoryPullConfig.toUrlParams();

        System.out.println(url);

        // dejanski api call za match history
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Riot-Token", API_Key.getApiKey())
                .GET()
                .build();

        // spet odpremo nov klient da posljemo api call
        HttpClient httpClient = HttpClient.newHttpClient();

        // posljemo dejanski api call prek clienta
        HttpResponse<String> matchHistoryResponse;
        try {
            matchHistoryResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // če igralec nima dosti iger ki ustrezajo kriterijem, RIOT api lahko vrne nazaj [] (prazen seznam). zato preverimo, da se to ni zgodilo.
        if(!matchHistoryResponse.body().startsWith("[")) {
            throw new RuntimeException("Nekaj je narobe z API odgovorom pri pridobivanju Match historyja igralca");
        }

        // nov gson da lahko spodaj nas response spremenimo nazaj iz json-a v String[]
        Gson gson = new Gson();

        // nas response dejansko spremenimo v list
        return gson.fromJson(matchHistoryResponse.body(), String[].class);
    }

    // overloading so that i can have optional arguments.
    public static String[] getMatchHistory(Summoner igralec) {
        return getMatchHistory(igralec, 100, null, null, null);
    }

    public static String[] getMatchHistory(Summoner igralec, int count) {
        return getMatchHistory(igralec, count, null, null, null);
    }

    public static String[] getMatchHistory(Summoner igralec, int count, Integer start) {
        return getMatchHistory(igralec, count, null, null, start);
    }

    public static TFT_Match getMatchData(String match_id) {
        String url = "https://europe.api.riotgames.com/tft/match/v1/matches/" + match_id;

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Riot-Token", API_Key.getApiKey())
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> matchDataResponse;

        try {
            matchDataResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        return gson.fromJson(matchDataResponse.body(), TFT_Match.class);
    }
}
