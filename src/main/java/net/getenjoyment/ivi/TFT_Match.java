package net.getenjoyment.ivi;

public class TFT_Match {
    private MatchMetadata metadata;
    private MatchInfo info;

    public TFT_Match() {

    }

    public MatchMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(MatchMetadata metadata) {
        this.metadata = metadata;
    }

    public MatchInfo getInfo() {
        return info;
    }

    public void setInfo(MatchInfo info) {
        this.info = info;
    }
}
