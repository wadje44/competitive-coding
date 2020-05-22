import java.io.*; 
class Solution {
    
    public static boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) return false;
        int[] ascii = new int[26];
        int i=0, count=0;
        for(i=0; i<ransomNote.length(); i++) {
            ascii[(int)ransomNote.charAt(i) - 97]++;
            count++;
        }
        for(i=0; i<magazine.length(); i++) {
            
            if(ascii[(int)magazine.charAt(i) - 97] != 0) {
                ascii[(int)magazine.charAt(i) - 97]--;
                count--;
            }
        }
        if(count != 0) {
            return false;
        }
        return true;
    }
    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String[] params = readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()), 2);
            String[] result = new String[] {Boolean.toString(canConstruct(params[0], params[1]))};
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
