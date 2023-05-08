/**
 *
 *  @author Daniliuk Andrei S24610
 *
 */

package zad2;


import zad1.InputConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/*<-- niezbędne import */
public class Main {
  public static void main(String[] args) throws IOException {
    /*<--
     *  definicja operacji w postaci lambda-wyrażeń:
     *  - flines - zwraca listę wierszy z pliku tekstowego
     *  - join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)
     *  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
     *  - sum - zwraca sumę elmentów listy liczb całkowitych
     */


    Function<String, List<String>> flines = f1 -> {
      try {
        return apply(f1);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    };

    Function<List<String>, String> join = f -> {
      StringBuilder sb = new StringBuilder();
      for (String s : f){
        sb.append(s);
      }
      return String.valueOf(sb);
    };

    Function<String, List<Integer>> collectInts = f -> {
      List<Integer> list = new ArrayList<Integer>();
      StringBuilder sb = new StringBuilder();
      boolean check = false;
      for (int i = 0; i < f.length(); i++){
        if (Character.isDigit(f.charAt(i))){
          sb.append(f.charAt(i));
          check = true;
        } else if(check) {
          list.add(Integer.parseInt(String.valueOf(sb)));
          check = false;
          sb = new StringBuilder();
        }
      }
      return list;
    };

    Function<List<Integer>, Integer> sum = f -> {
      int summ = 0;
      for (int i : f){
        summ += i;
      }
      return summ;
    };

    String fname = System.getProperty("user.home") + "/LamComFile.txt";
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
    String text = fileConv.convertBy(flines, join);
    List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
    Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

    System.out.println(lines);
    System.out.println(text);
    System.out.println(ints);
    System.out.println(sumints);

    List<String> arglist = Arrays.asList(args);
    InputConverter<List<String>> slistConv = new InputConverter<>(arglist);
    sumints = slistConv.convertBy(join, collectInts, sum);
    System.out.println(sumints);

    // Zadania badawcze:
    // Operacja flines zawiera odczyt pliku, zatem może powstac wyjątek IOException
    // Wymagane jest, aby tę operację zdefiniowac jako lambda-wyrażenie
    // Ale z lambda wyrażeń nie możemy przekazywac obsługi wyjatków do otaczającego bloku
    // I wobec tego musimy pisać w definicji flines try { } catch { }
    // Jak spowodować, aby nie było to konieczne i w przypadku powstania wyjątku IOException
    // zadziałała klauzula throws metody main
  }

  private static List<String> apply(String f) throws IOException {
    List<String> list = new ArrayList<String>();
    File file = new File(f);
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line = "";
    while (br.ready()) {
      line = br.readLine();
      list.add(line);
    }
    return list;
  }
}
