import java.io.*;
import java.util.ArrayList;

class StockSpanner {
    int[] data;
    int[] ans;
    int total;
    public StockSpanner() {
        data = new int[10000];
        ans  = new int[10000];
        total = 0;
    }
    public int findLastBig(int index) {
        if(index < 0) return 0;
        if(data[index] <= data[total]) {
            return ans[index] + findLastBig(index - ans[index]);
        } else {
            return 0;
        }
    }
    
    public int next(int price) {
        data[total] = price;
        if(total == 0) {
            ans[total] = 1;
        } else {
            ans[total] = 1 + findLastBig(total-1);
        }
        total++;
        return ans[total-1];
    }

    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            StockSpanner ss = new StockSpanner();
            String paramsInString = readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()),1)[0];
            String[] paramsStringArray = paramsInString.substring(1, paramsInString.length()-1).split(",");
            int[] params = new int[paramsStringArray.length];
            for(int j=0; j<params.length;j++) {
                params[j] = Integer.parseInt(paramsStringArray[j]);
            }
            ArrayList<Integer> resultInt = new ArrayList<Integer>();
            for(int k=0; k<params.length; k++) {
                resultInt.add(ss.next(params[k]));
            }
            String[] result = new String[] {resultInt.toString()};
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
