package net.getenjoyment.ivi;

public class SummonerStats {
    private Summoner igralec;
    private TFT_Match[] matchHistoryMojegaIgralca;
    private double winrate;
    private int gold_left;
    private float last_round;
    private int total_damage_to_players;
    private float placement;
    private int players_eliminated;

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

}
