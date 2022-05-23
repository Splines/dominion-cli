package me.splines.dominion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleUtil {

    private ConsoleUtil() {
        throw new IllegalStateException("ConsoleUtil is a utility class");
    }

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

    public static Optional<Integer> getOptionalIntFromUser() {
        while (true) {
            try {
                return Optional.of(Integer.parseInt(sc.next()));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
    }

    public static List<Integer> getIntsFromUser() {
        while (true) {
            try {
                // split around comma and remove whitespaces
                String[] userStrings = sc.next().split(",");
                List<String> strings = Arrays.asList(userStrings);
                strings.replaceAll(String::trim);
                return strings.stream().map(Integer::parseInt).toList();
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

    public static String getStringFromUser() {
        while (true) {
            String line = sc.nextLine();
            try {
                Double.parseDouble(line);
            } catch (NumberFormatException e) {
                return line; // string is not a number
            }
            System.out.println("You've entered a number instead of text");
        }
    }

    public static List<String> getStringListFromUser() {
        // split around comma and remove whitespaces
        String[] userStrings = sc.next().split(",");
        List<String> strings = Arrays.asList(userStrings);
        strings.replaceAll(String::trim);
        return strings;
    }
}
