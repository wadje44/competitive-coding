import java.util.Scanner;

public class PixelString {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int choice;
        do {
            System.out.print(
                    "\nEnter fuctionality choice number \n" +
                            "\t 1. Pixel plots to Pattern \n" +
                            "\t 2. String to pattern \n" +
                            "\t 3. Exit application \n\n" +
                            "choice :: ");
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    String input;
                    System.out.print("Enter pixel positions : ");
                    input = in.next();
                    new Pixel(input);
                    break;
                case 2:
                    String inputString;
                    System.out.print("Enter pixel string : ");
                    inputString = in.next();
                    try {
                        new StringToPixel(inputString);
                    } catch (Exception e) {
                        System.out.println(e);
                        choice = -1;
                    }
                    break;
                default:
                    break;
            }
        } while (choice != 3); // To keep repeating until exit option choosen
        in.close();
    }

}
