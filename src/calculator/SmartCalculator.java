package calculator;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SmartCalculator {

    static void runCalculator() {

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("/exit")) {

            if (line.equals("/help")) {
                System.out.println("Any help here");
            }
            else if (isUnknownCommand(line)) {
                System.out.println("Unknown command");
            }
            else if (isInvalidExpression(line)) {
                System.out.println("Invalid expression");
            }
            else if (line.length() > 0) {

                Matcher matcher = Pattern.compile("(^|[ +-])+\\d+").matcher(line);

                int result = 0;
                while (matcher.find()) {
                    result += getNumberFromString(matcher.group());
                }

                System.out.println(result);

            }

            line = scanner.nextLine();
        }

        System.out.print("Bye!");

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
