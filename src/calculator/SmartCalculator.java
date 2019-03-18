package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SmartCalculator {

    private enum Status {ORDINAL_EXPRESSION,ASSIGNMENT,VARIABLE,EMPTY_LINE,COMMAND,
        INVALID_EXPRESSION,INVALID_IDENTIFIER,INVALID_VALUE,INVALID_ASSIGNMENT}

    Map<Character, Integer> variables = new HashMap<>();

    static void runCalculator() {

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("/exit")) {

            parseLine(line);

            line = scanner.nextLine();

        }

        System.out.print("Bye!");

    }

    private static void parseLine(String line) {

        Status lineStatus = computeLineStatus(line);

        switch (lineStatus) {
            case ORDINAL_EXPRESSION:
                processExpression(line);
                break;
            case ASSIGNMENT:
                processAssingment(line);
                break;
            case VARIABLE:
                printVariable(line);
                break;
            case COMMAND:
                executeCommand(line);
                break;
            case INVALID_VALUE:
                System.out.println("Invalid value");
                break;
            case INVALID_ASSIGNMENT:
                System.out.println("Invalid assignment");
                break;
            case INVALID_EXPRESSION:
                System.out.println("Invalid expression");
                break;
            case INVALID_IDENTIFIER:
                System.out.println("Invalid value");
                break;
            case EMPTY_LINE:
                break;
        }

    }

    private static Status computeLineStatus(String line) {

        if (line.length() == 0) return Status.EMPTY_LINE;

        if (isAssignment(line)) return Status.ASSIGNMENT;

        if (isVariable(line)) return  Status.VARIABLE;

        if (isCommand(line)) return Status.COMMAND;

        if (isOrdinalExpression(line)) return Status.ORDINAL_EXPRESSION;




        return Status.INVALID_EXPRESSION;

    }

    private static boolean isVariable(String line) {

        Matcher matcher = Pattern.compile("(^|[ +-])+([a-zA-z])+\\s+$").matcher(line);

        return matcher.find();

    }

    private static boolean isOrdinalExpression(String line) {

        Matcher matcher = Pattern.compile("((^|[ +-])+(\\d|[a-zA-z])+)+$").matcher(line);

        return matcher.find();

    }

    private static void printVariable(String line) {

        System.out.println(getNumberFromVariable(line));

    }

    private static void processAssingment(String line) {



    }

    private static void processExpression(String line) {

        Matcher matcher = Pattern.compile("(^|[ +-])+(\\d|[a-zA-z])+").matcher(line);

        int result = 0;

        while (matcher.find()) {

            if (matcher.group().matches("(^|[ +-])+(\\d)+")) {

                result += getNumberFromString(matcher.group());

            } else {

                result += getNumberFromVariable(matcher.group());

            }

        }

        System.out.println(result);

    }

    private static int getNumberFromVariable(String group) {

        return  0;

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
        Matcher matcher = Pattern.compile("^/\\w+$").matcher(line);
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

}
