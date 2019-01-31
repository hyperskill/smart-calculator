package calculator;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (checkUserInput(line)) {
                String[] values = parseLine(line);
                long sum = sum(values);
                System.out.println(sum);
            }
        }
    }

    private static boolean checkUserInput(String input) {
        if ("".equals(input.trim()) || input.isEmpty() || input == null) {
            return false;
        }

        if ('/' == input.charAt(0)) {
            if ("/help".equals(input)) {
                printHelp();
                return false;
            } else if ("/exit".equals(input)) {
                System.out.println("Bye!");
                System.exit(0);
            } else {
                System.out.println("Unknown command");
                return false;
            }
        }

        Pattern pattern = Pattern.compile("(^[+-]?\\d+$)|([+-]?\\d*[+-]+\\d+)+");
        Matcher matcher = pattern.matcher(input.replaceAll("\\s*", ""));

        if (matcher.matches()) {
            return true;
        } else {
            System.out.println("Incorrect input. Try again.");
            return false;
        }
    }

    private static String[] parseLine(String line) {
        String[] values;

        while (true) {
            line = line.replaceAll("-{2}", "+");
            line = line.replaceAll("[+]{2,}", "+");
            line = line.replaceAll("[+][-]|[-][+]", "-");

            if (line.contains("+-") || line.contains("-+") || line.contains("--")) {
                continue;
            }

            line = line.replaceAll("\\s+", "");
            line = line.replaceAll("[+]", " +").replaceAll("[-]", " -");
            line = line.replaceAll("^\\s*|\\s*$", "");

            values = line.split(" ");
            break;
        }
        return values;
    }

    private static long sum(String[] values) {
        if (values.length == 1) {
            return Long.parseLong(values[0]);
        }

        long result = 0;
        for (int i = 0; i < values.length; i++) {
            result += Long.parseLong(values[i]);
        }
        return result;
    }

    private static void printHelp() {
        System.out.println("The program calculates the sum of the numbers.\n" +
                "Enter the equation using numbers, plus and minus.\n" +
                "For example, -2 + 3 - 5 + 10\n" +
                "or \"\\exit\" for closing program");
    }
}