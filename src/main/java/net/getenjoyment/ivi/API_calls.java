package net.getenjoyment.ivi;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class API_calls {
    public static String getPUUID (String name, String tag) {
        String url = "https://europe.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + Methods.jsonify(name) + "/" + Methods.jsonify(tag);

        System.out.println(url);

        //naredimo nov api call - v našem primeru GET
        HttpRequest summonerGet = HttpRequest.newBuilder()
                //iz zgornjega Stringa naredimo URL
                .uri(URI.create(url))

                //VSAK DAN rabim nov api key.!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                .header("X-Riot-Token", API_Key.getApiKey())

                /*          Tako bi naredil body in objasnil da se gre za GET če NE BI uporabljal GSON.
                .GET(HttpRequest.BodyPublishers.ofString({"startTime": "1754006400"}))
                                                            body text ^
                 */

                //Objasnimo, da se gre za get in ne post / put / delete
                .GET()

                //zbuildamo?
                .build();

        //Odpremo nov http klient (predstavljaj si kaksen web extension ki poslje api call)
        HttpClient httpClient = HttpClient.newHttpClient();

        //v klientu pošljemo naš request in z bodyhandlers sporočimo da nazaj pričakujemo String.
        //(pred =) shranimo odgovor v getResponse
        HttpResponse<String> nameResponse;
        try {
            nameResponse = httpClient.send(summonerGet, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(nameResponse.body());
    }

    public static String[] getMatchHistory(Summoner igralec) {

        //naredimo nov match history za nasega igralca, s katerim bomo manipulirali
        MatchHistory mojMatchHistory = new MatchHistory();

        // TODO: settamo matchhistory parametre ---------------------------------------------to bo najbrz treba drugje
        mojMatchHistory.setStartTime("1754006400");

        //encodamo matchhistory parametre v url obliko
        String parametri = URLEncoder.encode(mojMatchHistory.getStartTime(), StandardCharsets.UTF_8);
        System.out.println(parametri);

        // TODO: NUJNO NAREDITI ENO METODO KI MI GENERIRA URL-JE  !
        String url = "https://europe.api.riotgames.com/tft/match/v1/matches/by-puuid/" + igralec.getPuuid() + "/ids?startTime=" + parametri;

        System.out.println(url);

        //dejanski api call za match history
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Riot-Token", API_Key.getApiKey())
                .GET()
                .build();

        //spet odpremo nov klient da posljemo api call
        HttpClient httpClient = HttpClient.newHttpClient();

        //posljemo dejanski api call prek clienta
        HttpResponse<String> matchHistoryResponse;
        try {
            matchHistoryResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        //nov gson da lahko spodaj nas response spremenimo nazaj iz json-a v String[]
        Gson gson = new Gson();

        //nas response dejansko spremenimo v list
        String[] seznamIger = gson.fromJson(matchHistoryResponse.body(), String[].class);

        return seznamIger;
    }
}
