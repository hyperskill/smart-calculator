package calculator;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String AVAILABLE_SYMBOLS = "[a-zA-Z0-9-+/=*/()^]+";
    private static Map<String, String> variables = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (checkUserInput(line)) {
                String[] values = parser(line);
                if (values != null) {
                    BigInteger result = calculate(values);
                    System.out.println(result);
                }
            }
        }
    }

    private static boolean checkUserInput(String input) {
        if ("".equals(input.trim()) || input.isEmpty() || input == null) {
            return false;
        }

        if (!input.replaceAll(" ", "").matches(AVAILABLE_SYMBOLS)) {
            System.out.println("Not acceptable chars. Invalid expression");
            return false;
        }

        if ('/' == input.charAt(0)) {
            if ("/help".equals(input)) {
                printHelp();
                return false;
            } else if ("/vars".equals(input)) {
                printVariables();
                return false;
            } else if ("/exit".equals(input)) {
                System.out.println("Bye!");
                System.exit(0);
            } else {
                System.out.println("Unknown command");
                return false;
            }
        }

        //if parentheses,symbols of division and multiply is correct, and not start with "-("
        if (!checkParentheses(input)
                || input.matches("(.*([*/^]){2,}.*)|(^[*/\\^]+.*)")
                || input.startsWith("-(")) {
            System.out.println("Invalid expression");
            return false;
        }

        Pattern charsPattern = Pattern.compile("[a-z]+", Pattern.CASE_INSENSITIVE);
        Matcher charsMatcher = charsPattern.matcher(input.replaceAll("\\s*", ""));
        boolean valid;

        if (charsMatcher.find()) {
            valid = checkEquationWithVariables(input);
        } else {
            valid = checkEquationWithOnlyDigits(input);
        }

        if (valid) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkEquationWithVariables(String input) {
        input = input.replaceAll("[()]*\\s*", "");
        //if var is correct
        if (input.matches("^[a-zA-Z]+$")) {
            return true;
        }

        if (input.indexOf('=') != -1) {
            //if assignment is correct
            if (input.matches("^[a-zA-Z]+=[+-]*[a-zA-Z]+$|^[a-zA-Z]+=[+-]*[0-9]+$")) {
                return true;
            }

            //if more than one "="
            int quantityEquals = 0;
            for (int i = 0; i < input.length(); i++) {
                if ('=' == input.charAt(i)) {
                    quantityEquals++;
                }
                if (quantityEquals > 1) {
                    System.out.println("Invalid assignment");
                    return false;
                }
            }

            String[] identifierAndValue = input.split("=");

            if (identifierAndValue.length > 1) {
                //if var is incorrect
                if (!identifierAndValue[0].matches("^[a-zA-Z]+$")) {
                    System.out.println("Invalid identifier");
                    return false;
                }

                //if value is incorrect
                Pattern valuePattern = Pattern.compile("[0-9][a-z]|[a-z][0-9]", Pattern.CASE_INSENSITIVE);
                Matcher valueMatcher = valuePattern.matcher(identifierAndValue[1]);
                if (valueMatcher.find()) {
                    System.out.println("Invalid value");
                    return false;
                }
            } else {
                //if only var without value, example "i = "
                System.out.println("Invalid value");
                return false;
            }
        } else {
            //if equation with var is correct
            if (input.matches("([-+]?([a-zA-Z]*|[0-9]*)([-+]+|[*^/])([a-zA-Z]+|[0-9]+))+")) {
                return true;
            }

            if (!input.matches("^[a-zA-Z]+$")) {
                System.out.println("Invalid identifier");
                return false;
            }
        }
        return true;
    }

    private static boolean checkEquationWithOnlyDigits(String input) {
        Pattern pattern = Pattern.compile("(^[+-]?\\d+$)|([+-]?\\d*([-+]+|[*^/])\\d+)+");
        Matcher matcher = pattern.matcher(input.replaceAll("[()]*\\s*", ""));

        if (matcher.matches()) {
            return true;
        } else {
            System.out.println("Invalid expression");
            return false;
        }
    }

    private static boolean checkParentheses(String input) {
        if (input.replaceAll(" ", "").matches("(.*\\([a-zA-Z0-9]*\\).*)|(.*[a-zA-Z0-9]+\\(.*)|(.*\\)[a-zA-Z0-9]+.*)")) {
            return false;
        }

        if (input.indexOf(')') < input.indexOf("(")) {
            return false;
        }

        int openingParentheses = 0;
        int closingParentheses = 0;

        for (int i = 0; i < input.length(); i++) {
            if ('(' == input.charAt(i)) {
                openingParentheses++;
            }
            if (')' == input.charAt(i)) {
                closingParentheses++;
            }
            if (closingParentheses > openingParentheses) {
                return false;
            }
        }

        if (openingParentheses != closingParentheses) {
            return false;
        }

        return true;
    }

    private static String[] parser(String line) {
        line = line.replaceAll("\\s+", "");
        String[] values;

        //print variable
        if (line.matches("^[a-zA-Z]+$")) {
            if (variables.containsKey(line)) {
                System.out.println(variables.get(line));
                return null;
            } else {
                System.out.println("Unknown variable");
                return null;
            }
        }

        //if contains =
        if (line.contains("=")) {
            values = removeExtraOperators(line);

            String var = values[0];
            String value = values[values.length - 1];

            //if value variable
            if (value.matches("[-+]?[a-zA-Z]+")) {
                value = replaceVariableToValue(value);
            }

            variables.put(var, value);
            return null;
        } else {
            values = removeExtraOperators(line);

            for (int i = 0; i < values.length; i++) {
                String var = values[i];

                //if not digit
                if (var.matches("[-+]?[a-zA-Z]+")) {
                    //if var exists
                    if (replaceVariableToValue(var) != null) {
                        values[i] = replaceVariableToValue(var);
                    } else {
                        return null;
                    }

                }
            }

            //if equation start with "-" or "+"
            if (values[0].matches("[+-]")) {
                values[1] = values[0] + values[1];
                String[] copy = new String[values.length - 1];
                for (int i = 1; i < values.length; i++) {
                    copy[i - 1] = values[i];
                }
                values = copy;
            }
        }
        return values;
    }

    private static String replaceVariableToValue(String value) {
        String operator = "";

        if (value.startsWith("+") || value.startsWith("-")) {
            operator = value.substring(0, 1);
            value = value.substring(1, value.length());
        }

        //if variable exists
        if (variables.containsKey(value)) {
            value = variables.get(value);
        } else {
            System.out.println("Unknown variable: " + value);
            return null;
        }

        operator = operator.replaceAll("\\+", "");
        value = operator.concat(value);

        if (value.contains("--")) {
            value = value.replaceAll("--", "");
        }

        return value;
    }

    private static String[] removeExtraOperators(String line) {
        String[] values = null;
        while (true) {
            line = line.replaceAll("-{2}", "+");
            line = line.replaceAll("[+]{2,}", "+");
            line = line.replaceAll("[+][-]|[-][+]", "-");

            if (line.contains("+-") || line.contains("-+") || line.contains("--")) {
                continue;
            }

            line = line.replaceAll("[+]", " + ")
                    .replaceAll("[-]", " - ")
                    .replaceAll("[*]", " * ")
                    .replaceAll("[/]", " / ")
                    .replaceAll("[\\^]", " ^ ")
                    .replaceAll("[(]", " ( ")
                    .replaceAll("[)]", " ) ")
                    .replaceAll("[=]", " = ");
            line = line.trim();
//            System.out.println(line);

            values = line.split("\\s+");
            break;
        }
        return values;
    }

    private static BigInteger calculate(String[] values) {
        //reverse polish notification
        RPN rpn = new RPN();
        BigInteger result = rpn.parse(values);

        return result;
    }

    private static void printHelp() {
        System.out.println("The smart calculator.\n" +
                "Enter the equation using numbers, +, -, *, /, (, ) and = (to assign variables).\n" +
                "For example, 3 + 8 * ( ( big + 3 ) * 2 + 1 ) - AAA / ( var + 1 ), or var = 100000000000000\n" +
                "Commands:\n" +
                "\"/help\" for print this note," +
                "\"/vars\" for print available variables,\n" +
                "\"/exit\" for closing program.");
    }

    private static void printVariables() {
        for (Map.Entry<String, String> vars : variables.entrySet()) {
            System.out.println(vars.getKey() + " = " + vars.getValue());
        }
    }
}
