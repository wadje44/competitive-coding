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
                int stringSize = strings.get(0).length();

                int zerocount[] = new int[stringSize];
                int onecount[] = new int[stringSize];
                for (int i = 0; i < strings.size(); i++) {
                    for (int j = 0; j < stringSize; j++) {

                        if (strings.get(i).charAt(j) == '0') {
                            zerocount[j]++;
                        } else {
                            onecount[j]++;
                        }
                    }
                }
                String gamma = "", eplsilon = "";
                for (int j = 0; j < stringSize; j++) {
                    if (zerocount[j] > onecount[j]) {
                        gamma += "0";
                        eplsilon += "1";
                    } else {
                        gamma += "1";
                        eplsilon += "0";
                    }
                }
                int gammaNumber = Integer.parseInt(gamma, 2);
                int eplsilonNumber = Integer.parseInt(eplsilon, 2);
                System.out.println("input " + (fi + 1) + " answer is: " + (gammaNumber * eplsilonNumber));
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + ((gammaNumber * eplsilonNumber)));
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
