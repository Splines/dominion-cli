import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public static List<Integer> getIntsFromUser() {
        while (true) {
            try {
                // split around comma and remove whitespaces
                List<String> strings = Arrays.asList(
                        sc.next().split("\\s*,\\s*"));
                return strings.stream()
                        .map(s -> Integer.parseInt(s))
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                System.out.println("Something is not a whole number, " +
                        "please check your input and try again...");
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
