import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class Solution {

    public static int commonCount(String a, String b) {
        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            for (int j = 0; j < b.length(); j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                int sum = 0;
                for (int i = 0; i < strings.size(); i++) {
                    HashMap<String, Integer> map1 = new HashMap<>();
                    HashMap<Integer, String> map2 = new HashMap<>();
                    String[] inputDigits = Arrays.copyOfRange(strings.get(i).split(" "), 0, 10);
                    for (int j = 0; j < inputDigits.length; j++) {
                        char[] temp = inputDigits[j].toCharArray();
                        Arrays.sort(temp);
                        inputDigits[j] = new String(temp);
                        int l = inputDigits[j].length();
                        if (l == 2) {
                            map1.put(inputDigits[j], 1);
                            map2.put(1, inputDigits[j]);
                        } else if (l == 4) {
                            map1.put(inputDigits[j], 4);
                            map2.put(4, inputDigits[j]);
                        } else if (l == 3) {
                            map1.put(inputDigits[j], 7);
                            map2.put(7, inputDigits[j]);
                        } else if (l == 7) {
                            map1.put(inputDigits[j], 8);
                            map2.put(8, inputDigits[j]);
                        }
                    }
                    for (int j = 0; j < inputDigits.length; j++) {
                        int l = inputDigits[j].length();
                        if (l == 5) {
                            if (commonCount(inputDigits[j], map2.get(7)) == 3) {
                                map1.put(inputDigits[j], 3);
                                map2.put(3, inputDigits[j]);
                            } else if (commonCount(inputDigits[j], map2.get(4)) == 3) {
                                map1.put(inputDigits[j], 5);
                                map2.put(5, inputDigits[j]);
                            } else {
                                map1.put(inputDigits[j], 2);
                                map2.put(2, inputDigits[j]);
                            }
                        } else if (l == 6) {
                            if (commonCount(inputDigits[j], map2.get(4)) == 4) {
                                map1.put(inputDigits[j], 9);
                                map2.put(9, inputDigits[j]);
                            } else if (commonCount(inputDigits[j], map2.get(7)) == 3) {
                                map1.put(inputDigits[j], 0);
                                map2.put(0, inputDigits[j]);
                            } else {
                                map1.put(inputDigits[j], 6);
                                map2.put(6, inputDigits[j]);
                            }
                        }
                    }
                    String[] values = Arrays.copyOfRange(strings.get(i).split(" "), 11, 15);
                    String a = new String("");
                    for (int j = 0; j < values.length; j++) {
                        char[] temp = values[j].toCharArray();
                        Arrays.sort(temp);
                        values[j] = new String(temp);
                        a = a.concat("" + map1.get(values[j]));
                    }
                    sum += Integer.parseInt(a);
                }
                System.out.println("input " + (fi + 1) + " answer is: " + sum);
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + sum);
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
