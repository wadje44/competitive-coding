import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.print.attribute.HashAttributeSet;

public class Solution {

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                LinkedList<Character> linkedList = new LinkedList<>();
                HashMap<String, Character> config = new HashMap<String, Character>();
                HashMap<Character, Integer> countMap = new HashMap<Character, Integer>();
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                int minCount = 9999999, maxCount = 0;
                for (int i = 0; i < strings.get(0).length(); i++) {
                    char temp = strings.get(0).charAt(i);
                    if (!countMap.containsKey(temp)) {
                        countMap.put(temp, 0);
                    }
                    countMap.put(temp, countMap.get(temp) + 1);
                    linkedList.add(temp);
                }
                for (int i = 2; i < strings.size(); i++) {
                    String[] entry = strings.get(i).split(" -> ");
                    config.put(entry[0], entry[1].charAt(0));
                }
                for (int i = 0; i < 10; i++) {
                    int n = linkedList.size();
                    for (int j = 0; j < n - 1; j++) {
                        String sub = linkedList.get(j) + "" + linkedList.get(j + 1);
                        Character newChar = config.get(sub);
                        if (newChar != null) {
                            linkedList.add(j + 1, newChar);
                            if (!countMap.containsKey(newChar)) {
                                countMap.put(newChar, 0);
                            }
                            countMap.put(newChar, countMap.get(newChar) + 1);
                            j++;
                            n++;
                        }
                    }
                }
                Character min = '0', max = '0';
                for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
                    System.out.println(entry.getKey() + " " + entry.getValue());
                    if (minCount > entry.getValue()) {
                        minCount = entry.getValue();
                        min = entry.getKey();
                    }
                    if (maxCount < entry.getValue()) {
                        maxCount = entry.getValue();
                        max = entry.getKey();
                    }
                }
                System.out.println("input " + (fi + 1) + " answer is: " + (maxCount - minCount));
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + (maxCount - minCount));
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
