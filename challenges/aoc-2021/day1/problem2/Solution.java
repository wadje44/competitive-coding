import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;

class Solution {
  public static void main(String args[]) {
    try {
      File inputFolder = new File("./test-cases/input");
      File[] listOfFiles = inputFolder.listFiles();
      for (int fi = 0; fi < listOfFiles.length; fi++) {
        List<Integer> ints = Files.lines(Paths.get(listOfFiles[fi].getPath()))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
        int count = 0;
        for (int i = 3; i < ints.size(); i++) {
          int current = ints.get(i);
          int previous = ints.get(i - 3);
          if (previous < current) {
            count++;
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