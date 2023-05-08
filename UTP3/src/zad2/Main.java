/**
 * @author Daniliuk Andrei S24610
 */

package src.zad2;


import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.toList;

public class Main {

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
        List<String> result = dest.stream().filter(n -> n.contains("WAW")).map(n -> {
            int check = 0;
            StringBuilder to = new StringBuilder();
            StringBuilder price = new StringBuilder();
            for (int i = 0; i < n.length(); i++) {
                if (n.charAt(i) == ' ')
                    check++;
                else
                    switch (check) {
                        case 1:
                            to.append(n.charAt(i));
                            break;
                        case 2:
                            price.append(n.charAt(i));
                            break;
                        default:
                    }

            }
            double finPrice = Double.parseDouble(price.toString());
            return "to " + to + " - price in PLN: " + ((int) (finPrice * ratePLNvsEUR));
        }).collect(Collectors.toList());

        for (String r : result) System.out.println(r);
    }
}
