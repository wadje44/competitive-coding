import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    public static int traverseMore(int i, int j, Integer[][] map, boolean[][] flags) {
        if (i >= 0 && j >= 0 && i < map.length && j < map[0].length) {
            if (!flags[i][j] && map[i][j] != 9) {
                flags[i][j] = true;
                return (1 + traverseMore(i - 1, j, map, flags)
                        + traverseMore(i + 1, j, map, flags)
                        + traverseMore(i, j - 1, map, flags)
                        + traverseMore(i, j + 1, map, flags));
            }
        }
        return 0;
    }

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
                int result = 1;
                List<Integer> count = new ArrayList<>();
                boolean[][] flags = new boolean[strings.size()][strings.get(0).length()];
                for (int i = 0; i < strings.size(); i++) {
                    for (int j = 0; j < strings.get(0).length(); j++) {
                        if (map[i][j] != 9 && !flags[i][j]) {
                            flags[i][j] = true;
                            count.add(1 + traverseMore(i - 1, j, map, flags)
                                    + traverseMore(i + 1, j, map, flags)
                                    + traverseMore(i, j - 1, map, flags)
                                    + traverseMore(i, j + 1, map, flags));
                        }
                    }
                }
                Collections.sort(count);
                Collections.reverse(count);
                result = count.get(0) * count.get(1) * count.get(2);
                System.out.println("input " + (fi + 1) + " answer is: " + result);
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + result);
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
