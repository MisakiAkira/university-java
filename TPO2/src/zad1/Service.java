/**
 * @author Daniliuk Andrei S24610
 */

package zad1;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Service {

    private final String country;

    Service(String country) {
        this.country = country;
    }

    String getWeather(String city) {
        try {
            HttpURLConnection cityConn =
                    (HttpURLConnection) new URL("http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=5&appid=").openConnection();
            BufferedReader cityBr = new BufferedReader(new InputStreamReader(cityConn.getInputStream()));
            String cityInfo = cityBr.readLine();
            double lat = Double.parseDouble(cityInfo.substring(cityInfo.indexOf("lat"), cityInfo.indexOf(',', cityInfo.indexOf("lat"))).split(":")[1]),
                    lon = Double.parseDouble(cityInfo.substring(cityInfo.indexOf("lon"), cityInfo.indexOf(',', cityInfo.indexOf("lon"))).split(":")[1]);

            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + lat +
                    "&lon=" + lon + "&appid=&units=metric").openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            return br.readLine().replace(',', '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    double getRateFor(String currencyCode) {
        try {
            Map<String, String> countries = new HashMap<>();
            for (String iso : Locale.getISOCountries()) {
                Locale l = new Locale("", iso);
                countries.put(l.getDisplayCountry(), iso);
            }

            HttpURLConnection request = (HttpURLConnection) new URL("https://api.exchangerate.host/convert?from=" +
                    new Locale("", countries.get(country)).getISO3Country() + "&to=" + currencyCode).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

            String line = br.readLine();
            return Double.parseDouble(line.substring(line.indexOf("result"), line.indexOf('}', line.indexOf("result"))).split(":")[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    double getNBPRate() {
        try {
            Map<String, String> countries = new HashMap<>();
            for (String iso : Locale.getISOCountries()) {
                Locale l = new Locale("", iso);
                countries.put(l.getDisplayCountry(), iso);
            }

            Currency.getInstance(new Locale("", countries.get(country))).getCurrencyCode();

            HttpURLConnection request = (HttpURLConnection) new URL("https://static.nbp.pl/dane/kursy/xml/b014z230405.xml").openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

            boolean found = false;
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains(Currency.getInstance(new Locale("", countries.get(country))).getCurrencyCode())) {
                    found = true;
                    break;
                }
            }

            if (found) {
                line = br.readLine();
                return NumberFormat.getInstance(Locale.FRANCE).parse(line.substring(line.indexOf('>') + 1, line.lastIndexOf('<'))).doubleValue();
            }

            HttpURLConnection request1 = (HttpURLConnection) new URL("https://static.nbp.pl/dane/kursy/xml/a069z230407.xml").openConnection();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(request1.getInputStream()));

            while ((line = br1.readLine()) != null) {
                if (line.contains(Currency.getInstance(new Locale("", countries.get(country))).getCurrencyCode())) {
                    found = true;
                    break;
                }
            }

            if (found) {
                line = br1.readLine();
                return NumberFormat.getInstance(Locale.FRANCE).parse(line.substring(line.indexOf('>') + 1, line.lastIndexOf('<'))).doubleValue();
            }

            return -1;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
