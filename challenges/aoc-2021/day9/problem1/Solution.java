import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                Integer[][] map = new Integer[strings.size()][strings.get(0).length()];
                for (int i = 0; i < strings.size(); i++) {
                    map[i] = Stream.of(strings.get(i).split("")).map(e -> Integer.parseInt(e))
                            .toArray(Integer[]::new);
                }
                int result = 0;
                for (int i = 0; i < strings.size(); i++) {
                    for (int j = 0; j < strings.get(0).length(); j++) {
                        if (i != 0 && map[i - 1][j] <= map[i][j]) {
                            continue;
                        }
                        if (i != (strings.size() - 1) && map[i + 1][j] <= map[i][j]) {
                            continue;
                        }
                        if (j != 0 && map[i][j - 1] <= map[i][j]) {
                            continue;
                        }
                        if (j != (strings.get(0).length() - 1) && map[i][j + 1] <= map[i][j]) {
                            continue;
                        }
                        result += 1 + map[i][j];
                    }
                }
                System.out.println("input " + (fi + 1) + " answer is: " + result);
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + "strings.size()");
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
