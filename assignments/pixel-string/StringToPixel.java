import java.util.HashMap;

public class StringToPixel {
    // Maps saved for direct use
    private static String[][] a = {
            { " ", "*", "*", "*", " ", " " },
            { "*", " ", " ", " ", "*", " " },
            { "*", "*", "*", "*", "*", " " },
            { "*", " ", " ", " ", "*", " " },
            { "*", " ", " ", " ", "*", " " },
            { "*", " ", " ", " ", "*", " " }
    };

    private static String[][] b = {
            { "*", "*", "*", "*", " ", " " },
            { "*", " ", " ", " ", "*", " " },
            { "*", "*", "*", "*", " ", " " },
            { "*", " ", " ", " ", "*", " " },
            { "*", " ", " ", " ", "*", " " },
            { "*", "*", "*", "*", " ", " " }
    };

    private static String[][] c = {
            { " ", "*", "*", "*", " ", " " },
            { "*", " ", " ", " ", "*", " " },
            { "*", " ", " ", " ", " ", " " },
            { "*", " ", " ", " ", " ", " " },
            { "*", " ", " ", " ", "*", " " },
            { " ", "*", "*", "*", " ", " " }
    };

    private static String[][] one = {
            { " ", " ", "*", " ", " ", " " },
            { " ", "*", "*", " ", " ", " " },
            { " ", " ", "*", " ", " ", " " },
            { " ", " ", "*", " ", " ", " " },
            { " ", " ", "*", " ", " ", " " },
            { " ", "*", "*", "*", " ", " " }
    };

    private static String[][] two = {
            { " ", "*", "*", "*", " ", " " },
            { "*", " ", " ", " ", "*", " " },
            { " ", " ", " ", "*", " ", " " },
            { " ", " ", "*", " ", " ", " " },
            { " ", "*", " ", " ", " ", " " },
            { "*", "*", "*", "*", "*", " " }
    };

    private static String[][] three = {
            { " ", "*", "*", "*", " ", " " },
            { "*", " ", " ", " ", "*", " " },
            { " ", " ", "*", "*", " ", " " },
            { " ", " ", " ", " ", "*", " " },
            { "*", " ", " ", " ", "*", " " },
            { " ", "*", "*", "*", " ", " " }
    };

    StringToPixel(String input) {
        char[] inputCharArray = input.toCharArray();
        HashMap<Character, String[][]> pixelMap = new HashMap<>();
        pixelMap.put('A', a);
        pixelMap.put('B', b);
        pixelMap.put('C', c);
        pixelMap.put('1', one);
        pixelMap.put('2', two);
        pixelMap.put('3', three);
        for (int i = 0; i < inputCharArray.length; i++) {
            // throw error if invalid character is passed
            if (!pixelMap.containsKey(inputCharArray[i])) {
                throw new Error("Invalid character at position " + i
                        + " Allowed characters A, B, C, 1, 2 and 3");
            }
        }
        for (int i = 0; i < 6; i++) { // Printing row by row output
            for (int j = 0; j < inputCharArray.length; j++) {
                String[][] temp = pixelMap.get(inputCharArray[j]); // going thorugh all chars` columns for a row
                for (int k = 0; k < temp[0].length; k++) {
                    System.out.print(temp[i][k]);
                }
            }
            System.out.println();
        }
    }

}
