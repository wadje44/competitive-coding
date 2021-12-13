import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    public static boolean checkAdj(Integer[][] levels, int i, int j) {
        if (i > 0 && j > 0 && levels[i - 1][j - 1] != 0) {
            return false;
        }
        if (i > 0 && levels[i - 1][j] != 0) {
            return false;
        }
        if (i > 0 && j < 9 && levels[i - 1][j + 1] != 0) {
            return false;
        }
        if (j > 0 && levels[i][j - 1] != 0) {
            return false;
        }
        if (j < 9 && levels[i][j + 1] != 0) {
            return false;
        }
        if (i < 9 && j > 0 && levels[i + 1][j - 1] != 0) {
            return false;
        }
        if (i < 9 && levels[i + 1][j] != 0) {
            return false;
        }
        if (i < 9 && j < 9 && levels[i + 1][j + 1] != 0) {
            return false;
        }
        return true;
    }

    public static int loopIn(Integer[][] levels, int i, int j, boolean[][] flag) {
        int count = 0;
        if (i > 0 && j > 0 && !flag[i - 1][j - 1]) {
            if (levels[i - 1][j - 1] != 9) {
                levels[i - 1][j - 1]++;
            } else {
                levels[i - 1][j - 1] = 0;
                flag[i - 1][j - 1] = true;
                count++;
                count += loopIn(levels, i - 1, j - 1, flag);
            }
        }
        if (i > 0 && !flag[i - 1][j]) {
            if (levels[i - 1][j] != 9) {
                levels[i - 1][j]++;
            } else {
                levels[i - 1][j] = 0;
                flag[i - 1][j] = true;
                count++;
                count += loopIn(levels, i - 1, j, flag);
            }
        }
        if (i > 0 && j < 9 && !flag[i - 1][j + 1]) {
            if (levels[i - 1][j + 1] != 9) {
                levels[i - 1][j + 1]++;
            } else {
                levels[i - 1][j + 1] = 0;
                flag[i - 1][j + 1] = true;
                count++;
                count += loopIn(levels, i - 1, j + 1, flag);
            }
        }
        if (j > 0 && !flag[i][j - 1]) {
            if (levels[i][j - 1] != 9) {
                levels[i][j - 1]++;
            } else {
                levels[i][j - 1] = 0;
                flag[i][j - 1] = true;
                count++;
                count += loopIn(levels, i, j - 1, flag);
            }
        }
        if (j < 9 && !flag[i][j + 1]) {
            if (levels[i][j + 1] != 9) {
                levels[i][j + 1]++;
            } else {
                levels[i][j + 1] = 0;
                flag[i][j + 1] = true;
                count++;
                count += loopIn(levels, i, j + 1, flag);
            }
        }
        if (i < 9 && j > 0 && !flag[i + 1][j - 1]) {
            if (levels[i + 1][j - 1] != 9) {
                levels[i + 1][j - 1]++;
            } else {
                levels[i + 1][j - 1] = 0;
                flag[i + 1][j - 1] = true;
                count++;
                count += loopIn(levels, i + 1, j - 1, flag);
            }
        }
        if (i < 9 && !flag[i + 1][j]) {
            if (levels[i + 1][j] != 9) {
                levels[i + 1][j]++;
            } else {
                levels[i + 1][j] = 0;
                flag[i + 1][j] = true;
                count++;
                count += loopIn(levels, i + 1, j, flag);
            }
        }
        if (i < 9 && j < 9 && !flag[i + 1][j + 1]) {
            if (levels[i + 1][j + 1] != 9) {
                levels[i + 1][j + 1]++;
            } else {
                levels[i + 1][j + 1] = 0;
                flag[i + 1][j + 1] = true;
                count++;
                count += loopIn(levels, i + 1, j + 1, flag);
            }
        }
        return count;
    }

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                Integer[][] levels = new Integer[10][10];
                int count = 0;
                for (int i = 0; i < 10; i++) {
                    levels[i] = Stream.of(strings.get(i).split("")).map(e -> Integer.parseInt(e))
                            .toArray(Integer[]::new);
                }
                int k;
                for (k = 0; true; k++) {
                    int tempCount = 0;
                    boolean[][] flag = new boolean[10][10];
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            if (levels[i][j] == 9) {
                                levels[i][j] = 0;
                                flag[i][j] = true;
                                count++;
                                tempCount++;
                                int t = loopIn(levels, i, j, flag);
                                tempCount += t;
                                count += t;
                            } else {
                                if (!flag[i][j]) {
                                    levels[i][j]++;
                                }
                            }
                        }
                    }
                    for (int i = 1; i < 9; i++) {
                        for (int j = 1; j < 9; j++) {
                            if (levels[i][j] != 0 && checkAdj(levels, i, j)) {
                                levels[i][j] = 0;
                                flag[i][j] = true;
                                count++;
                                tempCount++;
                            }
                        }
                    }
                    if (tempCount == 100) {
                        break;
                    }
                }

                System.out.println("input " + (fi + 1) + " answer is: " + (k + 1));
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + (k + 1));
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
