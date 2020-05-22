import java.util.*;
import java.io.*; 

class Solution {
    public static int actualLogic(int x, int n) {

        if(n>4 || n==0) {
            return (1);
        }
        else {
            int unitx = x % 10;
            if(unitx == 0) {
                return (0);
            }
            int[] case4 = new int[] {0,1,6,1,6,5,6,1,6,1};
            int[] case3 = new int[] {0,1,4,9,6,5,6,9,4,1}; 
            int temp = (int) n;
            switch(temp){
                case 1:
                    return (unitx);
                case 2:
                    return (case4[unitx]); // case2 is be same as case4
                case 3:
                    return (case3[unitx]);
                case 4:
                    return (case4[unitx]);
                default:
                    return (0);
            }  
        }  
    }

    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String[] params = readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()), 2);
            String[] result = new String[] {Integer.toString(actualLogic(Integer.parseInt(params[0]), Integer.parseInt(params[1])))};
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
