import java.io.*; 

class VersionControl {
    public static int badVersion;

    public static boolean isBadVersion(int t) {
        if(t>=badVersion) return true;
        return false;
    }
}

public class Solution extends VersionControl {

    public static int firstBadVersion(int n) {
        int low=1, high=n, mid = (low/2 +high/2);
        while(low < high) {
            if(isBadVersion(mid))
                high = mid;
            else
                low = mid+1;
            mid = (low/2+high/2);
        }
        return low;
    }
    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String[] params = readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()), 2);
            badVersion = Integer.parseInt(params[1]);
            String[] result = new String[] {Integer.toString(firstBadVersion(Integer.parseInt(params[0])))};
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
