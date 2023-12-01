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
          int first = -1;
          int last = -1;
          for(int j=0; j < current.length(); j++) {
            Boolean flag = Character.isDigit(current.charAt(j));
            if(flag) {
              if (first == -1) {
                first = Character.getNumericValue(current.charAt(j));  
              } else {
                last = Character.getNumericValue(current.charAt(j));
              }
            }
          }
          ans += first * 10 + (last == -1 ? first : last);
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