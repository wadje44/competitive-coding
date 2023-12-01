import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;

class Solution {
  public static int getIntValue(String s) {
    switch(s) {
      case "one":
        return 1;
      case "two":
        return 2;
      case "three":
        return 3;
      case "four":
        return 4;
      case "five":
        return 5;
      case "six":
        return 6;
      case "seven":
        return 7;
      case "eight":
        return 8;
      case "nine":
        return 9;
      default:
        return -1;
    }
  }
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
          int k = 0;
          int first = -1;
          int last = -1;
          while(k < current.length()) {
            Boolean flag = Character.isDigit(current.charAt(k));
            if(flag) {
              if (first == -1) {
                first = Character.getNumericValue(current.charAt(k));  
              } else {
                last = Character.getNumericValue(current.charAt(k));
              }
              k++;
            } else {
              int l = 3;
              while(l < 6 && current.length() >= k+l) {
                int intValue = getIntValue(current.substring(k, k + l));
                if(intValue != -1) {
                  if (first == -1) {
                    first = intValue;
                  } else {
                    last = intValue;
                  }
                  // in overlapping numbers take both numbers in account, this is stupid but this is what expected
                  // so don't increase length
                  // k = k + l - 1;
                  break;
                }
                l++;
              }
              k++;
            }
          }         
          ans += (first * 10 + (last == -1 ? first : last));          
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