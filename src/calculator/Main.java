package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static Map<String, String> variables = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if (checkUserInput(line)) {
                String[] values = parser(line);
                if (values != null) {
                    long sum = calculate(values);
                    System.out.println(sum);
                }
            }
        }
    }

    private static boolean checkUserInput(String input) {
        if ("".equals(input.trim()) || input.isEmpty() || input == null) {
            return false;
        }

        if (!input.replaceAll(" ", "").matches("[a-zA-Z0-9-+/=]+")) {
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
        input = input.replaceAll("\\s*", "");

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
            if (input.matches("([-+]?([a-zA-Z]*|[0-9]*)[-+]+([a-zA-Z]+|[0-9]+))+")) {
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
        Pattern pattern = Pattern.compile("(^[+-]?\\d+$)|([+-]?\\d*[+-]+\\d+)+");
        Matcher matcher = pattern.matcher(input.replaceAll("\\s*", ""));

        if (matcher.matches()) {
            return true;
        } else {
            System.out.println("Invalid expression");
            return false;
        }
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
                    values[i] = replaceVariableToValue(var);
                }
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
        String[] values;
        while (true) {
            line = line.replaceAll("-{2}", "+");
//            System.out.println(line);
            line = line.replaceAll("[+]{2,}", "+");
//            System.out.println(line);
            line = line.replaceAll("[+][-]|[-][+]", "-");
//            System.out.println(line);

            if (line.contains("+-") || line.contains("-+") || line.contains("--")) {
                continue;
            }

            line = line.replaceAll("[+]", " +").replaceAll("[-]", " -").replaceAll("[=]", " = ");
//            System.out.println(line);
            line = line.replaceAll("^\\s*|\\s*$", "");
//            System.out.println(line);

            values = line.split(" ");
            break;
        }
        return values;
    }

    private static long calculate(String[] values) {
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
        System.out.println("The program calculates the calculate of the numbers.\n" +
                "Enter the equation using numbers, plus and minus.\n" +
                "For example, -2 + 3 - 5 + 10\n" +
                ", \"\\vars\" for print available variables" +
                "or \"\\exit\" for closing program");
    }

    private static void printVariables() {
        for (Map.Entry<String, String> vars : variables.entrySet()) {
            System.out.println(vars.getKey() + ": " + vars.getValue());
        }
    }
}