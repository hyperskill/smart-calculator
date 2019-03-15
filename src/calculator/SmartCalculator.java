package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SmartCalculator {

    private static Map<Character, Integer> variables;

    public SmartCalculator() {
        variables = new HashMap<>();
    }

    static void runCalculator() {

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("/exit")) {

            try {
                parseLine(line);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid expression");
            }

            line = scanner.nextLine();

        }

        System.out.print("Bye!");

    }

    private static void parseLine(String line) {
        if (isCommand(line)) {

            executeCommand(line);

        } else if (isAssignment(line)) {

        } else if (isInvalidExpression(line)) {

            throw new IllegalArgumentException("Invalid expression");

        } else if (line.length() > 0) {

            Matcher matcher = Pattern.compile("(^|[ +-])+\\d+").matcher(line);

            int result = 0;
            while (matcher.find()) {
                result += getNumberFromString(matcher.group());
            }

            System.out.println(result);

        }
    }

    private static boolean isAssignment(String line) {
        return line.contains("=");
    }

    private static void executeCommand(String line) {
        if (line.equals("/help")) {
            System.out.println("Any help here");
        }
        else {
            System.out.println("Unknown command");
        }
    }

    private static boolean isCommand(String line) {
        Matcher matcher = Pattern.compile("^\\/\\w+$").matcher(line);
        return matcher.find();
    }

    private static int getNumberFromString(String group) {

        char[] charArray = group.toCharArray();
        boolean isNegative = false;
        int charPositionOfZero = (int)'0';
        int result = 0;

        for (char symbol:
             charArray) {
            if (symbol == '-') isNegative = !isNegative;
            if (symbol >= charPositionOfZero&& symbol < charPositionOfZero+10) {
                result = result*10+(int)symbol-charPositionOfZero;
            }
        }

        if (isNegative) result = -result;
        return result;
    }

    private static boolean isInvalidExpression(String expression) {
        Matcher matcher = Pattern.compile("^\\s*[+-]*\\s*\\d+(\\s*[+-]+\\s*+\\d+)*\\s*$").matcher(expression);
        return !matcher.find();
    }

    private static boolean isUnknownCommand(String command) {
        Matcher matcher = Pattern.compile("^\\/(?!help$|exit$)\\w*").matcher(command);
        return matcher.find();
    }

}
