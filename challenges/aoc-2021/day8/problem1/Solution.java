import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                int count = 0;
                for (int i = 0; i < strings.size(); i++) {
                    String[] values = Arrays.copyOfRange(strings.get(i).split(" "), 11, 15);
                    for (int index = 0; index < values.length; index++) {
                        int l = values[index].length();
                        if (l == 2 || l == 3 || l == 4 || l == 7) {
                            count++;
                        }
                    }
                }
                System.out.println("input " + (fi + 1) + " answer is: " + count);
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + count);
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
