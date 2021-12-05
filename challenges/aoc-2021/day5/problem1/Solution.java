import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class Solution {

    public static int markMatrix(int[][] matrix, int x1, int y1, int x2, int y2) {
        int mark2Count = 0;
        if (y1 == y2) {
            if (x1 > x2) {
                for (int i = x2; i <= x1; i++) {
                    matrix[i][y1]++;
                    if (matrix[i][y1] == 2) {
                        mark2Count++;
                    }
                }
            } else {
                for (int i = x1; i <= x2; i++) {
                    matrix[i][y1]++;
                    if (matrix[i][y1] == 2) {
                        mark2Count++;
                    }
                }
            }
        } else {
            if (y1 > y2) {
                for (int i = y2; i <= y1; i++) {
                    matrix[x1][i]++;
                    if (matrix[x1][i] == 2) {
                        mark2Count++;
                    }
                }
            } else {
                for (int i = y1; i <= y2; i++) {
                    matrix[x1][i]++;
                    if (matrix[x1][i] == 2) {
                        mark2Count++;
                    }
                }
            }
        }
        return mark2Count;
    }

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                int[][] matrix = new int[1000][1000];
                int count = 0;
                for (int i = 0; i < strings.size(); i++) {
                    String[] cordinates = strings.get(i).split(" -> ");
                    String[] point1 = cordinates[0].split(",");
                    String[] point2 = cordinates[1].split(",");
                    int x1 = Integer.parseInt(point1[0]);
                    int y1 = Integer.parseInt(point1[1]);
                    int x2 = Integer.parseInt(point2[0]);
                    int y2 = Integer.parseInt(point2[1]);
                    if (x1 == x2 || y1 == y2) {
                        count += markMatrix(matrix, x1, y1, x2, y2);
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
