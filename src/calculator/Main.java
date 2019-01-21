package calculator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.*;

public class Main {

    static Map<String,String> map = new HashMap<>();

    static int sum = 0; //while now sum is result

    public static void main (String[] args) {
        begin();
    }

    static class WrongCommandException extends Exception {
    }
    static class EmptyLineException extends WrongCommandException {
    }
    static class WrongBackslashCommandException extends WrongCommandException {
    }
    static class WrongVariable extends Exception {
    }
    static class WrongValue extends WrongVariable {
    }
    static class VariableNotSet extends RuntimeException {
    }
    static void LineValidation(String line) throws WrongCommandException {
        if(line.matches("^\\s*/\\w.*")){
            throw new WrongCommandException();
        }
        if(line.matches("^\\s*/\\s+\\w.*")){
            throw new WrongCommandException();
        }
        if(line.matches("^\\s*\\\\\\w.*")){
            throw new WrongBackslashCommandException();
        }
        if(line.equals("")){
            throw new EmptyLineException();
        }
    }
    static void VariableValidation (String[] dig_array) throws WrongVariable {
        if (dig_array.length!=2) {
            throw new WrongVariable();
        }
        if (!dig_array[1].matches("[a-zA-Z]|\\d+")){
            throw new WrongValue();
        }
        if (!dig_array[0].matches("[a-zA-Z]+")) {
            throw new WrongVariable();
        }
    }

    static void VariableExist (String dig) throws VariableNotSet {
        if (!map.containsKey(dig)) {
            throw new VariableNotSet();
        }
    }

    static void begin() {
        Scanner sc = new Scanner(System.in);
        String line = "";

        while (!line.equals("/exit")) {
            line = sc.nextLine();

            if (line.equals("/exit")) {
                System.out.println("Bye!");
                break;
            }
            if (line.equals("/help")) {
                System.out.println("The program calculates the sum of numbers.\n" +
                        "Type something like -67 + 5 - 34 and you will get the result.\n" +
                        "Commands:\n" +
                        "/exit  -  type it to exit program.\n" +
                        "/help - this text.");
                continue;
            }
            try {
                LineValidation(line);
            } catch (EmptyLineException e) {
                System.out.println("The line is empty. Enter some math expression to calculate.");
                continue;
            } catch (WrongBackslashCommandException e) {
                System.out.println("It seems like you tried to enter command, but started it with backslash.\n" +
                        "Type /help to see full command list.");
                continue;
            } catch (WrongCommandException e) {
                System.out.println("Unknown command.\n" +
                        "Type /help to see full command list.");
                continue;
            }
            if (line.matches("^\\s*\\w+\\s*=\\s*\\w+\\s*$")) {
                variableProcessing(line);
                continue;
            }
            line = clearingLine(line);
            processing(line);
        }
    }
    static void variableProcessing(String line) {
        line = line.replaceAll("(^\\s+)|(\\s+$)", "");
        line = line.replaceAll("\\s*=\\s*", " = ");
        line = line.replaceAll("\\s+", " ");
        Pattern pat = Pattern.compile("\\s=\\s");
        String[] dig_array = pat.split(line);
        try {
            VariableValidation(dig_array);
        } catch (WrongValue e) {
            System.out.println("Wrong value.");
        } catch (WrongVariable e) {
            System.out.println("Whatever you typed, it's not variable.\n" +
                    "Variable should initialize as a = 1, b = 51, veganpizza = 1000.\n" +
                    "Latin characters only, no spaces or digits in variable name.");
        }
        if (dig_array[1].matches("\\d+")) {
            map.put(dig_array[0],dig_array[1]);
        } else {
            try {
                VariableExist(dig_array[1]);
                map.put(dig_array[0],map.get(dig_array[1]));
            } catch (RuntimeException e) {
                System.out.println("Unknown variable '"+dig_array[1]+"'.");
            }
        }
    }

    static void sum(int digit) {
        sum = sum + digit;
    }

    static String clearingLine(String line) {
        line = line.replaceAll(" ", "");
        line = line.replaceAll("^", "+");
        line = line.replaceAll("(--)+", "+");
        line = line.replaceAll("\\++", "+");
        line = line.replaceAll("(\\+-)|(-\\+)", "-");
        line = line.replaceAll("\\+", " +");
        line = line.replaceAll("-", " -");
        line = line.replaceAll("^ ", "");
        return line;
    }

    static String[] setVariables (String[] dig_array){
        //System.out.println(Arrays.toString(dig_array)); //del me (just for testing)
        for (int i=0; i<dig_array.length; i++) {
            if (dig_array[i].matches("(\\+|-)[a-zA-Z]+")){
                VariableExist(dig_array[i].substring(1));
                dig_array[i] = dig_array[i].replace(dig_array[i].substring(1),map.get(dig_array[i].substring(1)));
            }
        }
        //System.out.println(Arrays.toString(dig_array)); //del me (just for testing)
        return dig_array;
    }

    static void processing(String line) {
        Pattern pat = Pattern.compile("(?:\\s)");
        String[] dig_array = pat.split(line);
        try {
            dig_array = setVariables(dig_array);
        for (String dig : dig_array) {
            sum(Integer.parseInt(dig));
        }
        System.out.println("= " + sum);
        } catch (NumberFormatException e) {
            System.out.println("Invalid expression.");
        } catch (VariableNotSet e) {
            System.out.println("Unknown variable.");
        } finally {
            map.clear();
            sum = 0;
        }
    }
} //main end
