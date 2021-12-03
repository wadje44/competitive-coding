import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                int horizantal = 0, depth = 0, aim = 0;
                for (int i = 0; i < strings.size(); i++) {
                    String move = strings.get(i);
                    String[] dirAndMag = move.split(" ");
                    switch (dirAndMag[0]) {
                        case "forward":
                            horizantal += Integer.parseInt(dirAndMag[1]);
                            depth += aim * Integer.parseInt(dirAndMag[1]);
                            break;
                        case "down":
                            aim += Integer.parseInt(dirAndMag[1]);
                            break;
                        case "up":
                            aim -= Integer.parseInt(dirAndMag[1]);
                            break;
                        default:
                            break;
                    }
                }
                System.out.println("input " + (fi + 1) + " answer is: " + (horizantal * depth));
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + (horizantal * depth));
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
