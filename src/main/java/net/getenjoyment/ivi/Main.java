package net.getenjoyment.ivi;

import com.google.gson.Gson;

public class Main {
    public static void main (String[] args) throws Exception{

        //povemo gameName in tagLine da dobimo nazaj puuid
        Summoner mojSummoner = new Summoner("I let her go bro", "BURAZ");

        //naredimo dejanski API call in pridobimo nazaj puuid
        String getPuuidResponse = API_Calls.getPUUID(mojSummoner);
        System.out.println(getPuuidResponse);

        //iz json odgovora dodamo k mojemu summonerju njegove podatke
        Gson gson = new Gson();
        mojSummoner = gson.fromJson(getPuuidResponse, Summoner.class);  //(kaj pretvorimo iz jsona, v kateri class)

        System.out.println(mojSummoner.getGameName());
        System.out.println(mojSummoner.getTagLine());
        System.out.println(mojSummoner.getPuuid());

        //dejansko pozenemo API call za pridobitev match historyja in ga shranimo v String[]
        String[] matchHistoryMojegaIgralca = API_Calls.getMatchHistory(mojSummoner);

        //izpišemo seznam iger
        Methods.izpisiSeznam(matchHistoryMojegaIgralca);

    }
}


