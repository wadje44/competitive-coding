import java.io.*; 

class Solution {
    public static int findComplement(int num) {
        int d = num, r, result = 0,two_power=1;
        do {
            r = d%2;
            d = d/2;
            if(r == 0) {
                result += two_power;
            }
            two_power *= 2;
        } while(d!=0);
        return result;
    }

    
    public static void main(String args[]) {
        File folder = new File("test_cases/input");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            int param = Integer.parseInt(readLinesAsStrings(String.format("test_cases/input/%s", listOfFiles[i].getName()), 1)[0]);
            String[] result = new String[] {Integer.toString(findComplement(param))};
            writeLinesAsStrings(String.format("test_cases/expected_output/%d.txt",(i+1)), result);
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
