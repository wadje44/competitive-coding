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
        List<String> lines = Files.lines(Paths.get(listOfFiles[fi].getPath()))
            .collect(Collectors.toList());
        int ans = 0;
        for (int i = 0; i < lines.size(); i++) {
          String current = lines.get(i);
          int colonIndex = current.indexOf(':');
          int id = Integer.parseInt(current.substring(5,colonIndex));
          HashMap<String, Integer> max = new HashMap<String, Integer>();
          // Add keys and values (Country, City)
          max.put("red", 0);
          max.put("green", 0);
          max.put("blue", 0);
          String[] values = current.substring(colonIndex+1).split(";");
          boolean flag = true;
          for (int j = 0; j < values.length && flag; j++) {
            String[] balls = values[j].split(",");
            for (int k = 0; k < balls.length && flag; k++) {
              String[] count = balls[k].split(" ");
              int t = Integer.parseInt(count[1]);
              if(max.get(count[2]) < t) {
                max.put(count[2], t);
              }
            }
          }
          ans += max.get("red") * max.get("green") * max.get("blue");
        }
        System.out.println("input " + (fi + 1) + " answer is: " + ans);
        BufferedWriter writer = new BufferedWriter(
            new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
        writer.write(" answer is: " + ans);
        writer.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}