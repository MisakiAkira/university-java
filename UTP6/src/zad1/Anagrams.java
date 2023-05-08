/**
 *
 *  @author Daniliuk Andrei S24610
 *
 */

package zad1;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Anagrams {

    private String path;
    private List<String> allWords = new ArrayList<>();
    private List<List<String>> anagrams;

    Anagrams(String path){
        this.path = path;
        try {
            wordsToArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        anagrams = findAnagrams();
    }

    private void wordsToArray() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine();
        allWords = Arrays.asList(line.split(" "));
    }

    private List<List<String>> findAnagrams(){
        List<List<String>> anagrams = new ArrayList<>();
        for(int i = 0; i < allWords.size(); i++){
            char[] firstWord = allWords.get(i).toCharArray();
            Arrays.sort(firstWord);
            boolean isAdded = false;
            for (List<String> ls : anagrams){
                char[] word = ls.get(0).toCharArray();
                Arrays.sort(word);
                if (Arrays.equals(firstWord, word)){
                    ls.add(allWords.get(i));
                    isAdded = true;
                    break;
                }
            }
            if (!isAdded){
                List<String> ls = new ArrayList<>();
                ls.add(allWords.get(i));
                anagrams.add(ls);
            }
        }
        return anagrams;
    }

    List<List<String>> getSortedByAnQty(){
        Collections.sort(anagrams, (o1, o2) -> {
            if (o1.size() > o2.size())
                return -1;
            else if (o2.size() > o1.size())
                return 1;
            else
                return o1.get(0).compareTo(o2.get(0));
        });
        return anagrams;
    }

    String getAnagramsFor(String word){
        boolean isExist = false;
        for (String s : allWords){
            if (word.equals(s)){
                isExist = true;
                break;
            }
        }
        if (!isExist)
            return word + ": " + null;
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        for (List<String> ls : anagrams){
            char[] nag = ls.get(0).toCharArray();
            Arrays.sort(nag);
            if (Arrays.equals(nag, chars)){
                List<String> fin = new ArrayList<>(ls);
                fin.removeIf(word::equals);
                return word + ": " + fin;
            }
        }
        return word + ": " + new ArrayList<>();
    }

}  
