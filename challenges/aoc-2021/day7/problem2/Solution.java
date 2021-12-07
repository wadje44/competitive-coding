import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.*;

public class Solution {

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                Integer[] numbers = Stream.of(strings.get(0).split(",")).map(e -> Integer.parseInt(e))
                        .toArray(Integer[]::new);
                Arrays.sort(numbers);
                Integer[] numbersDistance = new Integer[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    numbersDistance[i] = 0;
                    for (int j = 0; j < numbers.length; j++) {
                        int diff = Math.abs(numbers[i] - numbers[j]);
                        numbersDistance[i] += (diff * (diff + 1)) / 2;
                    }
                }
                int k;
                for (k = 0; k < numbersDistance.length - 1; k++) {
                    if (numbersDistance[k] < numbersDistance[k + 1]) {
                        break;
                    }
                }
                int min = numbersDistance[k - 1];
                for (int i = numbers[k - 1]; i <= numbers[k + 1]; i++) {
                    int tempMin = 0;
                    for (int j = 0; j < numbers.length; j++) {
                        int diff = Math.abs(i - numbers[j]);
                        tempMin += (diff * (diff + 1)) / 2;
                    }
                    if (tempMin < min) {
                        min = tempMin;
                    }
                }
                System.out.println("input " + (fi + 1) + " answer is: " + min);
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + min);
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
