package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SmartCalculator {

    static Pattern identifierPattern;
    static Pattern exspressionPattern;
    static Pattern assignmentPattern;

    static Map<String, Integer> variables = new HashMap<>();

    SmartCalculator() {
        identifierPattern = Pattern.compile("^\\s*([a-zA-z])+\\s*(?==)");
        exspressionPattern = Pattern.compile("(?<==)\\s*([a-zA-z]+|\\d+)\\s*$");
        assignmentPattern = Pattern.compile("^\\s*([a-zA-z])+\\s*=\\s*([a-zA-z]+|\\d+)\\s*$");
    }

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

        if (line.length() == 0) {

            //doing nothing

        } else if (isAssignment(line)) {

            processAssingment(line);

        } else if (isVariable(line)) {

            printVariable(line);

        } else if (isCommand(line)) {

            executeCommand(line);

        } else if (isOrdinalExpression(line)) {

            processExpression(line);

        }

    }

    private static boolean isAssignment(String line) {

        return line.contains("=");

    }

    private static boolean isVariable(String line) {

        return Pattern.compile("^\\s*([a-zA-z])+\\s*$").matcher(line).find();

    }

    private static boolean isCommand(String line) {

        return Pattern.compile("^/\\w+$").matcher(line).find();

    }

    private static boolean isOrdinalExpression(String line) {

        return Pattern.compile("((^|[ +-])+(\\d|[a-zA-z])+)+$").matcher(line).find();

    }

    private static void processAssingment(String line) {

        Matcher identifierMatcher = identifierPattern.matcher(line);
        Matcher expressionMatcher = exspressionPattern.matcher(line);
        Matcher assignmentMatcher = assignmentPattern.matcher(line);

        boolean isCorrectIdentifier = identifierMatcher.find();
        boolean isCorrectValue = expressionMatcher.find();
        boolean isCorrectAssignment = assignmentMatcher.find();

        if (!isCorrectIdentifier) {

            System.out.println("Invalid identifier");

        } else if (!isCorrectValue) {

            System.out.println("Invalid value");

        } else if (!isCorrectAssignment) {

            System.out.println("Invalid assignment");

        } else {

            variables.put(identifierMatcher.group(),calculateExpression(expressionMatcher.group()));

        }

    }

    private static void printVariable(String line) {

        System.out.println(getNumberFromVariable(line));

    }

    private static void executeCommand(String line) {

        if (line.equals("/help")) {
            System.out.println("Any help here");
        }
        else {
            System.out.println("Unknown command");
        }

    }

    private static void processExpression(String line) {

        int result = calculateExpression(line);

        System.out.println(result);

    }

    private static int calculateExpression(String line) {

        Matcher matcher = Pattern.compile("(^|[ +-])+(\\d|[a-zA-z])+").matcher(line);

        int result = 0;

        while (matcher.find()) {

            if (matcher.group().matches("(^|[ +-])+(\\d)+")) {

                result += getNumberFromString(matcher.group());

            } else {

                result += getNumberFromVariable(matcher.group());

            }

        }

        return result;

    }

    private static int getNumberFromVariable(String group) {

        return  0;

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
