import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
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
        int ans = 0;
        
        for (int i = 0; i < lines.size(); i++) {
          String current = lines.get(i);
          int colonIndex = current.indexOf(':');
          int id = Integer.parseInt(current.substring(5,colonIndex));
          String[] values = current.substring(colonIndex+1).split(";");
          boolean flag = true;
          for (int j = 0; j < values.length && flag; j++) {
            String[] balls = values[j].split(",");
            for (int k = 0; k < balls.length && flag; k++) {
              String[] count = balls[k].split(" ");
              if(max.get(count[2]) < Integer.parseInt(count[1])) {
                flag = false;
                break;
              }
            }
          }
          if(flag) {
            ans += id;
          }
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