import java.io.*; 

class Solution {
    
    public static int firstUniqChar(String s) {
        int[] index = new int[26];
        int[] count = new int[26];
        int min = s.length();
        
        for(int i=0; i<s.length(); i++) {
            count[(int) s.charAt(i) - 97]++;
            index[(int) s.charAt(i) - 97] = i;
        }
        for(int i=0; i<26; i++) {
            if(count[i] == 1) {
                int tempI = s.indexOf((char)(i+97));
                if(min > tempI) {
                    min = index[i];
                }
            }
        }
        if(min == s.length()) return -1;
        return min;
    }

    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String[] params = readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()), 1);
            String[] result = new String[] {Integer.toString(firstUniqChar(params[0]))};
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
