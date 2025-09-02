package net.getenjoyment.ivi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Methods {

    // izpis seznama
    public static void izpisiSeznam(String[] seznam) {
        for (String singleMatch : seznam) {
            System.out.println(singleMatch);
        }
    }

    // za spremembo datuma v unix obliko
    public static long dateToUnix() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        long unixDate;

        int day = 1;
        int month = 8;
        int year = 2025;


        LocalDate date = LocalDate.of(year, month, day);
        Instant instant = date.atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();
        unixDate = instant.getEpochSecond();
        System.out.println(unixDate);

//        try {
//            System.out.println("Input the day: ");
//            int day = Integer.parseInt(br.readLine());
//
//            System.out.println("Input the month: ");
//            int month = Integer.parseInt(br.readLine());
//
//            System.out.println("Input the year: ");
//            int year = Integer.parseInt(br.readLine());
//
//        TUKAJ VSTAVI VSE OD UNIXDATE NAPREJ CE ZELIS USER INPUT DATUM ----------------------------------------
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        return unixDate;
    }

    // vzame seznam iger v Stringu in pridobi za vsako data in vrne seznam Objektov TFT_Match
    // TODO: tole metodo treba zbrisat in v api call nardit da mi vrne list matchev in ne string
    public static TFT_Match[] seznamTftMatchevVClassu(String[] seznamMatchStringov) {

        TFT_Match[] seznamDejanskihIger = new TFT_Match[seznamMatchStringov.length];

        for (int i = 0; i < seznamMatchStringov.length; i++) {
            TFT_Match dejanskaIgra = API_Calls.getMatchData(seznamMatchStringov[i]);
            seznamDejanskihIger[i] = dejanskaIgra;
        }
        return seznamDejanskihIger;
    }
}


