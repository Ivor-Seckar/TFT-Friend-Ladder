package net.getenjoyment.ivi;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Methods {

    //vzame vse presledke in jih spremeni v %20  (poleg ostalih stvari)
    public static String jsonify(String username) {
        return URLEncoder.encode(username, StandardCharsets.UTF_8).replace("+", "%20");
    }

    //izpis seznama
    public static void izpisiSeznam(String[] seznam) {
        for (int i = 0; i < seznam.length; i++) {
            System.out.println(seznam[i]);
        }
    }
}


