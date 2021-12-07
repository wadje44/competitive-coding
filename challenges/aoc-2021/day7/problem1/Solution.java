import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    public static int getMedian(Integer[] numbers) {
        Arrays.sort(numbers);
        int median;
        if (numbers.length % 2 == 1) {
            median = numbers[numbers.length / 2];
        } else {
            median = (numbers[(numbers.length / 2) - 1] + numbers[numbers.length / 2]) / 2;
        }
        return median;
    }

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                Integer[] numbers = Stream.of(strings.get(0).split(",")).map(e -> Integer.parseInt(e))
                        .toArray(Integer[]::new);
                int median = getMedian(numbers);
                int sum = 0;
                for (int i = 0; i < numbers.length; i++) {
                    sum += Math.abs(numbers[i] - median);
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
