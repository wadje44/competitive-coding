import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.print.attribute.HashAttributeSet;

public class Solution {

    public static int cost(int[][] a, int[][] dp, int n, int m, int i, int j) {
        if(i < 0 || i >= n || j < 0 || j >= m) {
            return Integer.MAX_VALUE;
        }
        if(dp[i][j] == -1) {
            dp[i][j] = a[i][j] + Math.min(
                                    cost(a, dp, n, m, i, j-1),
                                    Math.min(  
                                        cost(a, dp, n, m, i-1, j),
                                        Math.min(
                                            cost(a, dp, n, m, i+1, j),
                                            cost(a, dp, n, m, i, j+1)
                                        )
                                    )
                                );
        }
        return dp[i][j];
        
    }

    public static void main(String args[]) {
        try {
            File inputFolder = new File("./test-cases/input");
            File[] listOfFiles = inputFolder.listFiles();
            for (int fi = 0; fi < listOfFiles.length; fi++) {
                List<String> strings = Files.lines(Paths.get(listOfFiles[fi].getPath()))
                        .collect(Collectors.toList());
                int n = strings.size(), m = strings.get(0).length();
                int[][] a = new int[n][m];
                int[][] dp = new int[n][m];
                for (int i = 0; i < n; i++) {
                    String[] t = strings.get(i).split("");
                    for (int j = 0; j < m; j++) {
                        a[i][j] = Integer.parseInt(t[j]);
                        dp[i][j] = -1;
                    }
                }
                dp[n-1][m-1] = a[n-1][m-1];
                int result = cost(a, dp, n, m, 0, 0);
                // for (int i = 0; i < n; i++) {
                //     System.out.println(Arrays.toString(dp[i]));
                // }
                System.out.println("input " + (fi + 1) + " answer is: " + (result-a[0][0]));
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./test-cases/output/" + (fi + 1) + ".txt"));
                writer.write(" answer is: " + (result-a[0][0]));
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
