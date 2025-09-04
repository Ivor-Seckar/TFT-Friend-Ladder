package net.getenjoyment.ivi;

import com.google.gson.Gson;

import java.util.Arrays;

public class Main {
    public static void main (String[] args) throws Exception {

        //povemo gameName in tagLine da dobimo nazaj puuid
//        Summoner mojSummoner = new Summoner("I let her go bro", "BURAZ");
//        Summoner mojSummoner = new Summoner("relearn", "9999");
        Summoner mojSummoner = new Summoner("marjanca", "weša");



        //naredimo dejanski API call in pridobimo nazaj puuid
        String getPuuidResponse = API_Calls.getPUUID(mojSummoner);
        System.out.println(getPuuidResponse);

        //iz json odgovora dodamo k mojemu summonerju njegove podatke
        Gson gson = new Gson();
        mojSummoner = gson.fromJson(getPuuidResponse, Summoner.class);  //(kaj pretvorimo iz jsona, v kateri class)

        System.out.println(mojSummoner.getGameName());
        System.out.println(mojSummoner.getTagLine());
        System.out.println(mojSummoner.getPuuid());

        SummonerStats statiMojegaIgralca = new SummonerStats(mojSummoner);
        statiMojegaIgralca.setSummonerMatchHistory();
        statiMojegaIgralca.setWinrate();
        statiMojegaIgralca.setGold_left();
        statiMojegaIgralca.setAverageLast_Round();

        mojSummoner.setStats(statiMojegaIgralca);

        System.out.println(statiMojegaIgralca.getWinrate());
        System.out.println(statiMojegaIgralca.getGold_left());
        System.out.println(statiMojegaIgralca.returnLastRound());

        TFT_Match zadnjaIgraMojegaIgralca = statiMojegaIgralca.getMatchHistoryMojegaIgralca()[0];   // 0 = latest igra, zadnji element v arrayu = najstarejsa igra
        zadnjaIgraMojegaIgralca.getInfo().izpisiParticipants();
        System.out.println(zadnjaIgraMojegaIgralca.getInfo().getGame_length());
        System.out.println(zadnjaIgraMojegaIgralca.getInfo().getTft_game_type());
        System.out.println(Arrays.toString(zadnjaIgraMojegaIgralca.getMetadata().getParticipants()));
//        System.out.println(zadnjaIgraMojegaIgralca.getInfo().getGameCreation());

    }
}


