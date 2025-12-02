import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

  public static Boolean checkIfInvalidNumber(BigInteger num) {
      String numStr = num.toString();
      int length = numStr.length();
      int half = length / 2;

      for (int i = 1; i <= (half); i++) {
          if (length % i == 0) {
              String part = numStr.substring(0, i);
              StringBuilder sb = new StringBuilder();
              int repeat = length / i;
              for (int j = 0; j < repeat; j++) {
                  sb.append(part);
              }
              if (sb.toString().equals(numStr)) {
                  return true;
              }
          }
      }
      return false;
  }

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
        BigInteger counter = BigInteger.ZERO;
        for (int i = 0; i < lines.size(); i++) {
          String current = lines.get(i);
          List<String> ranges = Arrays.asList(current.split(","));
          for (int ri = 0; ri < ranges.size(); ri++) {
              List<String> bounds = Arrays.asList(ranges.get(ri).split("-"));
              BigInteger left = new BigInteger(bounds.get(0));
              BigInteger right = new BigInteger(bounds.get(1));
              // System.out.println(left + "  " + right);
              for (BigInteger num = left; num.compareTo(right) <= 0; num = num.add(BigInteger.ONE)) {
                  if (checkIfInvalidNumber(num)) {
                      // System.out.println("num " + num);
                      counter = counter.add(num);
                  }
              }
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