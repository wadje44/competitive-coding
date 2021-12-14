import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.print.attribute.HashAttributeSet;
import java.math.BigInteger;

public class Solution {

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                HashMap<String, Character> config = new HashMap<String, Character>();
                HashMap<String, BigInteger> countMap = new HashMap<String, BigInteger>();
                HashMap<Character, BigInteger> characterCountMap = new HashMap<Character, BigInteger>();
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                for (int i = 0; i < strings.get(0).length() - 1; i++) {
                    if (!countMap.containsKey(strings.get(0).charAt(i) + "" + strings.get(0).charAt(i + 1))) {
                        countMap.put(strings.get(0).charAt(i) + "" + strings.get(0).charAt(i + 1), new BigInteger("0"));
                    }
                    countMap.put(strings.get(0).charAt(i) + "" + strings.get(0).charAt(i + 1),
                            countMap.get(strings.get(0).charAt(i) + "" + strings.get(0).charAt(i + 1))
                                    .add(new BigInteger("1")));
                }
                for (int i = 2; i < strings.size(); i++) {
                    String[] entry = strings.get(i).split(" -> ");
                    config.put(entry[0], entry[1].charAt(0));
                }
                for (int i = 0; i < 40; i++) {
                    HashMap<String, BigInteger> tempCountMap = new HashMap<String, BigInteger>();
                    for (Map.Entry<String, BigInteger> entry : countMap.entrySet()) {
                        Character newChar = config.get(entry.getKey());
                        if (newChar != null) {
                            String newEntry1 = entry.getKey().charAt(0) + "" + newChar;
                            String newEntry2 = newChar + "" + entry.getKey().charAt(1);
                            if (!tempCountMap.containsKey(newEntry1)) {
                                tempCountMap.put(newEntry1, new BigInteger("0"));
                            }
                            tempCountMap.put(newEntry1, tempCountMap.get(newEntry1).add(entry.getValue()));
                            if (!tempCountMap.containsKey(newEntry2)) {
                                tempCountMap.put(newEntry2, new BigInteger("0"));
                            }
                            tempCountMap.put(newEntry2, tempCountMap.get(newEntry2).add(entry.getValue()));
                        } else {
                            if (!tempCountMap.containsKey(entry.getKey())) {
                                tempCountMap.put(entry.getKey(), new BigInteger("0"));
                            }
                            tempCountMap.put(entry.getKey(), tempCountMap.get(entry.getKey()).add(entry.getValue()));
                        }
                    }
                    countMap = new HashMap<String, BigInteger>(tempCountMap);
                }
                for (Map.Entry<String, BigInteger> entry : countMap.entrySet()) {
                    if (!characterCountMap.containsKey(entry.getKey().charAt(0))) {
                        characterCountMap.put(entry.getKey().charAt(0), new BigInteger("0"));
                    }
                    characterCountMap.put(entry.getKey().charAt(0),
                            characterCountMap.get(entry.getKey().charAt(0)).add(entry.getValue()));

                    if (!characterCountMap.containsKey(entry.getKey().charAt(1))) {
                        characterCountMap.put(entry.getKey().charAt(1), new BigInteger("0"));
                    }
                    characterCountMap.put(entry.getKey().charAt(1),
                            characterCountMap.get(entry.getKey().charAt(1)).add(entry.getValue()));
                }
                characterCountMap.put(strings.get(0).charAt(0),
                        characterCountMap.get(strings.get(0).charAt(0)).add(new BigInteger("1")));
                characterCountMap.put(strings.get(0).charAt(strings.get(0).length() - 1),
                        characterCountMap.get(strings.get(0).charAt(strings.get(0).length() - 1))
                                .add(new BigInteger("1")));
                BigInteger minCount = new BigInteger("99999999999999999"), maxCount = new BigInteger("0");
                Character min = '0', max = '0';
                for (Map.Entry<Character, BigInteger> entry : characterCountMap.entrySet()) {
                    System.out.println(entry.getKey() + " " + entry.getValue());
                    if (minCount.compareTo(entry.getValue()) == 1) {
                        minCount = new BigInteger(entry.getValue().toString());
                        min = entry.getKey();
                    }
                    if (maxCount.compareTo(entry.getValue()) == -1) {
                        maxCount = new BigInteger(entry.getValue().toString());
                        max = entry.getKey();
                    }
                }
                System.out.println((maxCount.toString() + " " + minCount.toString()));
                System.out.println((max + " " + min));
                System.out.println("input " + (fi + 1) + " answer is: "
                        + (maxCount.subtract(minCount).divide(new BigInteger("2"))));
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + (maxCount.subtract(minCount).divide(new BigInteger("2"))));
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
