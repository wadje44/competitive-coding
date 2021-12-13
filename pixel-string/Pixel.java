public class Pixel {
    private char[] inputString;
    private char pixels[][];

    Pixel(String input) {
        inputString = input.toCharArray();
        pixels = new char[6][36];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 36; j++) {
                pixels[i][j] = ' ';
            }
        }
        generatePixels();
        printPixels();
    }

    private void generatePixels() {
        int prevCharCount = 0, previousDigitCount = 0, row = -1, column = -1;
        for (int i = 0; i < inputString.length; i++) {
            if (Character.isUpperCase(inputString[i])) {
                if (prevCharCount == 1) {
                    throw new Error("Invalid character at position " + i + "Expected digit");
                } else {
                    if (previousDigitCount != 0) {
                        plotPoint(row, column);
                        row = -1;
                        column = -1;
                    }
                    previousDigitCount = 0;
                    prevCharCount++;
                    row = getRow(inputString[i], i);
                }
            } else if (Character.isDigit(inputString[i])) {
                prevCharCount = 0;
                column = getColumn(inputString[i], i, previousDigitCount, column);
                previousDigitCount++;
            }
        }
        plotPoint(row, column);
    }

    private void plotPoint(int row, int column) {
        pixels[row][column] = '*';
    }

    private int getRow(char rowChar, int pos) {
        int row = ((int) rowChar) - 65;
        if (row < 0 || row > 5) {
            throw new Error("Invalid character at position " + pos + " Expected row in A-F");
        }
        return row;
    }

    private int getColumn(char columnChar, int pos, int previousDigitCount, int prevColumn) {
        int digit = ((int) columnChar) - 48;
        int column = 0;
        if (previousDigitCount == 0) {
            column = digit;
        } else if (previousDigitCount == 1) {
            column = (prevColumn * 10) + digit;
        } else {
            throw new Error("Invalid character at position " + pos + "Expected Character");
        }
        if (column < 0 || column > 35) {
            throw new Error("Invalid character at position " + (pos - 1) + "Expected column in 0-35");
        }
        return column;
    }

    private void printPixels() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 36; j++) {
                System.out.print(pixels[i][j]);
            }
            System.out.println();
        }
    }
}
