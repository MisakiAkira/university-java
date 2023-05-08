package zad2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ProgLang {

    String path;

    ProgLang(String s) {
        path = s;
    }

    Map<String, List<String>> getLangsMap() throws IOException {
        Map<String, List<String>> langProg = new LinkedHashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null){
            String[] words = line.split("\t");
            List<String> prog = new ArrayList<>(Arrays.asList(words).subList(1, words.length));
            langProg.put(words[0], prog.stream().distinct().collect(Collectors.toList()));
        }
        br.close();
        return langProg;
    }

    Map<String, List<String>> getProgsMap() throws IOException {
        Map<String, List<String>> progLangs = new LinkedHashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null){
            String[] words = line.split("\t");
            for (int i = 1; i < words.length; i ++){
                List<String> lang;
                if (progLangs.containsKey(words[i])){
                    lang = new ArrayList<>(progLangs.get(words[i]));
                } else {
                    lang = new ArrayList<>();
                }
                lang.add(words[0]);
                progLangs.put(words[i], lang.stream().distinct().collect(Collectors.toList()));
            }
        }
        return progLangs;
    }

    Map<String, List<String>> getLangsMapSortedByNumOfProgs() throws IOException {
        List<Map.Entry<String, List<String>>> entries = new ArrayList<>(getLangsMap().entrySet());
        entries.sort((o1, o2) -> o2.getValue().size() - o1.getValue().size());
        Map<String, List<String>> langProg = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : entries)
            langProg.put(entry.getKey(), entry.getValue());
        return langProg;
    }

    Map<String, List<String>> getProgsMapSortedByNumOfLangs() throws IOException {
        List<Map.Entry<String, List<String>>> entries = new ArrayList<>(getProgsMap().entrySet());
        entries.sort((o1, o2) -> {
            if(o2.getValue().size() - o1.getValue().size() == 0)
                return o1.getKey().compareTo(o2.getKey());
            else
                return o2.getValue().size() - o1.getValue().size();
        });
        Map<String, List<String>> progLangs = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : entries)
            progLangs.put(entry.getKey(), entry.getValue());
        return progLangs;
    }

    Map<String, List<String>> getProgsMapForNumOfLangsGreaterThan(int numOfLang) throws IOException {
        Map<String, List<String>> fin = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : getProgsMap().entrySet()){
            if (entry.getValue().size() > numOfLang)
                fin.put(entry.getKey(), entry.getValue());
        }
        return fin;
    }

}
