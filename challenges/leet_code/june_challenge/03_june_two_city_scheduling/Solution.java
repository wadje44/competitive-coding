import java.lang.*;
import java.util.*; 
import java.io.*; 

class Solution {
    public static int twoCitySchedCost(int[][] costs) {
        int i, ans = 0;
        Arrays.sort(costs, new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
               return o1[0] - o1[1] - (o2[0] - o2[1]);
            }
        });
        for(i=0; i<costs.length/2; i++) {
            ans += costs[i][0] + costs[costs.length/2 + i][1];
        } 
        
        return ans;
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
            String[] result = new String[] {Integer.toString(twoCitySchedCost(param))};
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
             for(int i=0; i<data.length; i++) {
                writer.write(data[i]);
                if(i!=(data.length-1)) writer.newLine();
            }
            writer.close();
        } catch(IOException E) {
            System.out.println("IOException caught while reading file: " + E);
        } 
    }


}
