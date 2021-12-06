import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    public static long getFishes(int remainingdays, int totaldays) {
        if (totaldays < remainingdays)
            return 1;
        long count = 0;
        totaldays = totaldays - remainingdays;
        int period = 7;
        while (totaldays >= 0) {
            count += (getFishes(9, totaldays));
            totaldays -= period;
        }
        return count + 1;
    }

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                HashMap<Integer, Long> map = new HashMap<Integer, Long>();
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                Integer[] numbers = Stream.of(strings.get(0).split(",")).map(e -> Integer.parseInt(e))
                        .toArray(Integer[]::new);
                BigInteger bigcount = new BigInteger("0");
                for (int i = 0; i < numbers.length; i++) {
                    if (!map.containsKey(numbers[i] + 1)) {
                        map.put(numbers[i] + 1, getFishes(numbers[i] + 1, 80));
                    }
                    bigcount = bigcount.add(new BigInteger(Long.toString(map.get(numbers[i] + 1))));
                }
                System.out.println("input " + (fi + 1) + " answer is: " + bigcount.toString());
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + bigcount);
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
