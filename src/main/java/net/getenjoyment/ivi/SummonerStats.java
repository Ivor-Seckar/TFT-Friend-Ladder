package net.getenjoyment.ivi;

public class SummonerStats {
    private Summoner igralec;
    private TFT_Match[] matchHistoryMojegaIgralca;
    private double winrate;

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

    // additional methods
    public void setSummonerMatchHistory() {
        String[] matchHistoryMojegaIgralcaVStringu = API_Calls.getMatchHistory(igralec);
        this.matchHistoryMojegaIgralca = Methods.seznamTftMatchevVClassu(matchHistoryMojegaIgralcaVStringu);
    }

    public void setWinrate () {
        String imeInTagIgralca = igralec.getGameName() + igralec.getTagLine();
        System.out.println(imeInTagIgralca);

        int count = 0;
        int winCount = 0;

        for(TFT_Match igra : matchHistoryMojegaIgralca) {

            String[] playerPuuids = igra.getMetadata().getParticipants();
            for (int i = 0; i < playerPuuids.length; i++) {
                if(igralec.getPuuid().equals(playerPuuids[i]) && igra.getInfo().getParticipants()[i].isWin()) {
                    winCount++;
                }
            }
            count++;
        }

        System.out.println(count);
        System.out.println(winCount);

        this.winrate = (double) winCount /count;
    }
}
