import java.util.Scanner;

public class ConsoleUtil {

    private static final Scanner sc = new Scanner(System.in);

    public static int getIntFromUser() {
        while (true) {
            try {
                return Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                System.out.println("That's not a whole number, try again...");
            }
        }
    }

    public static boolean getBooleanFromUser() {
        while (true) {
            String line = sc.nextLine();
            if (line.equalsIgnoreCase("yes"))
                return true;
            else if (line.equalsIgnoreCase("no"))
                return false;
            System.out.println("That's not a valid answer (yes/no), try again...");
        }
    }
}
