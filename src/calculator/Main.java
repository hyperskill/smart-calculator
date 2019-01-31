package calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (checkUserInput(line)) {
                String[] values = parseLine(line);
                long sum = sum(values);
                System.out.println(sum);
            } else {
                System.out.println("Incorrect input. Try again.");
            }
        }
    }
    private static boolean checkUserInput(String input) {
        if ("".equals(input.trim()) || input.isEmpty() || input == null) {
            return false;
        }

        if ("/help".equals(input)) {
            printHelp();
            return false;
        }

        if ("/exit".equals(input)) {
            System.out.println("Bye!");
            System.exit(0);
        }

        return true;
    }

    private static String[] parseLine(String line) {

        line = line.replaceAll("-{2}", "+");
        line = line.replaceAll("[+]{2,}", "+");
        line = line.replaceAll("[+][-]|[-][+]", "-");
        line = line.replaceAll("\\s+", "");
        line = line.replaceAll("[+]", " +").replaceAll("[-]", " -");
        line = line.replaceAll("^\\s*|\\s*$", "");

        String[] values = line.split(" ");
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
                "or \"\\exit\" for closing programm");
    }
}