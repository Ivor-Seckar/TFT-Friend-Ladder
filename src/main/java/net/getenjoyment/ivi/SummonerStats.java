package net.getenjoyment.ivi;

import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummonerStats {
    private Summoner igralec;
    private ArrayList<TFT_Match> matchHistory;
    private ArrayList<TFT_Match> normalMatchHistory;
    private ArrayList<TFT_Match> rankedMatchHistory;
    private ArrayList<TFT_Match> doubleUpMatchHistory;
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

    public ArrayList<TFT_Match> getMatchHistory() {
        return matchHistory;
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

    public ArrayList<TFT_Match> getNormalMatchHistory() {
        return normalMatchHistory;
    }

    public ArrayList<TFT_Match> getRankedMatchHistory() {
        return rankedMatchHistory;
    }

    public ArrayList<TFT_Match> getDoubleUpMatchHistory() {
        return doubleUpMatchHistory;
    }

    // additional methods
    public void setSummonerMatchHistory(int setNumber, Integer gameCount) {
        ArrayList<TFT_Match> matchHistory = new ArrayList<>();
        ArrayList<TFT_Match> normalMatchHistory = new ArrayList<>();
        ArrayList<TFT_Match> rankedMatchHistory = new ArrayList<>();
        ArrayList<TFT_Match> doubleUpMatchHistory = new ArrayList<>();

        int start = 0;
        boolean continueFetching = true;

        while(continueFetching) {
            String[] matchHistoryVStringu = null;

            try {
                matchHistoryVStringu = API_Calls.getMatchHistory(igralec, 100, start);

                if (matchHistoryVStringu.length == 0) {  // Could be end of history or temporary API empty response
                    Thread.sleep(10_000);
                    continue;
                }

                int processedMatches = 0;

                for (int i = 0; i < matchHistoryVStringu.length; i++) {
                    TFT_Match dejanskaIgra = API_Calls.getMatchData(matchHistoryVStringu[i]);
                    int failCount = 0;

                    while ((dejanskaIgra == null || dejanskaIgra.getInfo() == null) && failCount < 6) {
                        System.out.println("Retrying match " + matchHistoryVStringu[i] + " because info is null.");
                        Thread.sleep(15_000);
                        dejanskaIgra = API_Calls.getMatchData(matchHistoryVStringu[i]);
                        failCount++;
                    }
                    if (dejanskaIgra == null || dejanskaIgra.getInfo() == null){
                        System.out.println("Skipping match " + matchHistoryVStringu[i] + " because info is null.");
                        continue;
                    }

                    if (dejanskaIgra.getInfo().getTft_set_number() != setNumber || (gameCount != null && matchHistory.size() >= gameCount)) {
                        continueFetching = false;
                        break;
                    }

                    int queueId = dejanskaIgra.getInfo().getQueue_id();

                    if (queueId == 1100) { // ranked
                        rankedMatchHistory.add(dejanskaIgra);
                    } else if (queueId == 1090) { // normal
                        normalMatchHistory.add(dejanskaIgra);
                    } else if (queueId == 1160) { // double up
                        doubleUpMatchHistory.add(dejanskaIgra);
                    } else {
                        System.out.println("Unknown queue: " + queueId + " for game " + matchHistoryVStringu[i]);
                        continue;
                    }

                    matchHistory.add(dejanskaIgra);
                    processedMatches++;

                    if (i > 0 && i % 85 == 0) {  // when i is divisible by 90, sleep for 2 minutes. (so that i don't exceed the API rate limits - 20 requests/second or 100 requests/2 minutes)
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

        this.matchHistory = matchHistory;
        this.normalMatchHistory = normalMatchHistory;
        this.rankedMatchHistory = rankedMatchHistory;
        this.doubleUpMatchHistory = doubleUpMatchHistory;
    }

    public void setSummonerMatchHistory(int setNumber) {
        setSummonerMatchHistory(setNumber, null);
    }

    public void setWinrate (ArrayList<TFT_Match> myGames) {
        int winCount = 0;

        for(TFT_Match igra : myGames) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i]) && igra.getInfo().getParticipants()[i].isWin()) {
                    winCount++;
                }
            }
        }

        System.out.println(winCount);
        System.out.println(myGames.size());

        this.winrate = (double) winCount / myGames.size();
    }

    public void setGold_left (ArrayList<TFT_Match> myGames) {
        int goldLeft = 0;

        for(TFT_Match igra : myGames) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i])) {
                    int goldLeftInThisGame = igra.getInfo().getParticipants()[i].getGold_left();
                    goldLeft += goldLeftInThisGame;
                }
            }
        }
        this.gold_left = (int) Math.round((double)goldLeft/ myGames.size());
    }

    public void setAverageLast_Round (ArrayList<TFT_Match> myGames) {
        double LastRoundTotal = 0;

        for(TFT_Match igra : myGames) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i])) {
                    double lastRound  = igra.getInfo().getParticipants()[i].getLast_round();
                    LastRoundTotal += lastRound;
                }
            }
        }
        this.last_round = LastRoundTotal/ myGames.size();
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

    public void setAverageTotal_damage_to_players (ArrayList<TFT_Match> myGames) {
        int totalDamage = 0;

        for(TFT_Match igra : myGames) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i])) {
                    int damageThisGame = igra.getInfo().getParticipants()[i].getTotal_damage_to_players();
                    totalDamage += damageThisGame;
                }
            }
        }
        this.total_damage_to_players = (int) Math.round((double)totalDamage/ myGames.size());
    }

    public void setAveragePlacement (ArrayList<TFT_Match> myGames) {
        float totalPlacement = 0;

        for(TFT_Match igra : myGames) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i])) {
                    int placementThisGame = igra.getInfo().getParticipants()[i].getPlacement();
                    totalPlacement += placementThisGame;
                }
            }
        }
        this.placement = totalPlacement / myGames.size();
    }

    public void setAveragePlayersEliminated (ArrayList<TFT_Match> myGames) {
        int totalEliminations = 0;

        for(TFT_Match igra : myGames) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i])) {
                    int eliminationsThisGame = igra.getInfo().getParticipants()[i].getPlayers_eliminated();
                    totalEliminations += eliminationsThisGame;
                }
            }
        }
        this.players_eliminated = totalEliminations / myGames.size();
    }

    public void setFavourite_trait(ArrayList<TFT_Match> myGames) {
        HashMap<String, Integer> mojiTraiti = new HashMap<>();

        for(TFT_Match igra : myGames) {

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

    public String returnFavourite_trait(ArrayList<TFT_Match> myGames) {
        return "Favourite trait: " + favourite_trait
                + "\nThat trait was played \033[1m" + favourite_trait_times_played
                + "\033[0m times in " + myGames.size() + "\sgames."
                + "\nFavourite trait (silver plus): " + favourite_silver_plus_trait
                + "\nThat trait was played \033[1m" + favourite_silver_plus_trait_times_played
                + "\033[0m times in " + myGames.size() + "\sgames.";
    }

    public void setFavourite_unit(ArrayList<TFT_Match> myGames) {
        HashMap<String, Integer> mojiUniti = new HashMap<>();

        for(TFT_Match igra : myGames) {

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

//    public void setProgress(ArrayList<TFT_Match> myGames) {
//        if(this.winrate == 0 || this.favourite_unit == null) {
//            System.out.println("Cannot calculate progress due to missing data.");
//            return;
//        }
//
//        ArrayList<TFT_Match> progressList = new ArrayList<>();
//
//        if(myGames.size() >= 20) {
//            progressList = (ArrayList<TFT_Match>) myGames.subList(0, 20);  // BE CAREFUL - sublists are connected to the main list and any changes will affect that main list too.
//        } else {
//            System.out.println("There isn’t sufficient recent match data to accurately assess progress.");
//        }
//
//
//    }

    public void setAndPrintEveryStat(String queue) { // all, normal, ranked, doubleUp
        ArrayList<TFT_Match> myGames;

        switch (queue) {
            case "all" -> myGames = this.matchHistory;
            case "normal" -> myGames = this.normalMatchHistory;
            case "ranked" -> myGames = this.rankedMatchHistory;
            case "doubleUp" -> myGames = this.doubleUpMatchHistory;
            default -> throw new IllegalArgumentException("Invalid queue type entry.");
        }
        this.setWinrate(myGames);
        this.setGold_left(myGames);
        this.setAverageLast_Round(myGames);
        this.setAverageTotal_damage_to_players(myGames);
        this.setAveragePlacement(myGames);
        this.setAveragePlayersEliminated(myGames);
        this.setFavourite_trait(myGames);
        this.setFavourite_unit(myGames);

        System.out.println("Successfully calculated all stats for " + queue + " queue.");


        System.out.println("Top 4 rate: " + this.getWinrate()*100 + "%");
        System.out.println("Average gold left: " + this.getGold_left());
        System.out.println(this.returnLastRound());
        System.out.println("Average total damage to players: " + this.getTotal_damage_to_players());
        System.out.println("Average placement: " + Math.round(this.getPlacement() * 100f) / 100f); //pomnožimo s 100, potem zaokrožimo na najbližje celo stevilo in potem spet delimo s 100. 3.1432424 --> 314.32424 --> 314 --> 3.14
        System.out.println("Average players eliminated: " + this.getPlayers_eliminated());
        System.out.println(this.returnFavourite_trait(myGames));
        System.out.println("Favourite unit: " + this.getFavourite_unit() + "\nThat unit was played: \033[1m" + this.getFavourite_unit_times_played() + "\033[0m times in " + myGames.size() + "\sgames.");
    }

}
