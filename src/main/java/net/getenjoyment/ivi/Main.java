package net.getenjoyment.ivi;

import com.google.gson.Gson;

import java.util.Arrays;

public class Main {
    public static void main (String[] args) throws Exception {

        //povemo gameName in tagLine da dobimo nazaj puuid
//        Summoner mojSummoner = new Summoner("I let her go bro", "BURAZ");
        Summoner mojSummoner = new Summoner("relearn", "9999");
//        Summoner mojSummoner = new Summoner("marjanca", "weša");

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
        statiMojegaIgralca.setAverageTotal_damage_to_players();
        statiMojegaIgralca.setAveragePlacement();
        statiMojegaIgralca.setAveragePlayersEliminated();
        statiMojegaIgralca.setFavourite_trait();
        statiMojegaIgralca.setFavourite_unit();

        mojSummoner.setStats(statiMojegaIgralca);

        System.out.println("Top 4 rate: " + statiMojegaIgralca.getWinrate()*100 + "%");
        System.out.println("Average gold left: " + statiMojegaIgralca.getGold_left());
        System.out.println(statiMojegaIgralca.returnLastRound());
        System.out.println("Average total damage to players: " + statiMojegaIgralca.getTotal_damage_to_players());
        System.out.println("Average placement: " + Math.round(statiMojegaIgralca.getPlacement() * 100f) / 100f); //pomnožimo s 100, potem zaokrožimo na najbližje celo stevilo in potem spet delimo s 100. 3.1432424 --> 314.32424 --> 314 --> 3.14
        System.out.println("Average players eliminated: " + statiMojegaIgralca.getPlayers_eliminated());
        System.out.println(statiMojegaIgralca.returnFavourite_trait());
        System.out.println("Favourite unit: " + statiMojegaIgralca.getFavourite_unit() + "\nThat unit was played: \033[1m" + statiMojegaIgralca.getFavourite_unit_times_played() + "\033[0m times in " + statiMojegaIgralca.getMatchHistoryMojegaIgralca().length + "\sgames.");

        TFT_Match zadnjaIgraMojegaIgralca = statiMojegaIgralca.getMatchHistoryMojegaIgralca()[0];   // 0 = latest igra, zadnji element v arrayu = najstarejsa igra
//        zadnjaIgraMojegaIgralca.getInfo().izpisiParticipants();
//        System.out.println(zadnjaIgraMojegaIgralca.getInfo().getGame_length());
        System.out.println("Game type: " + zadnjaIgraMojegaIgralca.getInfo().getTft_game_type());
//        System.out.println(Arrays.toString(zadnjaIgraMojegaIgralca.getMetadata().getParticipants()));
//        System.out.println(zadnjaIgraMojegaIgralca.getInfo().getGameCreation());

    }
}


