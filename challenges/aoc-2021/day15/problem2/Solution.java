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

                System.out.println("input " + (fi + 1) + " answer is: " + ("maxCount - minCount"));
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + ("maxCount - minCount"));
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
