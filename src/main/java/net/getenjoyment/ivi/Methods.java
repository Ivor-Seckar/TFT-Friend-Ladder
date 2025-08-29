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

        try {
            System.out.println("Input the day: ");
            int day = Integer.parseInt(br.readLine());

            System.out.println("Input the month: ");
            int month = Integer.parseInt(br.readLine());

            System.out.println("Input the year: ");
            int year = Integer.parseInt(br.readLine());

            LocalDate date = LocalDate.of(year, month, day);
            Instant instant = date.atStartOfDay().atZone(ZoneId.of("UTC")).toInstant();
            unixDate = instant.getEpochSecond();
            System.out.println(unixDate);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return unixDate;
    }
}


