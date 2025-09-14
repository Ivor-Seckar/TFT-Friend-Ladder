package net.getenjoyment.ivi;

import java.util.HashMap;
import java.util.Map;

public class SummonerStats {
    private Summoner igralec;
    private TFT_Match[] matchHistoryMojegaIgralca;
    private double winrate;
    private int gold_left;
    private float last_round;
    private int total_damage_to_players;
    private float placement;
    private int players_eliminated;
    private String favourite_trait;
    private int favourite_trait_times_played;
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

    public TFT_Match[] getMatchHistoryMojegaIgralca() {
        return matchHistoryMojegaIgralca;
    }

    public void setMatchHistoryMojegaIgralca(TFT_Match[] matchHistoryMojegaIgralca) {
        this.matchHistoryMojegaIgralca = matchHistoryMojegaIgralca;
    }

    public double getWinrate() {
        return winrate;
    }

    public int getGold_left() {
        return gold_left;
    }

    public float getLast_round() {
        return last_round;
    }

    public int getTotal_damage_to_players() {
        return total_damage_to_players;
    }

    public float getPlacement() {
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

    // additional methods
    public void setSummonerMatchHistory() {
        String[] matchHistoryMojegaIgralcaVStringu = API_Calls.getMatchHistory(igralec);
        this.matchHistoryMojegaIgralca = Methods.seznamTftMatchevVClassu(matchHistoryMojegaIgralcaVStringu);
    }

    // TODO: delete the COUNT after done testing. (unnecessary because we already have matchHistory.length)
    public void setWinrate () {
//        int count = 0;
        int winCount = 0;

        for(TFT_Match igra : matchHistoryMojegaIgralca) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i]) && igra.getInfo().getParticipants()[i].isWin()) {
                    winCount++;
                }
            }
//            count++;
        }

//        System.out.println(count);
        System.out.println(winCount);
        System.out.println(matchHistoryMojegaIgralca.length);

        this.winrate = (double) winCount / matchHistoryMojegaIgralca.length;
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
        this.gold_left = goldLeft/ matchHistoryMojegaIgralca.length;
    }

    public void setAverageLast_Round () {
        float LastRoundTotal = 0;

        for(TFT_Match igra : matchHistoryMojegaIgralca) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i])) {
                    float lastRound  = igra.getInfo().getParticipants()[i].getLast_round();
                    LastRoundTotal += lastRound;
                }
            }
        }
        this.last_round = LastRoundTotal/ matchHistoryMojegaIgralca.length;
    }

    public String returnLastRound() {
        return "Average total rounds played: " + (int)last_round + "\nStage: " + (int)last_round/6 + ", Round: " + (int)(last_round%6);
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
        this.total_damage_to_players = totalDamage/ matchHistoryMojegaIgralca.length;
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
        this.placement = totalPlacement / matchHistoryMojegaIgralca.length;
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
        this.players_eliminated = totalEliminations / matchHistoryMojegaIgralca.length;
    }

    // TODO: add support for trait style and trait tier
    public void setFavourite_trait() {
        HashMap<String, Integer> mojiTraiti = new HashMap<>();

        for(TFT_Match igra : matchHistoryMojegaIgralca) {

            MatchParticipant[] mojiIgralci = igra.getInfo().getParticipants();
            for(MatchParticipant participant : mojiIgralci) {
                if(igralec.getPuuid().equals(participant.getPuuid())) {
                    Trait[] traits = participant.getTraits();

                    for(Trait singleTrait : traits) {
                        if(mojiTraiti.containsKey(singleTrait.getName())) {
                            mojiTraiti.put(singleTrait.getName(), mojiTraiti.get(singleTrait.getName()) + 1);
                        } else {
                            mojiTraiti.put(singleTrait.getName(), 1);
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
    }

    public String returnFavourite_trait() {
        return "Favourite trait: " + favourite_trait + "\nThat trait was played \033[1m" + favourite_trait_times_played + "\033[0m times in" + matchHistoryMojegaIgralca.length + " games.";
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

//    public void progress

}
