import java.io.*;
import java.io.*; 
import java.util.*;

class CharCount {
    char data;
    int count;
    
    CharCount(char a, int b) {
        data = a;
        count = b;
    }
    
    public char getData(){
        return data;
    } 
    public int getCount() {
        return count;
    }

}

class Solution {
    public static String frequencySort(String s) {
        char[] arr = s.toCharArray();
        ArrayList<CharCount> list = new ArrayList<CharCount>();
        int count[] = new int[256];
        int i;
        for(i=0;i<arr.length;i++) {
            count[arr[i]]++;
        }
        for(i=0;i<256;i++) {
            
            if(count[i] != 0) {
                list.add(new CharCount((char) (i), count[i]));
            }
        }
        Collections.sort(list, new Comparator<CharCount>(){
            public int compare(CharCount s1, CharCount s2) {
                return s2.getCount() - s1.getCount();
            }
        });
        StringBuilder ans = new StringBuilder();
        for (i=0; i<list.size(); i++){
            int tcount = list.get(i).getCount();
            char data = list.get(i).getData();
            for(int k=0; k<tcount; k++){
                ans.append(data);
            }
        }
         return ans.toString();
    }

    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String[] params = readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()), 1);
            String[] result = new String[] {frequencySort(params[0])};
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
