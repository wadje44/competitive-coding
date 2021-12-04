import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class Solution {

    public static int bingo(HashMap<String, String> matrix, int current) {
        int result = 0;
        for (Map.Entry<String, String> mapElement : matrix.entrySet()) {
            result += Integer.parseInt(mapElement.getKey());
        }
        result *= current;
        return result;
    }

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                String[] inputStream = strings.get(0).split(",");
                List<HashMap<String, String>> matrices = new ArrayList<>();
                List<int[]> row = new ArrayList<>();
                List<int[]> col = new ArrayList<>();
                for (int i = 2; i < strings.size(); i++) {
                    row.add(new int[5]);
                    col.add(new int[5]);
                    HashMap<String, String> matrix = new HashMap<>();
                    for (int r = 0; r < 5; r++, i++) {
                        String[] elements = strings.get(i).trim().split("\\s+");
                        for (int c = 0; c < 5; c++) {
                            matrix.put(elements[c], "" + r + c);
                        }
                    }
                    matrices.add(matrix);
                }
                int result = 0;
                for (int i = 0; i < inputStream.length; i++) {
                    int j;
                    for (j = 0; j < matrices.size(); j++) {
                        if (matrices.get(j).get(inputStream[i]) != null) {
                            String[] rowcol = matrices.get(j).get(inputStream[i]).split("");
                            row.get(j)[Integer.parseInt(rowcol[0])]++;
                            col.get(j)[Integer.parseInt(rowcol[1])]++;
                            matrices.get(j).remove(inputStream[i]);
                            if (row.get(j)[Integer.parseInt(rowcol[0])] == 5 ||
                                    col.get(j)[Integer.parseInt(rowcol[1])] == 5) {
                                result = bingo(matrices.get(j), Integer.parseInt(inputStream[i]));
                                break;
                            }
                        }
                    }
                    if (j != matrices.size()) {
                        break;
                    }
                }
                System.out.println("input " + (fi + 1) + " answer is: " + result);
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + result);
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
