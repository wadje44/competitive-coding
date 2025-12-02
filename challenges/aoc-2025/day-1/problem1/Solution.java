import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

class Solution {
  public static void main(String args[]) {
    try {
      HashMap<String, Integer> max = new HashMap<String, Integer>();
      max.put("red", 12);
      max.put("green", 13);
      max.put("blue", 14);
      File inputFolder = new File("./test-cases/input");
      File[] listOfFiles = inputFolder.listFiles();
      for (int fi = 0; fi < listOfFiles.length; fi++) {
        List<String> lines = Files.lines(Paths.get(listOfFiles[fi].getPath()))
            .collect(Collectors.toList());
        int dial = 50;
        int counter = 0;
        for (int i = 0; i < lines.size(); i++) {
          String current = lines.get(i);
          Character direction = current.charAt(0);
          int steps = Integer.parseInt(current.substring(1,current.length()));
          steps = steps % 100;
          if (direction == 'L') {
            dial -= steps;
          } else {
            dial += steps;
          }
          if (dial < 0) {
            dial = 100 + dial;
          } else if (dial >= 100) {
            dial = dial - 100;
          }
          if (dial == 0) {
            counter++;
          }
        }
        System.out.println("input " + listOfFiles[fi].getName() + " answer is: " + counter);
        BufferedWriter writer = new BufferedWriter(
            new FileWriter("./test-cases/output/" + listOfFiles[fi].getName()));
        writer.write(" answer is: " + counter);
        writer.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}