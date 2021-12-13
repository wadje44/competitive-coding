import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.print.attribute.HashAttributeSet;

public class Solution {

    public static int countHash(char[][] paper, int startX, int startY, int maxX, int maxY) {
        int count = 0;
        for (int i = startY; i <= maxY; i++) {
            for (int j = startX; j <= maxX; j++) {
                if (paper[i][j] == '#') {
                    count++;
                }
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
                int lineN;
                int maxX = 0, maxY = 0, startX = 0, startY = 0;
                for (lineN = 0; lineN < strings.size(); lineN++) {
                    if (strings.get(lineN).contains(",")) {
                        String[] xy = strings.get(lineN).split(",");
                        int x = Integer.parseInt(xy[0]);
                        int y = Integer.parseInt(xy[1]);
                        if (maxX < x) {
                            maxX = x;
                        }
                        if (maxY < y) {
                            maxY = y;
                        }
                    } else {
                        break;
                    }
                }
                char[][] paper = new char[maxY + 1][maxX + 1];
                for (int i = 0; i < paper.length; i++) {
                    for (int j = 0; j < paper[0].length; j++) {
                        paper[i][j] = '.';
                    }
                }
                for (lineN = 0; lineN < strings.size(); lineN++) {
                    if (strings.get(lineN).contains(",")) {
                        String[] xy = strings.get(lineN).split(",");
                        int x = Integer.parseInt(xy[0]);
                        int y = Integer.parseInt(xy[1]);
                        paper[y][x] = '#';
                    } else {
                        break;
                    }
                }
                // for (int i = 0; i < paper.length; i++) {
                // System.out.println(Arrays.toString(paper[i]));
                // }
                System.out.println(maxX);
                System.out.println(maxY);

                String[] fold = strings.get(lineN + 1).split(" ")[2].split("=");
                int fromIndex = Integer.parseInt(fold[1]);
                int count = 0;
                System.out.println(fold[0]);
                if (fold[0].equals("x")) {
                    int i, j;
                    for (i = (fromIndex - 1), j = (fromIndex + 1); i >= 0 && j <= maxX; i--, j++) {
                        for (int yindex = 0; yindex <= maxY; yindex++) {
                            if (paper[yindex][i] == '#' || paper[yindex][j] == '#') {
                                paper[yindex][i] = '#';
                                paper[yindex][j] = '#';
                            }
                        }
                    }
                    if (i == -1 && j == (maxX + 1)) {
                        maxX = fromIndex - 1;
                    } else if (i == -1) {
                        startX = fromIndex + 1;
                    } else {
                        maxX = fromIndex - 1;
                    }
                    count = countHash(paper, startX, startY, maxX, maxY);
                } else {
                    int i, j;
                    for (i = (fromIndex - 1), j = (fromIndex + 1); i >= 0 && j <= maxY; i--, j++) {
                        for (int xindex = 0; xindex <= maxX; xindex++) {
                            if (paper[i][xindex] == '#' || paper[j][xindex] == '#') {
                                paper[i][xindex] = '#';
                                paper[j][xindex] = '#';
                            }
                        }
                    }
                    if (i == -1 && j == (maxY + 1)) {
                        maxY = fromIndex - 1;
                    } else if (i == -1) {
                        startY = fromIndex + 1;
                    } else {
                        maxY = fromIndex - 1;
                    }
                    count = countHash(paper, startX, startY, maxX, maxY);
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
