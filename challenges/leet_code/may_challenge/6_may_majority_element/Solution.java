import java.util.*; 
import java.io.*; 
class Solution {
    public static int majorityElement(int[] nums) {

        Arrays.sort(nums); 
        int i, n = nums.length;
        for(i=0; i< n; i++) {
            if(nums[i] == nums[i+ n/2 + n%2 -1]) {
                break;
            }
        }
        return nums[i];
    }

    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String paramsInString = readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()),1)[0];
            String[] paramsStringArray = paramsInString.substring(1, paramsInString.length()-1).split(",");
            int[] params = new int[paramsStringArray.length];
            for(int j=0; j<params.length;j++) {
                params[j] = Integer.parseInt(paramsStringArray[j]);
            }
            String[] result = new String[] {Integer.toString(majorityElement(params))};
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
