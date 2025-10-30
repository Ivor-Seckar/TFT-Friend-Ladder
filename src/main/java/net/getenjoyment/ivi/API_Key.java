package net.getenjoyment.ivi;

public class API_Key {

    // v edit run configuration ali v cmd(potem tudi pozenemo v cmd) moramo nastimati local variable:
    // set RIOT_API_KEY=(moj dejanski api key)
    private static final String apiKey = System.getenv("RIOT_API_KEY");

    public static String getApiKey() {
        if(apiKey == null) {
            throw new RuntimeException("API KEY ne deluje pravilno.");
        }
        return apiKey;
    }
}
