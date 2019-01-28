package calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine();
            if (checkUserInput(line)) {
                parseLine(line);
            }
        }
    }

    private static boolean checkUserInput(String input) {

        if ("".equals(input.trim()) || input.isEmpty() || input == null) {
            return false;
        }

        if ("/exit".equals(input)) {
            System.out.println("Bye!");
            System.exit(0);
        }

        return true;
    }

    private static void parseLine(String line) {
        String[] values = line.split(" ");

        if (values.length == 1) {
            System.out.println(values[0]);
        } else {
            System.out.println(sum(values));
        }
    }

    private static long sum(String[] values) {
        long result = 0;

        for (int i = 0; i < values.length; i++) {
            result += Long.parseLong(values[i]);
        }

        return result;
    }
}