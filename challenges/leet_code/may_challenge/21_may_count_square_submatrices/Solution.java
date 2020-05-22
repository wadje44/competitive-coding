import java.util.*; 
import java.io.*; 
class Solution {
 
    public static int findMin(int i, int j, int k) {
        if(i <= j && i <= k) return i;
        if(j <= k) return j;
        return k;
    }

    public static int findTotalByDp(int[][] matrix, int[][] dp, boolean[][] visited, int i, int j) {
        if(!visited[i][j]) {
            int total;
            if(matrix[i][j] == 0) {
                total = 0;
            } else {
                total = 1;
                if(i != 0 && j!=0) {
                    if(!visited[i-1][j] || !visited[i][j-1] || !visited[i-1][j-1]) return 0;
                    total += findMin(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]);
                }
            }
            visited[i][j] = true;
            dp[i][j] = total;
            if((j < matrix[0].length-1) && !visited[i][j+1]) total += findTotalByDp(matrix, dp, visited, i, j+1);
            if((i < matrix.length-1) && !visited[i+1][j]) total += findTotalByDp(matrix, dp, visited, i+1, j);
            return total;
        }
        return 0;
    }
    
    public static int countSquares(int[][] matrix) {
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        int[][] dp = new int[matrix.length][matrix[0].length];
        int isSquare = 0;
        if(matrix[0][0] == 1) {
            dp[0][0] = 1;
            isSquare++;
        }
        visited[0][0] = true;
        return (isSquare +
                findTotalByDp(matrix, dp, visited, 0, 1) +
                findTotalByDp(matrix, dp, visited, 1, 0));
    }

    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String paramsInString = readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()),1)[0];
            String[] wholeArray = paramsInString.substring(2, paramsInString.length()-2).split("\\],\\[");
            // count columns
            int columnsCount = wholeArray[0].substring(0, wholeArray[0].length()).split(",").length;
            int[][] param = new int[wholeArray.length][columnsCount];
            for(int j=0; j<wholeArray.length;j++) {
                String[] row = wholeArray[j].substring(0, wholeArray[j].length()).split(",");
                for(int k=0; k<row.length; k++) {
                    param[j][k] = Integer.parseInt(row[k]);
                }
            }
            String[] result = new String[] {Integer.toString(countSquares(param))};
            writeLinesAsStrings(String.format("test_cases/output/%d.txt",(i+1)), result);
        }
    }

    public static String[] readLinesAsStrings(String path, int numberOfLines){
        String[] result = new String[numberOfLines];
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            int lineNumber=0; 
            while (lineNumber<numberOfLines && ((result[lineNumber++] = br.readLine()) != null));
            br.close();
        } catch(IOException E) {
            System.out.println("IOException caught while reading file: " + E);
        } 
        return result;
    }

    public static void writeLinesAsStrings(String path, String[] data){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for(int i=0; i<data.length; i++) writer.write(data[i]);
            writer.close();
        } catch(IOException E) {
            System.out.println("IOException caught while reading file: " + E);
        } 
    }

}