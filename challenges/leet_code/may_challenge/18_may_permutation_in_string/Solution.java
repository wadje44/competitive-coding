import java.io.*;

class Solution {
    public static boolean checkInclusion(String s1, String s2) {
        if(s1.length() > s2.length()) return false;
        char[] s1arr = s1.toCharArray(), s2arr = s2.toCharArray();
        int[] count  = new int[26];
        int i,j, temp=s1.length(),si=0;
        for(i=0;i<s1arr.length;i++) {
            count[s1arr[i] - 'a']++;
        }
        for(i=0;i<s2arr.length;i++) {
            if(count[s2arr[i] - 'a'] != 0) {
                count[s2arr[i] - 'a']--;
                temp--;
                if(temp==0) {
                    return true;
                }
            } else {
                if(s1.indexOf(s2arr[i]) == -1){
                    for(j=si;j<i;j++) {
                        count[s2arr[j] - 'a']++;
                        temp++;
                    }
                    si = i+1;
                } else {
                    for(j=si;s2arr[j] != s2arr[i];j++) {
                        count[s2arr[j] - 'a']++;
                        temp++;
                    }
                    si = j+1;
                }
            }
        }
        return false;
    }
    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String[] params = readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()), 2);
            String[] result = new String[] {Boolean.toString(checkInclusion(params[0], params[1]))};
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
