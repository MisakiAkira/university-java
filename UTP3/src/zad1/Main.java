/**
 * @author Daniliuk Andrei S24610
 */

package src.zad1;


import java.util.*;

public class Main {

    static List<String> getPricesInPLN(List<String> destinations, double xrate) {
        return ListCreator.collectFrom(destinations)
                .when(n -> (n.contains("WAW")))
                .mapEvery(n -> {
                    int check = 0;
                    String to = "";
                    String price = "";
                    for (int i = 0; i < n.length(); i++) {
                        if (n.charAt(i) == ' ')
                            check++;
                        else
                            switch (check) {
                                case 1:
                                    to += n.charAt(i);
                                    break;
                                case 2:
                                    price += n.charAt(i);
                                    break;
                                default:
                            }

                    }
                    double finPrice = Double.parseDouble(price);
                    return "to " + to + " - price in PLN: " + ((int) (finPrice * xrate));
                });
    }

    public static void main(String[] args) {
        // Lista destynacji: port_wylotu port_przylotu cena_EUR
        List<String> dest = Arrays.asList(
                "bleble bleble 2000",
                "WAW HAV 1200",
                "xxx yyy 789",
                "WAW DPS 2000",
                "WAW HKT 1000"
        );
        double ratePLNvsEUR = 4.30;
        List<String> result = getPricesInPLN(dest, ratePLNvsEUR);
        for (String r : result) System.out.println(r);
    }
}
