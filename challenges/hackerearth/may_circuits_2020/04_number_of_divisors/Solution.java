import java.util.*;
import java.io.*; 

class Solution {
    public static int findUndivisible (int k, int j) {
        int temp = j%k;
        while(temp == 0) {
            j = j/k;
            temp = j%k;
        }
        return j;
    }
    public static long actualLogic(int n, int k) {
        
        long ans = 0;
        int temp = n;
        while(temp >= k){
            int d = temp/k;
            ans = ans + ((long)temp*(temp+1)/2) - ((long)(d+1)*k*d/2);
            temp = d;

        }
        ans += temp*(temp+1)/2;
        return (ans);
    }

    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            int numberOfTestcases = Integer.parseInt(readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()), 1)[0]);
            String[] testcasesString = readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()), numberOfTestcases+1);
            String[] result = new String[numberOfTestcases];
            for(int j=1; j<=numberOfTestcases; j++) {
                String[] params = testcasesString[j].split(" ");
                result[j-1] = Long.toString(actualLogic(Integer.parseInt(params[0]), Integer.parseInt(params[1])));
            }
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
