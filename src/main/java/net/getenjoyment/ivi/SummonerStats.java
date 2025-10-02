package net.getenjoyment.ivi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SummonerStats {
    private Summoner igralec;
    private ArrayList<TFT_Match> matchHistoryMojegaIgralca;
    private double winrate;
    private int gold_left;
    private double last_round;
    private int total_damage_to_players;
    private double placement;
    private int players_eliminated;
    private String favourite_trait;
    private String favourite_silver_plus_trait;
    private int favourite_trait_times_played;
    private int favourite_silver_plus_trait_times_played;
    private String favourite_unit;
    private int favourite_unit_times_played;

    public SummonerStats(Summoner igralec) {
        this.igralec = igralec;
    }

    public Summoner getIgralec() {
        return igralec;
    }

    public void setIgralec(Summoner igralec) {
        this.igralec = igralec;
    }

    public ArrayList<TFT_Match> getMatchHistoryMojegaIgralca() {
        return matchHistoryMojegaIgralca;
    }

    public double getWinrate() {
        return winrate;
    }

    public int getGold_left() {
        return gold_left;
    }

    public double getLast_round() {
        return last_round;
    }

    public int getTotal_damage_to_players() {
        return total_damage_to_players;
    }

    public double getPlacement() {
        return placement;
    }

    public int getPlayers_eliminated() {
        return players_eliminated;
    }

    public String getFavourite_trait() {
        return favourite_trait;
    }

    public int getFavourite_trait_times_played() {
        return favourite_trait_times_played;
    }

    public String getFavourite_unit() {
        return favourite_unit;
    }

    public int getFavourite_unit_times_played() {
        return favourite_unit_times_played;
    }

    public String getFavourite_silver_plus_trait() {
        return favourite_silver_plus_trait;
    }

    public int getFavourite_silver_plus_trait_times_played() {
        return favourite_silver_plus_trait_times_played;
    }

    // additional methods
    public void setSummonerMatchHistory(int setNumber, Integer gameCount) {
        ArrayList<TFT_Match> matchHistoryMojegaIgralca = new ArrayList<>();

        int start = 0;
        boolean continueFetching = true;

        while(continueFetching) {
            String[] matchHistoryMojegaIgralcaVStringu = null;

            try {
                matchHistoryMojegaIgralcaVStringu = API_Calls.getMatchHistory(igralec, 100, start);

                if (matchHistoryMojegaIgralcaVStringu.length == 0) {  // Could be end of history or temporary API empty response
                    Thread.sleep(10_000);
                    continue;
                }

                int processedMatches = 0;

                for (int i = 0; i < matchHistoryMojegaIgralcaVStringu.length; i++) {
                    TFT_Match dejanskaIgra = API_Calls.getMatchData(matchHistoryMojegaIgralcaVStringu[i]);
                    int failCount = 0;

                    while ((dejanskaIgra == null || dejanskaIgra.getInfo() == null) && failCount < 6) {
                        System.out.println("Retrying match " + matchHistoryMojegaIgralcaVStringu[i] + " because info is null.");
                        Thread.sleep(15_000);
                        dejanskaIgra = API_Calls.getMatchData(matchHistoryMojegaIgralcaVStringu[i]);
                        failCount++;
                    }
                    if (dejanskaIgra == null || dejanskaIgra.getInfo() == null){
                        System.out.println("Skipping match " + matchHistoryMojegaIgralcaVStringu[i] + " because info is null.");
                        continue;
                    }

                    if (dejanskaIgra.getInfo().getTft_set_number() != setNumber || (gameCount != null && matchHistoryMojegaIgralca.size() >= gameCount)) {
                        continueFetching = false;
                        break;
                    }
                    matchHistoryMojegaIgralca.add(dejanskaIgra);
                    processedMatches++;

                    if (i > 0 && i % 90 == 0) {  // when i is divisible by 90, sleep for 2 minutes. (so that i don't exceed the API rate limits - 20 requests/second or 100 requests/2 minutes)
                        System.out.println("...Pausing 2 minutes to respect API rate limits...");
                        Thread.sleep(120_000);
                    }
                }
                start += processedMatches;

            } catch (Exception e) {
                System.out.println("Rate limit hit. Waiting 2 minutes before retrying...");
                try {
                    Thread.sleep(120_000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // preserve interrupt status
                    throw new RuntimeException(ie);
                }
            }
        }

        this.matchHistoryMojegaIgralca = matchHistoryMojegaIgralca;
    }

    public void setSummonerMatchHistory(int setNumber) {
        setSummonerMatchHistory(setNumber, null);
    }

    public void setWinrate () {
        int winCount = 0;

        for(TFT_Match igra : matchHistoryMojegaIgralca) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i]) && igra.getInfo().getParticipants()[i].isWin()) {
                    winCount++;
                }
            }
        }

        System.out.println(winCount);
        System.out.println(matchHistoryMojegaIgralca.size());

        this.winrate = (double) winCount / matchHistoryMojegaIgralca.size();
    }

    public void setGold_left () {
        int goldLeft = 0;

        for(TFT_Match igra : matchHistoryMojegaIgralca) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i])) {
                    int goldLeftInThisGame = igra.getInfo().getParticipants()[i].getGold_left();
                    goldLeft += goldLeftInThisGame;
                }
            }
        }
        this.gold_left = (int) Math.round((double)goldLeft/ matchHistoryMojegaIgralca.size());
    }

    public void setAverageLast_Round () {
        double LastRoundTotal = 0;

        for(TFT_Match igra : matchHistoryMojegaIgralca) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i])) {
                    double lastRound  = igra.getInfo().getParticipants()[i].getLast_round();
                    LastRoundTotal += lastRound;
                }
            }
        }
        this.last_round = LastRoundTotal/ matchHistoryMojegaIgralca.size();
    }

    public String returnLastRound() {
        int avgRound = (int) Math.floor(last_round); // math.floor for it to round down, Math.round for it to round accurately (even if upwards)
        int stage, round;

        if (avgRound <= 3) {
            // Still in stage 1
            stage = 1;
            round = avgRound;
        } else {
            // Subtract the 3 stage-1 rounds
            int afterCreeps = avgRound - 3;

            // Each stage after 1 has 7 rounds
            stage = 2 + (afterCreeps - 1) / 7;
            round = ((afterCreeps - 1) % 7) + 1;
        }

        return "Average total rounds played: " + (int)last_round
                + "\nStage: " + stage
                + ", Round: " + round;
    }

    public void setAverageTotal_damage_to_players () {
        int totalDamage = 0;

        for(TFT_Match igra : matchHistoryMojegaIgralca) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i])) {
                    int damageThisGame = igra.getInfo().getParticipants()[i].getTotal_damage_to_players();
                    totalDamage += damageThisGame;
                }
            }
        }
        this.total_damage_to_players = (int) Math.round((double)totalDamage/ matchHistoryMojegaIgralca.size());
    }

    public void setAveragePlacement () {
        float totalPlacement = 0;

        for(TFT_Match igra : matchHistoryMojegaIgralca) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i])) {
                    int placementThisGame = igra.getInfo().getParticipants()[i].getPlacement();
                    totalPlacement += placementThisGame;
                }
            }
        }
        this.placement = totalPlacement / matchHistoryMojegaIgralca.size();
    }

    public void setAveragePlayersEliminated () {
        int totalEliminations = 0;

        for(TFT_Match igra : matchHistoryMojegaIgralca) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i])) {
                    int eliminationsThisGame = igra.getInfo().getParticipants()[i].getPlayers_eliminated();
                    totalEliminations += eliminationsThisGame;
                }
            }
        }
        this.players_eliminated = totalEliminations / matchHistoryMojegaIgralca.size();
    }

    public void setFavourite_trait() {
        HashMap<String, Integer> mojiTraiti = new HashMap<>();

        for(TFT_Match igra : matchHistoryMojegaIgralca) {

            MatchParticipant[] mojiIgralci = igra.getInfo().getParticipants();
            for(MatchParticipant participant : mojiIgralci) {
                if(igralec.getPuuid().equals(participant.getPuuid())) {
                    Trait[] traits = participant.getTraits();

                    for(Trait singleTrait : traits) {
                        String hashMapKey = singleTrait.getName() + ":" + singleTrait.getStyle();

                        if(singleTrait.getStyle() != 0 && singleTrait.getNum_units() > 1 && mojiTraiti.containsKey(hashMapKey)) {
                            mojiTraiti.put(hashMapKey, mojiTraiti.get(hashMapKey) + 1);
                        } else if (singleTrait.getStyle() != 0 && singleTrait.getNum_units() > 1) {
                            mojiTraiti.put(hashMapKey, 1);
                        }
                    }
                }
            }
        }

        String favTrait = null;
        int favTraitTimesPlayed = 0;

        for(Map.Entry<String, Integer> element : mojiTraiti.entrySet()) {
            if(element.getValue() > favTraitTimesPlayed) {
                favTraitTimesPlayed = element.getValue();
                favTrait = element.getKey();
            }
        }

        if(favTraitTimesPlayed != 0) {
            this.favourite_trait = favTrait;
            this.favourite_trait_times_played = favTraitTimesPlayed;
        }
//---------------------------------------------------------------------------------
        String favTraitSilverPlus = null;
        int favTraitSilverPlusTimesPlayed = 0;

        for(Map.Entry<String, Integer> element : mojiTraiti.entrySet()) {

            String traitName = element.getKey();
            int traitStyle = Integer.parseInt(traitName.substring(traitName.indexOf(":") + 1));

            if(traitStyle > 1 && element.getValue() > favTraitSilverPlusTimesPlayed) {
                favTraitSilverPlusTimesPlayed = element.getValue();
                favTraitSilverPlus = element.getKey();
            }
        }

        if(favTraitSilverPlusTimesPlayed != 0) {
            this.favourite_silver_plus_trait = favTraitSilverPlus;
            this.favourite_silver_plus_trait_times_played = favTraitSilverPlusTimesPlayed;
        }
    }

    public String returnFavourite_trait() {
        return "Favourite trait: " + favourite_trait
                + "\nThat trait was played \033[1m" + favourite_trait_times_played
                + "\033[0m times in " + matchHistoryMojegaIgralca.size() + "\sgames."
                + "\nFavourite trait (silver plus): " + favourite_silver_plus_trait
                + "\nThat trait was played \033[1m" + favourite_silver_plus_trait_times_played
                + "\033[0m times in " + matchHistoryMojegaIgralca.size() + "\sgames.";
    }

    public void setFavourite_unit() {
        HashMap<String, Integer> mojiUniti = new HashMap<>();

        for(TFT_Match igra : matchHistoryMojegaIgralca) {

            MatchParticipant[] mojiIgralci = igra.getInfo().getParticipants();
            for(MatchParticipant participant : mojiIgralci) {
                if(igralec.getPuuid().equals(participant.getPuuid())) {
                    Unit[] units = participant.getUnits();

                    for(Unit singleUnit : units) {
                        String singleUnitName = singleUnit.getCharacter_id();
                        if(mojiUniti.containsKey(singleUnitName)) {
                            mojiUniti.put(singleUnitName, mojiUniti.get(singleUnitName) + 1);
                        } else {
                            mojiUniti.put(singleUnitName, 1);
                        }
                    }
                }
            }
        }

        String favUnit = null;
        int favUnitTimesPlayed = 0;

        for(Map.Entry<String, Integer> element : mojiUniti.entrySet()) {
            if(element.getValue() > favUnitTimesPlayed) {
                favUnitTimesPlayed = element.getValue();
                favUnit = element.getKey();
            }
        }

        if(favUnitTimesPlayed != 0) {
            this.favourite_unit = favUnit;
            this.favourite_unit_times_played = favUnitTimesPlayed;
        }
    }
// TODO: separate double up, normal and ranked.

//    public void progress

}
