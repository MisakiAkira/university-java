/**
 *
 *  @author Daniliuk Andrei S24610
 *
 */

package zad1;

public class Main {
  public static void main(String[] args) {
    Service s = new Service("Belarus");
    //String weatherJson = s.getWeather("Warsaw");
    //Double rate1 = s.getRateFor("USD");
    //Double rate2 = s.getNBPRate();
    // ...
    // część uruchamiająca GUI
    new Gui();
  }
}
